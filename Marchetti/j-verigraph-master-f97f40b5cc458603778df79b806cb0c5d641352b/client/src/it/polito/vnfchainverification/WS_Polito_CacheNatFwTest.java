package it.polito.vnfchainverification;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;
/**
 * 
 * @author Giacomo Costantini
 * <p/>
 * Cache - Nat - Fw     test												<p/>
 * 
 *    | HOST_A | ----| CACHE |----- | NAT | ----| FW |----- | HOST_B |		<p/>
 *    ...................|													<p/>
 *    | HOST_C | --------													<p/>
 *
 */
public class WS_Polito_CacheNatFwTest {

	public WS_Polito_CacheNatFwTest() {
		try
		  {
			VNFChainVerificationService service = new VNFChainVerificationService();
			VNFChainVerification proxy = service.getVNFChainVerificationPort();
			
			System.out.println ("Ready to invoke remote operation");

			
			VNF.PolitoWebServer hostB = new VNF.PolitoWebServer();
			VNF vnf2 = new VNF();
			VNFName vnfName2 = new VNFName();
			VNFIp vnfIp2 = new VNFIp();
			vnfName2.setId("hostB");
			vnfIp2.setId("ip_hostB");
			vnf2.setName(vnfName2);
			vnf2.getIPs().add(vnfIp2);
			vnf2.setAclFirewallOrDumbNodeOrEndHost(hostB);			

			
			VNF.PolitoWebClient hostA = new VNF.PolitoWebClient();
			VNF vnf1 = new VNF();
			VNFName vnfName1 = new VNFName();
			VNFIp vnfIp1 = new VNFIp();
			vnfName1.setId("hostA");
			vnfIp1.setId("ip_hostA");
			vnf1.setName(vnfName1);
			vnf1.getIPs().add(vnfIp1);
			hostA.setServerIP(vnfIp2);
			vnf1.setAclFirewallOrDumbNodeOrEndHost(hostA);			
			
			
			VNF.PolitoWebServer hostC = new VNF.PolitoWebServer();
			VNF vnf3 = new VNF();
			VNFName vnfName3 = new VNFName();
			VNFIp vnfIp3 = new VNFIp();
			vnfName3.setId("hostC");
			vnfIp3.setId("ip_hostC");
			vnf3.setName(vnfName3);
			vnf3.getIPs().add(vnfIp3);
			vnf3.setAclFirewallOrDumbNodeOrEndHost(hostC);			
		
			
			VNF.PolitoCache politoCache = new VNF.PolitoCache();
			VNF vnf4 = new VNF();
			VNFName vnfName4 = new VNFName();
			VNFIp vnfIp4 = new VNFIp();
			vnfName4.setId("politoCache");
			vnfIp4.setId("ip_politoCache");
			vnf4.setName(vnfName4);
			vnf4.getIPs().add(vnfIp4);
			politoCache.getInternalNodes().add(vnfName1);
			vnf4.setAclFirewallOrDumbNodeOrEndHost(politoCache);
		
			VNF.PolitoNat politoNat = new VNF.PolitoNat();
			VNF vnf5 = new VNF();
			VNFName vnfName5 = new VNFName();
			VNFIp vnfIp5 = new VNFIp();
			vnfName5.setId("politoNat");
			vnfIp5.setId("ip_politoNat");
			vnf5.setName(vnfName5);
			vnf5.getIPs().add(vnfIp5);
			politoNat.getInternalIPs().add(vnfIp1);
			politoNat.getInternalIPs().add(vnfIp3);
			vnf5.setAclFirewallOrDumbNodeOrEndHost(politoNat);
			
			
			VNF.AclFirewall politoFw = new VNF.AclFirewall();
			VNF vnf6 = new VNF();
			VNFName vnfName6 = new VNFName();
			VNFIp vnfIp6 = new VNFIp();
			vnfName6.setId("politoFw");
			vnfIp6.setId("ip_politoFw");
			vnf6.setName(vnfName6);
			vnf6.getIPs().add(vnfIp6);
			VNF.AclFirewall.Acl acl = new VNF.AclFirewall.Acl();
			acl.setIP1(vnfIp5); acl.setIP2(vnfIp2);
			politoFw.getAcl().add(acl);
			vnf6.setAclFirewallOrDumbNodeOrEndHost(politoFw);

			VNF.RoutingTable rt1 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry1 = new VNF.RoutingTable.Entry();
			entry1.setIP(vnfIp2); entry1.setName(vnfName4);
			VNF.RoutingTable.Entry entry2 = new VNF.RoutingTable.Entry();
			entry2.setIP(vnfIp3); entry2.setName(vnfName4);
			VNF.RoutingTable.Entry entry3 = new VNF.RoutingTable.Entry();
			entry3.setIP(vnfIp4); entry3.setName(vnfName4);
			VNF.RoutingTable.Entry entry4 = new VNF.RoutingTable.Entry();
			entry4.setIP(vnfIp5); entry4.setName(vnfName4);
			VNF.RoutingTable.Entry entry5 = new VNF.RoutingTable.Entry();
			entry5.setIP(vnfIp6); entry5.setName(vnfName4);
			rt1.getEntry().add(entry1);	
			rt1.getEntry().add(entry2); 
			rt1.getEntry().add(entry3);	
			rt1.getEntry().add(entry4); 
			rt1.getEntry().add(entry5);

			
			
			VNF.RoutingTable rt2 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry6 = new VNF.RoutingTable.Entry();
			entry6.setIP(vnfIp1); entry6.setName(vnfName6);
			VNF.RoutingTable.Entry entry7 = new VNF.RoutingTable.Entry();
			entry7.setIP(vnfIp3); entry7.setName(vnfName6);
			VNF.RoutingTable.Entry entry8 = new VNF.RoutingTable.Entry();
			entry8.setIP(vnfIp4); entry8.setName(vnfName6);
			VNF.RoutingTable.Entry entry9 = new VNF.RoutingTable.Entry();
			entry9.setIP(vnfIp5); entry9.setName(vnfName6);
			VNF.RoutingTable.Entry entry10 = new VNF.RoutingTable.Entry();
			entry10.setIP(vnfIp6); entry10.setName(vnfName6);
			rt2.getEntry().add(entry6);	
			rt2.getEntry().add(entry7); 
			rt2.getEntry().add(entry8);	
			rt2.getEntry().add(entry9); 
			rt2.getEntry().add(entry10);

			
			
			VNF.RoutingTable rt3 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry11 = new VNF.RoutingTable.Entry();
			entry11.setIP(vnfIp2); entry11.setName(vnfName4);
			VNF.RoutingTable.Entry entry12 = new VNF.RoutingTable.Entry();
			entry12.setIP(vnfIp1); entry12.setName(vnfName4);
			VNF.RoutingTable.Entry entry13 = new VNF.RoutingTable.Entry();
			entry13.setIP(vnfIp4); entry13.setName(vnfName4);
			VNF.RoutingTable.Entry entry14 = new VNF.RoutingTable.Entry();
			entry14.setIP(vnfIp5); entry14.setName(vnfName4);
			VNF.RoutingTable.Entry entry15 = new VNF.RoutingTable.Entry();
			entry15.setIP(vnfIp6); entry15.setName(vnfName4);
			rt3.getEntry().add(entry11);	
			rt3.getEntry().add(entry12); 
			rt3.getEntry().add(entry13);	
			rt3.getEntry().add(entry14); 
			rt3.getEntry().add(entry15);
			
			VNF.RoutingTable rt4 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry16 = new VNF.RoutingTable.Entry();
			entry16.setIP(vnfIp1); entry16.setName(vnfName1);
			VNF.RoutingTable.Entry entry17 = new VNF.RoutingTable.Entry();
			entry17.setIP(vnfIp2); entry17.setName(vnfName5);
			VNF.RoutingTable.Entry entry18 = new VNF.RoutingTable.Entry();
			entry18.setIP(vnfIp3); entry18.setName(vnfName3);
			VNF.RoutingTable.Entry entry19 = new VNF.RoutingTable.Entry();
			entry19.setIP(vnfIp5); entry19.setName(vnfName5);
			VNF.RoutingTable.Entry entry20 = new VNF.RoutingTable.Entry();
			entry20.setIP(vnfIp6); entry20.setName(vnfName5);
			rt4.getEntry().add(entry16);	
			rt4.getEntry().add(entry17); 
			rt4.getEntry().add(entry18);	
			rt4.getEntry().add(entry19); 
			rt4.getEntry().add(entry20);

			
			VNF.RoutingTable rt5 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry21 = new VNF.RoutingTable.Entry();
			entry21.setIP(vnfIp1); entry21.setName(vnfName4);
			VNF.RoutingTable.Entry entry22 = new VNF.RoutingTable.Entry();
			entry22.setIP(vnfIp2); entry22.setName(vnfName6);
			VNF.RoutingTable.Entry entry23 = new VNF.RoutingTable.Entry();
			entry23.setIP(vnfIp3); entry23.setName(vnfName4);
			VNF.RoutingTable.Entry entry24 = new VNF.RoutingTable.Entry();
			entry24.setIP(vnfIp4); entry24.setName(vnfName4);
			VNF.RoutingTable.Entry entry25 = new VNF.RoutingTable.Entry();
			entry25.setIP(vnfIp6); entry25.setName(vnfName6);
			rt5.getEntry().add(entry21);	
			rt5.getEntry().add(entry22); 
			rt5.getEntry().add(entry23);	
			rt5.getEntry().add(entry24); 
			rt5.getEntry().add(entry25);
			
			VNF.RoutingTable rt6 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry26 = new VNF.RoutingTable.Entry();
			entry26.setIP(vnfIp2); entry26.setName(vnfName2);
			VNF.RoutingTable.Entry entry27 = new VNF.RoutingTable.Entry();
			entry27.setIP(vnfIp1); entry27.setName(vnfName5);
			VNF.RoutingTable.Entry entry28 = new VNF.RoutingTable.Entry();
			entry28.setIP(vnfIp3); entry28.setName(vnfName5);
			VNF.RoutingTable.Entry entry29 = new VNF.RoutingTable.Entry();
			entry29.setIP(vnfIp5); entry29.setName(vnfName5);
			VNF.RoutingTable.Entry entry30 = new VNF.RoutingTable.Entry();
			entry30.setIP(vnfIp4); entry30.setName(vnfName5);
			rt6.getEntry().add(entry26);	
			rt6.getEntry().add(entry27); 
			rt6.getEntry().add(entry28);	
			rt6.getEntry().add(entry29); 
			rt6.getEntry().add(entry30);

			
			vnf1.setRoutingTable(rt1);
			vnf2.setRoutingTable(rt2);
			vnf3.setRoutingTable(rt3);
			vnf4.setRoutingTable(rt4);
			vnf5.setRoutingTable(rt5);
			vnf6.setRoutingTable(rt6);
			
			
			List<VNF> vnf = new ArrayList<VNF>();
			vnf.add(vnf1);
			vnf.add(vnf2);
			vnf.add(vnf3);
			vnf.add(vnf4);
			vnf.add(vnf5);
			vnf.add(vnf6);
			
			
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
			WS_Polito_CacheNatFwTest vnfClient = new WS_Polito_CacheNatFwTest();
	    }
}
