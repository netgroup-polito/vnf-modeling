
package it.polito.vnfchainverification;

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
 *         &lt;element name="Satisfied" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "satisfied"
})
@XmlRootElement(name = "checkIsolationPropertyResponse")
public class CheckIsolationPropertyResponse {

    @XmlElement(name = "Satisfied")
    protected boolean satisfied;

    /**
     * \u83b7\u53d6satisfied\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public boolean isSatisfied() {
        return satisfied;
    }

    /**
     * \u8bbe\u7f6esatisfied\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public void setSatisfied(boolean value) {
        this.satisfied = value;
    }

}
