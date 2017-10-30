
package it.polito.vnfchainverification;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VNF complex type\u7684 Java \u7c7b\u3002
 * 
 * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
 * 
 * <pre>
 * &lt;complexType name="VNF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.example.org/checkisolation}VNFName"/>
 *         &lt;element name="IPs" type="{http://www.example.org/checkisolation}VNFIp" maxOccurs="unbounded"/>
 *         &lt;choice>
 *           &lt;element name="AclFirewall">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Acl" maxOccurs="unbounded">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IP1" type="{http://www.example.org/checkisolation}VNFIp"/>
 *                               &lt;element name="IP2" type="{http://www.example.org/checkisolation}VNFIp"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="DumbNode">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="EndHost">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoAntispam">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Blacklist" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoCache">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="InternalNodes" type="{http://www.example.org/checkisolation}VNFName" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoErrFunction">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoMailClient">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoMailServer">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoNat">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="InternalIPs" type="{http://www.example.org/checkisolation}VNFIp" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoNF">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="NFRule" maxOccurs="unbounded">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IP1" type="{http://www.example.org/checkisolation}VNFIp"/>
 *                               &lt;element name="IP2" type="{http://www.example.org/checkisolation}VNFIp"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoWebClient">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="ServerIP" type="{http://www.example.org/checkisolation}VNFIp"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PolitoWebServer">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="RoutingTable">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="IP" type="{http://www.example.org/checkisolation}VNFIp"/>
 *                             &lt;element name="Name" type="{http://www.example.org/checkisolation}VNFName"/>
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
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VNF", propOrder = {
    "name",
    "iPs",
    "aclFirewallOrDumbNodeOrEndHost",
    "routingTable"
})
public class VNF {

    @XmlElement(name = "Name", required = true)
    protected VNFName name;
    @XmlElement(name = "IPs", required = true)
    protected List<VNFIp> iPs;
    @XmlElements({
        @XmlElement(name = "AclFirewall", type = VNF.AclFirewall.class),
        @XmlElement(name = "DumbNode", type = VNF.DumbNode.class),
        @XmlElement(name = "EndHost", type = VNF.EndHost.class),
        @XmlElement(name = "PolitoAntispam", type = VNF.PolitoAntispam.class),
        @XmlElement(name = "PolitoCache", type = VNF.PolitoCache.class),
        @XmlElement(name = "PolitoErrFunction", type = VNF.PolitoErrFunction.class),
        @XmlElement(name = "PolitoMailClient", type = VNF.PolitoMailClient.class),
        @XmlElement(name = "PolitoMailServer", type = VNF.PolitoMailServer.class),
        @XmlElement(name = "PolitoNat", type = VNF.PolitoNat.class),
        @XmlElement(name = "PolitoNF", type = VNF.PolitoNF.class),
        @XmlElement(name = "PolitoWebClient", type = VNF.PolitoWebClient.class),
        @XmlElement(name = "PolitoWebServer", type = VNF.PolitoWebServer.class)
    })
    protected Object aclFirewallOrDumbNodeOrEndHost;
    @XmlElement(name = "RoutingTable", required = true)
    protected VNF.RoutingTable routingTable;

    /**
     * \u83b7\u53d6name\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link VNFName }
     *     
     */
    public VNFName getName() {
        return name;
    }

    /**
     * \u8bbe\u7f6ename\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link VNFName }
     *     
     */
    public void setName(VNFName value) {
        this.name = value;
    }

    /**
     * Gets the value of the iPs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iPs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIPs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VNFIp }
     * 
     * 
     */
    public List<VNFIp> getIPs() {
        if (iPs == null) {
            iPs = new ArrayList<VNFIp>();
        }
        return this.iPs;
    }

    /**
     * \u83b7\u53d6aclFirewallOrDumbNodeOrEndHost\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link VNF.AclFirewall }
     *     {@link VNF.DumbNode }
     *     {@link VNF.EndHost }
     *     {@link VNF.PolitoAntispam }
     *     {@link VNF.PolitoCache }
     *     {@link VNF.PolitoErrFunction }
     *     {@link VNF.PolitoMailClient }
     *     {@link VNF.PolitoMailServer }
     *     {@link VNF.PolitoNat }
     *     {@link VNF.PolitoNF }
     *     {@link VNF.PolitoWebClient }
     *     {@link VNF.PolitoWebServer }
     *     
     */
    public Object getAclFirewallOrDumbNodeOrEndHost() {
        return aclFirewallOrDumbNodeOrEndHost;
    }

