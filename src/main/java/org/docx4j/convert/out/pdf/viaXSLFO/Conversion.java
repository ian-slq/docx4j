package org.docx4j.convert.out.pdf.viaXSLFO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.docx4j.XmlUtils;
import org.docx4j.convert.out.Converter;
import org.docx4j.convert.out.html.HtmlExporterNG2.EndnoteState;
import org.docx4j.convert.out.html.HtmlExporterNG2.FootnoteState;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.model.PropertyResolver;
import org.docx4j.model.TransformState;
import org.docx4j.model.SymbolModel.SymbolModelTransformState;
import org.docx4j.model.listnumbering.Emulator.ResultTriple;
import org.docx4j.model.properties.Property;
import org.docx4j.model.properties.PropertyFactory;
import org.docx4j.model.properties.paragraph.Indent;
import org.docx4j.model.properties.run.Font;
import org.docx4j.model.table.TableModel.TableModelTransformState;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.PPr;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;
import org.docx4j.wml.UnderlineEnumeration;
import org.docx4j.wml.PPrBase.NumPr.Ilvl;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.NodeIterator;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.fonts.FontTriplet;
import org.apache.log4j.Logger;
import org.apache.xml.dtm.ref.DTMNodeProxy;


public class Conversion extends org.docx4j.convert.out.pdf.PdfConversion {
	
	protected static Logger log = Logger.getLogger(Conversion.class);	
	
	public static final String PART_TRACKER = "partTracker";
	
	public Conversion(WordprocessingMLPackage wordMLPackage) {
		super(wordMLPackage);
	}
	
