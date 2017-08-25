//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.05 at 09:25:48 PM EDT 
//


package net.massdriver.orbit.model.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.massdriver.orbit.magnet.MagnetLink;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="channel">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                   &lt;element name="item" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="guid">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="isPermaLink" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="pubDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "channel"
})
@XmlRootElement(name = "rss")
public class Rss {

    @XmlElement(required = true)
    protected Rss.Channel channel;
    @XmlAttribute(name = "version")
    protected BigDecimal version;
    
    
    public Rss() {
    }

    public Rss(net.massdriver.orbit.model.horriblesubs.Rss rss) {
      setChannel(new Rss.Channel(rss.getChannel()));
      setVersion(new BigDecimal(rss.getVersion()));
    }

    public Rss(net.massdriver.orbit.model.tokyotosho.Rss rss) {
      setChannel(new Rss.Channel(rss.getChannel()));
      setVersion(rss.getVersion());
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link Rss.Channel }
     *     
     */
    public Rss.Channel getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rss.Channel }
     *     
     */
    public void setChannel(Rss.Channel value) {
        this.channel = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
     *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="guid">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="isPermaLink" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="pubDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "title",
        "description",
        "link",
        "item"
    })
    public static class Channel {

        @XmlElement(required = true)
        protected String title;
        @XmlElement(required = true)
        protected String description;
        @XmlElement(required = true)
        @XmlSchemaType(name = "anyURI")
        protected String link;
        protected List<Rss.Channel.Item> item;
        
        public Channel() {
        }

        public Channel(net.massdriver.orbit.model.horriblesubs.Rss.Channel channel) {
          setTitle(channel.getTitle());
          setDescription(channel.getDescription());
          setLink(channel.getLink());
          setItem(new ArrayList<Rss.Channel.Item>());
          for (net.massdriver.orbit.model.horriblesubs.Rss.Channel.Item item : channel.getItem()) {
            getItem().add(new Rss.Channel.Item(item));
          }
        }

        public Channel(net.massdriver.orbit.model.tokyotosho.Rss.Channel channel) {
          setTitle(channel.getTitle());
          setDescription(channel.getDescription());
          setLink(channel.getLink());
          setItem(new ArrayList<Rss.Channel.Item>());
          for (net.massdriver.orbit.model.tokyotosho.Rss.Channel.Item item : channel.getItem()) {
            getItem().add(new Rss.Channel.Item(item));
          }
        }

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitle(String value) {
            this.title = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the link property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLink() {
            return link;
        }

        /**
         * Sets the value of the link property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLink(String value) {
            this.link = value;
        }

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Rss.Channel.Item }
         * 
         * 
         */
        public List<Rss.Channel.Item> getItem() {
            if (item == null) {
                item = new ArrayList<Rss.Channel.Item>();
            }
            return this.item;
        }

        public void setItem(List<Rss.Channel.Item> item) {
            if (item == null) {
                item = new ArrayList<Rss.Channel.Item>();
            }
            this.item = item;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="guid">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="isPermaLink" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="pubDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "title",
            "link",
            "guid",
            "pubDate"
        })
        public static class Item {

            @XmlElement(required = true)
            protected String title;
            @XmlElement(required = true)
            protected String link;
            @XmlElement(required = true)
            protected String category;
            //@XmlElement(required = true)
            //protected Rss.Channel.Item.Guid guid;
            @XmlElement(required = true)
            protected String pubDate;
            
            public Item() {
            }

            public Item(net.massdriver.orbit.model.tokyotosho.Rss.Channel.Item item) {
              String html = item.getDescription();
              Document doc = Jsoup.parseBodyFragment(html);
              Elements links = doc.select("a[href]");
              for(Element link : links) {
                String linkString = link.attr("href");
                if (new MagnetLink(linkString).matches()) {
                  setLink(link.attr("href"));
                }
              }
              
              setTitle(item.getTitle());
              //setLink(item.getDescription());
              setCategory(item.getCategory());
              setPubDate((item.getPubDate()!=null)? item.getPubDate().toString(): (new Date().toString()));
            }

            public Item(net.massdriver.orbit.model.horriblesubs.Rss.Channel.Item item) {
              setTitle(item.getTitle());
              setLink(item.getLink());
              setCategory("Anime");
              setPubDate(item.getPubDate());
            }

            /**
             * Gets the value of the title property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTitle() {
                return title;
            }

            /**
             * Sets the value of the title property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTitle(String value) {
                this.title = value;
            }

            /**
             * Gets the value of the link property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLink() {
                return link;
            }

            /**
             * Sets the value of the link property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLink(String value) {
                this.link = value;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }



            /**
             * Gets the value of the guid property.
             * 
             * @return
             *     possible object is
             *     {@link Rss.Channel.Item.Guid }
             *     
             */
            //public Rss.Channel.Item.Guid getGuid() {
                //return guid;
            //}

            /**
             * Sets the value of the guid property.
             * 
             * @param value
             *     allowed object is
             *     {@link Rss.Channel.Item.Guid }
             *     
             */
            //public void setGuid(Rss.Channel.Item.Guid value) {
                //this.guid = value;
            //}

            /**
             * Gets the value of the pubDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPubDate() {
                return pubDate;
            }

            /**
             * Sets the value of the pubDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPubDate(String value) {
                this.pubDate = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="isPermaLink" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Guid {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "isPermaLink")
                protected String isPermaLink;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the isPermaLink property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getIsPermaLink() {
                    return isPermaLink;
                }

                /**
                 * Sets the value of the isPermaLink property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setIsPermaLink(String value) {
                    this.isPermaLink = value;
                }

            }

        }

    }

}
