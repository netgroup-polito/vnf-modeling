
package it.polito.vnfchainverification;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="source" type="{http://www.example.org/checkisolation}VNFName"/>
 *         &lt;element name="destination" type="{http://www.example.org/checkisolation}VNFName"/>
 *         &lt;element name="VNF" type="{http://www.example.org/checkisolation}VNF" maxOccurs="unbounded" minOccurs="2"/>
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
    "source",
    "destination",
    "vnf"
})
@XmlRootElement(name = "checkIsolationProperty")
public class CheckIsolationProperty {

    @XmlElement(required = true)
    protected VNFName source;
    @XmlElement(required = true)
    protected VNFName destination;
    @XmlElement(name = "VNF", required = true)
    protected List<VNF> vnf;

    /**
     * \u83b7\u53d6source\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link VNFName }
     *     
     */
    public VNFName getSource() {
        return source;
    }

    /**
     * \u8bbe\u7f6esource\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link VNFName }
     *     
     */
    public void setSource(VNFName value) {
        this.source = value;
    }

    /**
     * \u83b7\u53d6destination\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link VNFName }
     *     
     */
    public VNFName getDestination() {
        return destination;
    }

    /**
     * \u8bbe\u7f6edestination\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link VNFName }
     *     
     */
    public void setDestination(VNFName value) {
        this.destination = value;
    }

    /**
     * Gets the value of the vnf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vnf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVNF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VNF }
     * 
     * 
     */
    public List<VNF> getVNF() {
        if (vnf == null) {
            vnf = new ArrayList<VNF>();
        }
        return this.vnf;
    }

}
