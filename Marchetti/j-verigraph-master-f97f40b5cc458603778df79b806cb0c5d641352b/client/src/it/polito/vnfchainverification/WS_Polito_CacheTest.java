package it.polito.vnfchainverification;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;
/**
 * 
 * @author Giacomo Costantini
 * <p/>
 * Custom test	<p/>
 * 
 * | A | <------> | CACHE | <------> | B |
 *
 */
public class WS_Polito_CacheTest {

	public WS_Polito_CacheTest() {
		try
		  {
			VNFChainVerificationService service = new VNFChainVerificationService();
			VNFChainVerification proxy = service.getVNFChainVerificationPort();
			
			System.out.println ("Ready to invoke remote operation");
		
			VNF.EndHost a = new VNF.EndHost();
			VNF vnf1 = new VNF();
			VNFName vnfName1 = new VNFName();
			VNFIp vnfIp1 = new VNFIp();
			vnfName1.setId("a");
			vnfIp1.setId("ip_a");
			vnf1.setName(vnfName1);
			vnf1.getIPs().add(vnfIp1);
			vnf1.setAclFirewallOrDumbNodeOrEndHost(a);			

			VNF.EndHost b = new VNF.EndHost();
			VNF vnf2 = new VNF();
			VNFName vnfName2 = new VNFName();
			VNFIp vnfIp2 = new VNFIp();
			vnfName2.setId("b");
			vnfIp2.setId("ip_b");
			vnf2.setName(vnfName2);
			vnf2.getIPs().add(vnfIp2);
			vnf2.setAclFirewallOrDumbNodeOrEndHost(b);			

			VNF.PolitoCache politoCache = new VNF.PolitoCache();
			VNF vnf3 = new VNF();
			VNFName vnfName3 = new VNFName();
			VNFIp vnfIp3 = new VNFIp();
			vnfName3.setId("politoCache");
			vnfIp3.setId("ip_cache");
			vnf3.setName(vnfName3);
			vnf3.getIPs().add(vnfIp3);
			politoCache.getInternalNodes().add(vnfName1);
			vnf3.setAclFirewallOrDumbNodeOrEndHost(politoCache);
			
		
			VNF.RoutingTable rt1 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry1 = new VNF.RoutingTable.Entry();
			entry1.setIP(vnfIp1); entry1.setName(vnfName3);
			VNF.RoutingTable.Entry entry2 = new VNF.RoutingTable.Entry();
			entry2.setIP(vnfIp2); entry2.setName(vnfName3);
			rt1.getEntry().add(entry1);
			rt1.getEntry().add(entry2);
			
			VNF.RoutingTable rt2 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry3 = new VNF.RoutingTable.Entry();
			entry3.setIP(vnfIp1); entry3.setName(vnfName1);
			VNF.RoutingTable.Entry entry4 = new VNF.RoutingTable.Entry();
			entry4.setIP(vnfIp2); entry4.setName(vnfName2);
			rt2.getEntry().add(entry3);
			rt2.getEntry().add(entry4);
			
			vnf1.setRoutingTable(rt1);
			vnf2.setRoutingTable(rt1);
			vnf3.setRoutingTable(rt2);
			
			List<VNF> vnf = new ArrayList<VNF>();
			vnf.add(vnf1);
			vnf.add(vnf2);
			vnf.add(vnf3);

			
			boolean ret = proxy.checkIsolationProperty(vnfName1,vnfName2,vnf);
	    	if (ret) {
	    	 	System.out.println ("SATISFIED");
	    	}
	    	else
	    	 	System.out.println ("UNSATISFIED");
	  		
		  }catch(SOAPFaultException e2){
				 System.out.println ("Validation error: "+e2);
		  }catch(MalformedArgument_Exception e5){
				 System.out.println ("MalformedArgument error: "+e5);
		  }catch(Z3Error_Exception e1){
				 System.out.println ("Z3 error: "+e1);
		  }catch (WebServiceException_Exception e3){
				 System.out.println ("WebService operation error: "+e3);
		  }catch (Exception e){
			  System.out.println ("Fatal error: "+ e);
		  }
	}
	
	    public static void main(String[] args) {
			WS_Polito_CacheTest vnfClient = new WS_Polito_CacheTest();
	    }
}
