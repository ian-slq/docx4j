/*
 *  Copyright 2010, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */


package org.plutext.jaxb.svg11;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/2000/svg}desc"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}title"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}metadata"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}animate"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}set"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}animateMotion"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}animateColor"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}animateTransform"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}svg"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}g"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}defs"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}symbol"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}use"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}switch"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}image"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}style"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}path"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}rect"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}circle"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}line"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}ellipse"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}polyline"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}polygon"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}text"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}altGlyphDef"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}marker"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}color-profile"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}linearGradient"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}radialGradient"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}pattern"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}clipPath"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}mask"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}filter"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}cursor"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}a"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}view"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}script"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}font"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}font-face"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://www.w3.org/2000/svg}SVG.Presentation.attrib"/>
 *       &lt;attribute name="unicode" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="glyph-name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="d" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="arabic-form" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lang" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="horiz-adv-x" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="vert-origin-x" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="vert-origin-y" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="vert-adv-y" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "descOrTitleOrMetadata"
})
@XmlRootElement(name = "glyph")
public class Glyph {

    @XmlElements({
        @XmlElement(name = "filter", type = Filter.class),
        @XmlElement(name = "svg", type = Svg.class),
        @XmlElement(name = "title", type = Title.class),
        @XmlElement(name = "animate", type = Animate.class),
        @XmlElement(name = "symbol", type = Symbol.class),
        @XmlElement(name = "circle", type = Circle.class),
        @XmlElement(name = "text", type = Text.class),
        @XmlElement(name = "view", type = View.class),
        @XmlElement(name = "ellipse", type = Ellipse.class),
        @XmlElement(name = "defs", type = Defs.class),
        @XmlElement(name = "animateColor", type = AnimateColor.class),
        @XmlElement(name = "use", type = Use.class),
        @XmlElement(name = "image", type = Image.class),
        @XmlElement(name = "desc", type = Desc.class),
        @XmlElement(name = "radialGradient", type = RadialGradient.class),
        @XmlElement(name = "switch", type = Switch.class),
        @XmlElement(name = "script", type = Script.class),
        @XmlElement(name = "pattern", type = Pattern.class),
        @XmlElement(name = "font", type = Font.class),
        @XmlElement(name = "animateTransform", type = AnimateTransform.class),
        @XmlElement(name = "clipPath", type = ClipPath.class),
        @XmlElement(name = "marker", type = Marker.class),
        @XmlElement(name = "g", type = G.class),
        @XmlElement(name = "animateMotion", type = AnimateMotion.class),
        @XmlElement(name = "mask", type = Mask.class),
        @XmlElement(name = "cursor", type = Cursor.class),
        @XmlElement(name = "altGlyphDef", type = AltGlyphDef.class),
        @XmlElement(name = "color-profile", type = ColorProfile.class),
        @XmlElement(name = "a", type = A.class),
        @XmlElement(name = "polygon", type = Polygon.class),
        @XmlElement(name = "metadata", type = Metadata.class),
        @XmlElement(name = "set", type = Set.class),
        @XmlElement(name = "font-face", type = FontFace.class),
        @XmlElement(name = "line", type = Line.class),
        @XmlElement(name = "style", type = Style.class),
        @XmlElement(name = "polyline", type = Polyline.class),
        @XmlElement(name = "linearGradient", type = LinearGradient.class),
        @XmlElement(name = "path", type = Path.class),
        @XmlElement(name = "rect", type = Rect.class)
    })
    protected List<Object> descOrTitleOrMetadata;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String unicode;
    @XmlAttribute(name = "glyph-name")
    @XmlSchemaType(name = "anySimpleType")
    protected String glyphName;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String d;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String orientation;
    @XmlAttribute(name = "arabic-form")
    @XmlSchemaType(name = "anySimpleType")
    protected String arabicForm;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String lang;
    @XmlAttribute(name = "horiz-adv-x")
    @XmlSchemaType(name = "anySimpleType")
    protected String horizAdvX;
    @XmlAttribute(name = "vert-origin-x")
    @XmlSchemaType(name = "anySimpleType")
    protected String vertOriginX;
    @XmlAttribute(name = "vert-origin-y")
    @XmlSchemaType(name = "anySimpleType")
    protected String vertOriginY;
    @XmlAttribute(name = "vert-adv-y")
    @XmlSchemaType(name = "anySimpleType")
    protected String vertAdvY;
    @XmlAttribute(name = "flood-color")
    @XmlSchemaType(name = "anySimpleType")
    protected String floodColor;
    @XmlAttribute(name = "flood-opacity")
    @XmlSchemaType(name = "anySimpleType")
    protected String floodOpacity;
    @XmlAttribute(name = "lighting-color")
    @XmlSchemaType(name = "anySimpleType")
    protected String lightingColor;