    /**
     * \u8bbe\u7f6eaclFirewallOrDumbNodeOrEndHost\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link VNF.AclFirewall }
     *     {@link VNF.DumbNode }
     *     {@link VNF.EndHost }
     *     {@link VNF.PolitoAntispam }
     *     {@link VNF.PolitoCache }
     *     {@link VNF.PolitoErrFunction }
     *     {@link VNF.PolitoMailClient }
     *     {@link VNF.PolitoMailServer }
     *     {@link VNF.PolitoNat }
     *     {@link VNF.PolitoNF }
     *     {@link VNF.PolitoWebClient }
     *     {@link VNF.PolitoWebServer }
     *     
     */
    public void setAclFirewallOrDumbNodeOrEndHost(Object value) {
        this.aclFirewallOrDumbNodeOrEndHost = value;
    }

    /**
     * \u83b7\u53d6routingTable\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link VNF.RoutingTable }
     *     
     */
    public VNF.RoutingTable getRoutingTable() {
        return routingTable;
    }

    /**
     * \u8bbe\u7f6eroutingTable\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link VNF.RoutingTable }
     *     
     */
    public void setRoutingTable(VNF.RoutingTable value) {
        this.routingTable = value;
    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Acl" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IP1" type="{http://www.example.org/checkisolation}VNFIp"/>
     *                   &lt;element name="IP2" type="{http://www.example.org/checkisolation}VNFIp"/>
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
        "acl"
    })
    public static class AclFirewall {

        @XmlElement(name = "Acl", required = true)
        protected List<VNF.AclFirewall.Acl> acl;

        /**
         * Gets the value of the acl property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the acl property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAcl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VNF.AclFirewall.Acl }
         * 
         * 
         */
        public List<VNF.AclFirewall.Acl> getAcl() {
            if (acl == null) {
                acl = new ArrayList<VNF.AclFirewall.Acl>();
            }
            return this.acl;
        }


        /**
         * <p>anonymous complex type\u7684 Java \u7c7b\u3002
         * 
         * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="IP1" type="{http://www.example.org/checkisolation}VNFIp"/>
         *         &lt;element name="IP2" type="{http://www.example.org/checkisolation}VNFIp"/>
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
            "ip1",
            "ip2"
        })
        public static class Acl {

            @XmlElement(name = "IP1", required = true)
            protected VNFIp ip1;
            @XmlElement(name = "IP2", required = true)
            protected VNFIp ip2;

            /**
             * \u83b7\u53d6ip1\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @return
             *     possible object is
             *     {@link VNFIp }
             *     
             */
            public VNFIp getIP1() {
                return ip1;
            }

            /**
             * \u8bbe\u7f6eip1\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @param value
             *     allowed object is
             *     {@link VNFIp }
             *     
             */
            public void setIP1(VNFIp value) {
                this.ip1 = value;
            }

            /**
             * \u83b7\u53d6ip2\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @return
             *     possible object is
             *     {@link VNFIp }
             *     
             */
            public VNFIp getIP2() {
                return ip2;
            }

            /**
             * \u8bbe\u7f6eip2\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @param value
             *     allowed object is
             *     {@link VNFIp }
             *     
             */
            public void setIP2(VNFIp value) {
                this.ip2 = value;
            }

        }

    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DumbNode {


    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class EndHost {


    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Blacklist" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
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
        "blacklist"
    })
    public static class PolitoAntispam {

        @XmlElement(name = "Blacklist", type = Integer.class)
        protected List<Integer> blacklist;

        /**
         * Gets the value of the blacklist property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the blacklist property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBlacklist().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getBlacklist() {
            if (blacklist == null) {
                blacklist = new ArrayList<Integer>();
            }
            return this.blacklist;
        }

    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="InternalNodes" type="{http://www.example.org/checkisolation}VNFName" maxOccurs="unbounded"/>
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
        "internalNodes"
    })
    public static class PolitoCache {

        @XmlElement(name = "InternalNodes", required = true)
        protected List<VNFName> internalNodes;

        /**
         * Gets the value of the internalNodes property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the internalNodes property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInternalNodes().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VNFName }
         * 
         * 
         */
        public List<VNFName> getInternalNodes() {
            if (internalNodes == null) {
                internalNodes = new ArrayList<VNFName>();
            }
            return this.internalNodes;
        }

    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class PolitoErrFunction {


    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class PolitoMailClient {


    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class PolitoMailServer {


    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="NFRule" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IP1" type="{http://www.example.org/checkisolation}VNFIp"/>
     *                   &lt;element name="IP2" type="{http://www.example.org/checkisolation}VNFIp"/>
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
        "nfRule"
    })
    public static class PolitoNF {

        @XmlElement(name = "NFRule", required = true)
        protected List<VNF.PolitoNF.NFRule> nfRule;

        /**
         * Gets the value of the nfRule property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the nfRule property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNFRule().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VNF.PolitoNF.NFRule }
         * 
         * 
         */
        public List<VNF.PolitoNF.NFRule> getNFRule() {
            if (nfRule == null) {
                nfRule = new ArrayList<VNF.PolitoNF.NFRule>();
            }
            return this.nfRule;
        }


        /**
         * <p>anonymous complex type\u7684 Java \u7c7b\u3002
         * 
         * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="IP1" type="{http://www.example.org/checkisolation}VNFIp"/>
         *         &lt;element name="IP2" type="{http://www.example.org/checkisolation}VNFIp"/>
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
            "ip1",
            "ip2"
        })
        public static class NFRule {

            @XmlElement(name = "IP1", required = true)
            protected VNFIp ip1;
            @XmlElement(name = "IP2", required = true)
            protected VNFIp ip2;

            /**
             * \u83b7\u53d6ip1\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @return
             *     possible object is
             *     {@link VNFIp }
             *     
             */
            public VNFIp getIP1() {
                return ip1;
            }

            /**
             * \u8bbe\u7f6eip1\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @param value
             *     allowed object is
             *     {@link VNFIp }
             *     
             */
            public void setIP1(VNFIp value) {
                this.ip1 = value;
            }

            /**
             * \u83b7\u53d6ip2\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @return
             *     possible object is
             *     {@link VNFIp }
             *     
             */
            public VNFIp getIP2() {
                return ip2;
            }

            /**
             * \u8bbe\u7f6eip2\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @param value
             *     allowed object is
             *     {@link VNFIp }
             *     
             */
            public void setIP2(VNFIp value) {
                this.ip2 = value;
            }

        }

    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="InternalIPs" type="{http://www.example.org/checkisolation}VNFIp" maxOccurs="unbounded"/>
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
        "internalIPs"
    })
    public static class PolitoNat {

        @XmlElement(name = "InternalIPs", required = true)
        protected List<VNFIp> internalIPs;

        /**
         * Gets the value of the internalIPs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the internalIPs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInternalIPs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VNFIp }
         * 
         * 
         */
        public List<VNFIp> getInternalIPs() {
            if (internalIPs == null) {
                internalIPs = new ArrayList<VNFIp>();
            }
            return this.internalIPs;
        }

    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ServerIP" type="{http://www.example.org/checkisolation}VNFIp"/>
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
        "serverIP"
    })
    public static class PolitoWebClient {

        @XmlElement(name = "ServerIP", required = true)
        protected VNFIp serverIP;

        /**
         * \u83b7\u53d6serverIP\u5c5e\u6027\u7684\u503c\u3002
         * 
         * @return
         *     possible object is
         *     {@link VNFIp }
         *     
         */
        public VNFIp getServerIP() {
            return serverIP;
        }

        /**
         * \u8bbe\u7f6eserverIP\u5c5e\u6027\u7684\u503c\u3002
         * 
         * @param value
         *     allowed object is
         *     {@link VNFIp }
         *     
         */
        public void setServerIP(VNFIp value) {
            this.serverIP = value;
        }

    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class PolitoWebServer {


    }


    /**
     * <p>anonymous complex type\u7684 Java \u7c7b\u3002
     * 
     * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IP" type="{http://www.example.org/checkisolation}VNFIp"/>
     *                   &lt;element name="Name" type="{http://www.example.org/checkisolation}VNFName"/>
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
        "entry"
    })
    public static class RoutingTable {

        @XmlElement(name = "Entry")
        protected List<VNF.RoutingTable.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VNF.RoutingTable.Entry }
         * 
         * 
         */
        public List<VNF.RoutingTable.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<VNF.RoutingTable.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>anonymous complex type\u7684 Java \u7c7b\u3002
         * 
         * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="IP" type="{http://www.example.org/checkisolation}VNFIp"/>
         *         &lt;element name="Name" type="{http://www.example.org/checkisolation}VNFName"/>
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
            "ip",
            "name"
        })
        public static class Entry {

            @XmlElement(name = "IP", required = true)
            protected VNFIp ip;
            @XmlElement(name = "Name", required = true)
            protected VNFName name;

            /**
             * \u83b7\u53d6ip\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @return
             *     possible object is
             *     {@link VNFIp }
             *     
             */
            public VNFIp getIP() {
                return ip;
            }

            /**
             * \u8bbe\u7f6eip\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @param value
             *     allowed object is
             *     {@link VNFIp }
             *     
             */
            public void setIP(VNFIp value) {
                this.ip = value;
            }

            /**
             * \u83b7\u53d6name\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @return
             *     possible object is
             *     {@link VNFName }
             *     
             */
            public VNFName getName() {
                return name;
            }

            /**
             * \u8bbe\u7f6ename\u5c5e\u6027\u7684\u503c\u3002
             * 
             * @param value
             *     allowed object is
             *     {@link VNFName }
             *     
             */
            public void setName(VNFName value) {
                this.name = value;
            }

        }

    }

}