	static Templates xslt;	
	static {
		try {
			Source xsltSource  = new StreamSource(
					org.docx4j.utils.ResourceUtils.getResource(
							"org/docx4j/convert/out/pdf/viaXSLFO/docx2fo.xslt"));
			xslt = XmlUtils.getTransformerTemplate(xsltSource);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private static File saveFO;
	public void setSaveFO(File save) {
		saveFO = save;
	}
	

	/**
	 * Create a FOP font configuration for each font used in the
	 * document.
	 * 
	 * @return
	 */
	private String declareFonts() {
		
		StringBuffer result = new StringBuffer();
		Map fontsInUse = wordMLPackage.getMainDocumentPart().fontsInUse();
		Iterator fontMappingsIterator = fontsInUse.entrySet().iterator();
		while (fontMappingsIterator.hasNext()) {
		    Map.Entry pairs = (Map.Entry)fontMappingsIterator.next();
		    if(pairs.getKey()==null) {
		    	log.info("Skipped null key");
//		    	pairs = (Map.Entry)fontMappingsIterator.next();
		    	continue;
		    }
		    
		    String fontName = (String)pairs.getKey();		    
		    
		    PhysicalFont pf = wordMLPackage.getFontMapper().getFontMappings().get(fontName);
		    
		    if (pf==null) {
		    	log.error("Document font " + fontName + " is not mapped to a physical font!");
		    	continue;
		    }
		    
		    result.append("<font embed-url=\"" +pf.getEmbeddedFile() + "\">" );
		    	// now add the first font triplet
			    FontTriplet fontTriplet = (FontTriplet)pf.getEmbedFontInfo().getFontTriplets().get(0);
			    addFontTriplet(result, fontTriplet);
		    result.append("</font>" );
		    
		    // bold, italic etc
		    PhysicalFont pfVariation = PhysicalFonts.getBoldForm(pf);
		    if (pfVariation!=null) {
			    result.append("<font embed-url=\"" +pfVariation.getEmbeddedFile() + "\">" );
		    	addFontTriplet(result, pf.getName(), "normal", "bold");
			    result.append("</font>" );
		    }
		    pfVariation = PhysicalFonts.getBoldItalicForm(pf);
		    if (pfVariation!=null) {
			    result.append("<font embed-url=\"" +pfVariation.getEmbeddedFile() + "\">" );
		    	addFontTriplet(result, pf.getName(), "italic", "bold");
			    result.append("</font>" );
		    }
		    pfVariation = PhysicalFonts.getItalicForm(pf);
		    if (pfVariation!=null) {
			    result.append("<font embed-url=\"" +pfVariation.getEmbeddedFile() + "\">" );
		    	addFontTriplet(result, pf.getName(), "italic", "normal");
			    result.append("</font>" );
		    }
			    
		}
		
		return result.toString();
		
	}
		
	private void addFontTriplet(StringBuffer result, FontTriplet fontTriplet) {
	    result.append("<font-triplet name=\"" + fontTriplet.getName() + "\""
				+ " style=\"" + fontTriplet.getStyle() + "\""
				+ " weight=\"" + weightToCSS2FontWeight(fontTriplet.getWeight()) + "\""
						+ "/>" );		
	}
	private void addFontTriplet(StringBuffer result, String familyName, String style, String weight) {
	    result.append("<font-triplet name=\"" + familyName + "\""
				+ " style=\"" + style + "\""
				+ " weight=\"" + weight + "\""
						+ "/>" );		
	}
	
	private String weightToCSS2FontWeight(int i) {
		
		if (i>=700) {
			return "bold";
		} else {
			return "normal";
		}
		
	}
	
	Configuration fopConfig;
	/**
	 * User can set their own fop configuration if they
	 * wish, in which can it is their responsibility
	 * to include the fonts the font mapper is using.
	 * (If this method is not used, an appropriate
	 *  configuration will be generated automatically)
	 * @param fopConfig
	 */
	public void setFopConfig(Configuration fopConfig) {
		this.fopConfig = fopConfig;
	}
	
	
	/** Create a pdf version of the document, using XSL FO. 
	 * 
	 * @param os
	 *            The OutputStream to write the pdf to 
	 * 
	 * */     
    public void output(OutputStream os) throws Docx4JException {
    	
    	// See http://xmlgraphics.apache.org/fop/0.95/embedding.html
    	// (reuse if you plan to render multiple documents!)
    	FopFactory fopFactory = FopFactory.newInstance();
    	
    	try {
                
    		if (fopConfig == null) {
    			
				DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
				String myConfig = "<fop version=\"1.0\"><strict-configuration>true</strict-configuration>"
						+ "<renderers><renderer mime=\"application/pdf\">"
						+ "<fonts>" + declareFonts() +
						// <directory>/home/dev/fonts</directory>" +
						// "<directory>/usr/share/fonts/truetype/ttf-lucida</directory>"
						// +
						// "<directory>/var/lib/defoma/fontconfig.d/D</directory>"
						// +
						// "<directory>/var/lib/defoma/fontconfig.d/L</directory>"
						// +
						// "<auto-detect/>" +
						"</fonts></renderer></renderers></fop>";

				log.info("\nUsing config:\n " + myConfig + "\n");

				// See FOP's PrintRendererConfigurator
				// String myConfig = "<fop
				// version=\"1.0\"><strict-configuration>true</strict-configuration>"
				// +
				// "<renderers><renderer mime=\"application/pdf\">" +
				// "<fonts><directory
				// recursive=\"true\">C:\\WINDOWS\\Fonts</directory>" +
				// "<auto-detect/>" +
				// "</fonts></renderer></renderers></fop>";

				fopConfig = cfgBuilder.build(new ByteArrayInputStream(myConfig
						.getBytes()));
			}
    		
    	  fopFactory.setUserConfig(fopConfig);
    	  
    	  Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, os);

    	  /*
    	   * Based on the principle that we'll do all the smarts via
    	   * extension functions which can take advantage of Java
    	   * and docx4j's model of the package, all the XSLT
    	   * needs is the main document part.
    	   * 
    	   * This means we can skip the step of generating a
    	   * Flat OPC XML file.
    	   */
    	  //Document domDoc = XmlPackage.getFlatDomDocument(wordMLPackage);
    	  Document domDoc = XmlUtils.marshaltoW3CDomDocument(wordMLPackage.getMainDocumentPart().getJaxbElement());
    	  
    	  java.util.HashMap<String, Object> settings = new java.util.HashMap<String, Object>();
			settings.put("wmlPackage", wordMLPackage);
	        String imageDirPath = System.getProperty("java.io.tmpdir");
			settings.put("imageDirPath", imageDirPath);
			
	      	  // Resulting SAX events (the generated FO) must be piped through to FOP
	      	  Result result = new SAXResult(fop.getDefaultHandler());
	      	  
	  		// Allow arbitrary objects to be passed to the converters.
	  		// The objects are assumed to be specific to a particular converter (eg table),
	  		// so assume there will be one object implementing TransformState per converter.   
	  		HashMap<String, TransformState> modelStates  = new HashMap<String, TransformState>();
	  		settings.put("modelStates", modelStates );	      	  
	      	  
//	  		Converter c = new Converter();
	      	Converter.getInstance().registerModelConverter("w:tbl", new TableWriter() );
	      	Converter.getInstance().registerModelConverter("w:sym", new SymbolWriter() );
	      	
			// By convention, the transform state object is stored by reference to the 
			// type of element to which its model applies
			modelStates.put("w:tbl", new TableModelTransformState() );
			modelStates.put("w:sym", new SymbolModelTransformState() );
	      	
			modelStates.put("footnoteNumber", new FootnoteState() );
			modelStates.put("endnoteNumber", new EndnoteState() );
			modelStates.put(PART_TRACKER, new PartTracker() );
			
	      	Converter.getInstance().start(wordMLPackage);
	      	  
	    	  
	  		if (saveFO!=null || log.isDebugEnabled()) {

	  			ByteArrayOutputStream intermediate = new ByteArrayOutputStream();
	  			Result intermediateResult =  new StreamResult( intermediate );
	  			
	  			XmlUtils.transform(domDoc, xslt, settings, intermediateResult);
	  			
	  			String fo = intermediate.toString("UTF-8");
	  			log.debug(fo);
	  			
	  			if (saveFO!=null) {
	  				FileUtils.writeStringToFile(saveFO, fo);	
	  				log.info("Saved " + saveFO.getPath() );
	  			}
	  			
	  			Source src = new StreamSource(new StringReader(fo));
		    	
	  			Transformer transformer = XmlUtils.tfactory.newTransformer();
	  			transformer.transform(src, result);
	  		} else {
	      	   
	    	  XmlUtils.transform(domDoc, xslt, settings, result);
	  			
	  		}
    	  
    	} catch (Exception e) {
    		throw new Docx4JException("FOP issues", e);
    	} finally {
    	  //Clean-up
    	  try {
			os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}		
		
	}

    /* ---------------Xalan XSLT Extension Functions ---------------- */
    
    /**
     * On the block representing the w:p, we want to put both
     * pPr and rPr attributes.
     * 
     * @param wmlPackage
     * @param pPrNodeIt
     * @param pStyleVal
     * @param childResults
     * @return
     */
    public static DocumentFragment createBlockForPPr( 
    		WordprocessingMLPackage wmlPackage,
    		NodeIterator pPrNodeIt,
    		String pStyleVal, NodeIterator childResults ) {
    	
    	PropertyResolver propertyResolver = 
    		wmlPackage.getMainDocumentPart().getPropertyResolver();
    	
    	// Note that this is invoked for every paragraph with a pPr node.
    	
    	// incoming objects are org.apache.xml.dtm.ref.DTMNodeIterator 
    	// which implements org.w3c.dom.traversal.NodeIterator
    	
    	String defaultParagraphStyleId = wmlPackage.getMainDocumentPart().getStyleDefinitionsPart().getDefaultParagraphStyle().getStyleId(); 
    	
		if ( pStyleVal ==null || pStyleVal.equals("") ) {
//			pStyleVal = "Normal";
			pStyleVal = defaultParagraphStyleId;
		}
    	log.debug("style '" + pStyleVal );     		

    	//    	log.info("pPrNode:" + pPrNodeIt.getClass().getName() ); // org.apache.xml.dtm.ref.DTMNodeIterator    	
//    	log.info("childResults:" + childResults.getClass().getName() ); 
    	
    	
        try {
        	
        	PPr pPrDirect = null;
        	
        	// Get the pPr node as a JAXB object,
        	// so we can read it using our standard
        	// methods.  Its a bit sad that we 
        	// can't just adorn our DOM tree with the
        	// original JAXB objects?
        	PPr pPr = null;
        	RPr rPr = null;
        	if (pPrNodeIt==null) {  // Never happens?        		
    			log.debug("Here after all!!");        		
        		pPr = propertyResolver.getEffectivePPr(defaultParagraphStyleId);
        		rPr = propertyResolver.getEffectiveRPr(defaultParagraphStyleId);
        	} else {
        		Node n = pPrNodeIt.nextNode();
        		if (n==null) {
        			log.warn("pPrNodeIt.nextNode() was null.");
            		pPr = propertyResolver.getEffectivePPr(defaultParagraphStyleId);
            		rPr = propertyResolver.getEffectiveRPr(defaultParagraphStyleId);
            		// TODO - in this case, we should be able to compute once,
            		// and on subsequent calls, just return pre computed value
        		} else {
					log.debug( XmlUtils.w3CDomNodeToString(n) );
        			Unmarshaller u = Context.jc.createUnmarshaller();			
        			u.setEventHandler(new org.docx4j.jaxb.JaxbValidationEventHandler());
        			Object jaxb = u.unmarshal(n);
    				pPrDirect =  (PPr)jaxb;
    				pPr = propertyResolver.getEffectivePPr(pPrDirect);  
    				if (pPr==null) {
    					log.warn("pPr null; obtained from: " + XmlUtils.w3CDomNodeToString(n) );
    				}
    				log.warn("getting rPr for paragraph style");    				
    				rPr = propertyResolver.getEffectiveRPr(null, pPrDirect); 
    					// rPr in pPr direct formatting only applies to paragraph mark, 
    					// so pass null here       				
        		}
        	}        	

			if (log.isDebugEnabled() && pPr!=null) {				
				log.debug(XmlUtils.marshaltoString(pPr, true, true));					
			}
        	
            // Create a DOM builder and parse the fragment			
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();        
			Document document = factory.newDocumentBuilder().newDocument();
			
			//log.info("Document: " + document.getClass().getName() );
			
			boolean inlist = false;
			
			Element foBlockElement;
			if (pPr!=null && pPr.getNumPr()!=null ) {
				
				inlist = true;
				
				// Its a list item.  At present we make a new list-block for
				// each list-item. This is not great; DocumentModel will ultimately
				// allow us to use fo:list-block properly.

				Element foListBlock = document.createElementNS("http://www.w3.org/1999/XSL/Format", 
						"fo:list-block");
				document.appendChild(foListBlock);
								
				foListBlock.setAttribute("provisional-distance-between-starts", "0.5in");
				
				Element foListItem = document.createElementNS("http://www.w3.org/1999/XSL/Format", 
						"fo:list-item");
				foListBlock.appendChild(foListItem);				

				
				Element foListItemLabel = document.createElementNS("http://www.w3.org/1999/XSL/Format", 
						"fo:list-item-label");
				foListItem.appendChild(foListItemLabel);
				
				Element foListItemLabelBody = document.createElementNS("http://www.w3.org/1999/XSL/Format", 
						"fo:block");
				foListItemLabel.appendChild(foListItemLabelBody);
				
	        	ResultTriple triple;
	        	if (pPrDirect!=null && pPrDirect.getNumPr()!=null) {
	        		triple = org.docx4j.model.listnumbering.Emulator.getNumber(
	        			wmlPackage, pStyleVal, 
	        			pPrDirect.getNumPr().getNumId().getVal().toString(), 
	        			pPrDirect.getNumPr().getIlvl().getVal().toString() ); 
	        	} else {
	        		// Get the effective values; since we already know this,
	        		// save the effort of doing this again in Emulator
	        		Ilvl ilvl = pPr.getNumPr().getIlvl();
	        		String ilvlString = ilvl == null ? "0" : ilvl.getVal().toString();
	        		triple = org.docx4j.model.listnumbering.Emulator.getNumber(
		        			wmlPackage, pStyleVal, 
		        			pPr.getNumPr().getNumId().getVal().toString(), 
		        			ilvlString ); 		        			
	        	}
				
				if (triple==null) {
	        		log.info("computed number ResultTriple was null");
					foListItemLabelBody.setTextContent("?");
	        	} else {
	        		
	        		// Indent (in combination with provisional-distance-between-starts
	        		// above
	        		if (triple.getIndent()!=null) {
	        			Indent indent = new Indent(triple.getIndent());
	    				//foListBlock.setAttribute(Indent.FO_NAME, "2in");
	    				indent.setXslFO(foListBlock);
	        		}
	        		
	        		// Set the font
	        		if (triple.getNumFont()!=null) {
	        			String font = Font.getPhysicalFont(wmlPackage, triple.getNumFont() );
	        			if (font!=null) {
	        				foListItemLabelBody.setAttribute(Font.FO_NAME, font );
	        			}
	        		}
	        		
	        		if (triple.getBullet()!=null ) {
		        		foListItemLabelBody.setTextContent(triple.getBullet() );
		        	} else if (triple.getNumString()==null) {
			    		log.error("computed NumString was null!");
						foListItemLabelBody.setTextContent("?");
			    	} else {
						Text number = document.createTextNode( triple.getNumString() );
						foListItemLabelBody.appendChild(number);		    		
			    	}
	        	}
				
				Element foListItemBody = document.createElementNS("http://www.w3.org/1999/XSL/Format", 
						"fo:list-item-body");
				foListItem.appendChild(foListItemBody);	

				foListItemBody.setAttribute(Indent.FO_NAME, "body-start()");
				
				foBlockElement = document.createElementNS("http://www.w3.org/1999/XSL/Format", 
						"fo:block");
				foListItemBody.appendChild(foBlockElement);
				
			} else {

				foBlockElement = document.createElementNS("http://www.w3.org/1999/XSL/Format", "fo:block");
				document.appendChild(foBlockElement);
			}
			
							
			if (pPr!=null) {
				createFoAttributes(pPr, ((Element)foBlockElement), inlist );
			}
			
			if (rPr!=null) {											
				createFoAttributes(wmlPackage, rPr, ((Element)foBlockElement) );
	        }
        
			// Our fo:block wraps whatever result tree fragment
			// our style sheet produced when it applied-templates
			// to the child nodes
			Node n = childResults.nextNode();
			
			// Handle empty case - want the block to be preserved!
			if (n.getChildNodes().getLength()==0) {
				
				((Element)foBlockElement).setAttribute( "white-space-treatment", "preserve");
				foBlockElement.setTextContent(" ");
				
			} else {
			
	//				log.info("Node we are importing: " + n.getClass().getName() );
	//				foBlockElement.appendChild(
	//						document.importNode(n, true) );
				/*
				 * Node we'd like to import is of type org.apache.xml.dtm.ref.DTMNodeProxy
				 * which causes
				 * org.w3c.dom.DOMException: NOT_SUPPORTED_ERR: The implementation does not support the requested type of object or operation.
				 * 
				 * See http://osdir.com/ml/text.xml.xerces-j.devel/2004-04/msg00066.html
				 * 
				 * So instead of importNode, use 
				 */
				XmlUtils.treeCopy( (DTMNodeProxy)n,  foBlockElement );
				
			}
			
			DocumentFragment docfrag = document.createDocumentFragment();
			docfrag.appendChild(document.getDocumentElement());

			return docfrag;
						
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString() );
			log.error(e);
		} 
    	
    	return null;
    	
    }

	public static void createFoAttributes(PPr pPr, Element foBlockElement, boolean inList){
		
    	List<Property> properties = PropertyFactory.createProperties(pPr);
    	
    	for( Property p :  properties ) {
			if (p!=null) {
				if (inList && !(p instanceof Indent) ) { 
					// Don't set start-indent in 
					// fo:list-item-body/fo:block.
					// This has to be handled above using something like 
					//  <fo:list-block provisional-distance-between-starts="0.5in" start-indent="2in">
					p.setXslFO(foBlockElement);
				} else if (!inList) {
					p.setXslFO(foBlockElement);
				}
			}
    	}
		
	}
	
    protected static void createFoAttributes(TcPr tcPr, Element foBlockElement){
    	// includes TcPrInner.TcBorders, CTShd, TcMar, CTVerticalJc
    	
		if (tcPr==null) {
			return;
		}
    	
    	List<Property> properties = PropertyFactory.createProperties(tcPr);    	
    	for( Property p :  properties ) {
			p.setXslFO(foBlockElement);
    	}    
    }
	

    /**
     * On a block representing a run, we just put run properties
     * from this rPr node. The paragraph style rPr's have been
     * taken care of on the fo block which represents the paragraph.
     * 
     * @param wmlPackage
     * @param rPrNodeIt
     * @param childResults
     * @return
     */
    public static DocumentFragment createBlockForRPr( 
    		WordprocessingMLPackage wmlPackage,
    		//NodeIterator pPrNodeIt,
    		NodeIterator rPrNodeIt,
    		NodeIterator childResults ) {

    	PropertyResolver propertyResolver = 
    		wmlPackage.getMainDocumentPart().getPropertyResolver();
    	
    	// Note that this is invoked for every paragraph with a pPr node.
    	
    	// incoming objects are org.apache.xml.dtm.ref.DTMNodeIterator 
    	// which implements org.w3c.dom.traversal.NodeIterator

    	
//    	log.info("rPrNode:" + rPrNodeIt.getClass().getName() ); // org.apache.xml.dtm.ref.DTMNodeIterator    	
//    	log.info("childResults:" + childResults.getClass().getName() ); 
    	
    	
        try {
        	
        	// Get the pPr node as a JAXB object,
        	// so we can read it using our standard
        	// methods.  Its a bit sad that we 
        	// can't just adorn our DOM tree with the
        	// original JAXB objects?
			Unmarshaller u = Context.jc.createUnmarshaller();			
			u.setEventHandler(new org.docx4j.jaxb.JaxbValidationEventHandler());

//			// We might not have a pPr node
//			PPr pPrDirect = null;
//        	if (pPrNodeIt!=null) {
//        		Node n = pPrNodeIt.nextNode();
//        		if (n!=null) {
//        			Object jaxb = u.unmarshal(n);
//        			try {
//        				pPrDirect =  (PPr)jaxb;
//        			} catch (ClassCastException e) {
//        		    	log.error("Couldn't cast " + jaxb.getClass().getName() + " to PPr!");
//        			}        	        			
//        		}
//        	}
        	
			Object jaxbR = u.unmarshal(rPrNodeIt.nextNode());			
			RPr rPrDirect = null;
			try {
				rPrDirect =  (RPr)jaxbR;
			} catch (ClassCastException e) {
		    	log.error("Couldn't cast .." );
			}        	
        	RPr rPr = propertyResolver.getEffectiveRPr(rPrDirect, null);
        	
            // Create a DOM builder and parse the fragment
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();        
			Document document = factory.newDocumentBuilder().newDocument();
			
			//log.info("Document: " + document.getClass().getName() );

			Node foInlineElement = document.createElementNS("http://www.w3.org/1999/XSL/Format", "fo:inline");			
			document.appendChild(foInlineElement);
			
				
			if (log.isDebugEnabled() && rPr!=null) {					
				log.debug(XmlUtils.marshaltoString(rPr, true, true));					
			}
			
			//if (rPr!=null) {				
				createFoAttributes(wmlPackage, rPr, ((Element)foInlineElement) );
			//}
			
			// Our fo:block wraps whatever result tree fragment
			// our style sheet produced when it applied-templates
			// to the child nodes
			Node n = childResults.nextNode();
			XmlUtils.treeCopy( (DTMNodeProxy)n,  foInlineElement );			
			
			DocumentFragment docfrag = document.createDocumentFragment();
			docfrag.appendChild(document.getDocumentElement());

			return docfrag;
						
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString() );
			log.error(e);
		} 
    	
    	return null;
    	
    }

	public static void createFoAttributes(WordprocessingMLPackage wmlPackage,
			RPr rPr, Element foInlineElement){

    	List<Property> properties = PropertyFactory.createProperties(wmlPackage, rPr);
    	
    	for( Property p :  properties ) {
    		p.setXslFO(foInlineElement);
    	}
		
	}
	
}
    