    /**
     * Gets the value of the descOrTitleOrMetadata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descOrTitleOrMetadata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescOrTitleOrMetadata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Filter }
     * {@link Svg }
     * {@link Title }
     * {@link Animate }
     * {@link Symbol }
     * {@link Circle }
     * {@link Text }
     * {@link View }
     * {@link Ellipse }
     * {@link Defs }
     * {@link AnimateColor }
     * {@link Use }
     * {@link Image }
     * {@link Desc }
     * {@link RadialGradient }
     * {@link Switch }
     * {@link Script }
     * {@link Pattern }
     * {@link Font }
     * {@link AnimateTransform }
     * {@link ClipPath }
     * {@link Marker }
     * {@link G }
     * {@link AnimateMotion }
     * {@link Mask }
     * {@link Cursor }
     * {@link AltGlyphDef }
     * {@link ColorProfile }
     * {@link A }
     * {@link Polygon }
     * {@link Metadata }
     * {@link Set }
     * {@link FontFace }
     * {@link Line }
     * {@link Style }
     * {@link Polyline }
     * {@link LinearGradient }
     * {@link Path }
     * {@link Rect }
     * 
     * 
     */
    public List<Object> getDescOrTitleOrMetadata() {
        if (descOrTitleOrMetadata == null) {
            descOrTitleOrMetadata = new ArrayList<Object>();
        }
        return this.descOrTitleOrMetadata;
    }

    /**
     * Gets the value of the unicode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnicode() {
        return unicode;
    }

    /**
     * Sets the value of the unicode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnicode(String value) {
        this.unicode = value;
    }

    /**
     * Gets the value of the glyphName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlyphName() {
        return glyphName;
    }

    /**
     * Sets the value of the glyphName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlyphName(String value) {
        this.glyphName = value;
    }

    /**
     * Gets the value of the d property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getD() {
        return d;
    }

    /**
     * Sets the value of the d property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setD(String value) {
        this.d = value;
    }

    /**
     * Gets the value of the orientation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * Sets the value of the orientation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrientation(String value) {
        this.orientation = value;
    }

    /**
     * Gets the value of the arabicForm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArabicForm() {
        return arabicForm;
    }

    /**
     * Sets the value of the arabicForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArabicForm(String value) {
        this.arabicForm = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the horizAdvX property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHorizAdvX() {
        return horizAdvX;
    }

    /**
     * Sets the value of the horizAdvX property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHorizAdvX(String value) {
        this.horizAdvX = value;
    }

    /**
     * Gets the value of the vertOriginX property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVertOriginX() {
        return vertOriginX;
    }

    /**
     * Sets the value of the vertOriginX property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVertOriginX(String value) {
        this.vertOriginX = value;
    }

    /**
     * Gets the value of the vertOriginY property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVertOriginY() {
        return vertOriginY;
    }

    /**
     * Sets the value of the vertOriginY property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVertOriginY(String value) {
        this.vertOriginY = value;
    }

    /**
     * Gets the value of the vertAdvY property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVertAdvY() {
        return vertAdvY;
    }

    /**
     * Sets the value of the vertAdvY property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVertAdvY(String value) {
        this.vertAdvY = value;
    }

    /**
     * Gets the value of the floodColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloodColor() {
        return floodColor;
    }

    /**
     * Sets the value of the floodColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloodColor(String value) {
        this.floodColor = value;
    }

    /**
     * Gets the value of the floodOpacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloodOpacity() {
        return floodOpacity;
    }

    /**
     * Sets the value of the floodOpacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloodOpacity(String value) {
        this.floodOpacity = value;
    }

    /**
     * Gets the value of the lightingColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLightingColor() {
        return lightingColor;
    }

    /**
     * Sets the value of the lightingColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLightingColor(String value) {
        this.lightingColor = value;
    }

}
