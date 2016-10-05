package it.polito.vnfchainverification;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;
/** 
 * @author Giacomo Costantini
 * <p/>
 * Antispam test													<p/>
 * 
 *| CLIENT | --------- | ANTISPAM | --------- | MAIL SERVER |		<p/>
 *..........................|										<p/>
 *...................| ERR FUNCTION |								<p/>
*/
public class WS_Polito_AntispamTest {

	public WS_Polito_AntispamTest() {
		try
		  {
			VNFChainVerificationService service = new VNFChainVerificationService();
			VNFChainVerification proxy = service.getVNFChainVerificationPort();
			
			System.out.println ("Ready to invoke remote operation");
		
			
			VNF.PolitoMailClient politoMailClient = new VNF.PolitoMailClient();
			VNF vnf1 = new VNF();
			VNFName vnfName1 = new VNFName();
			VNFIp vnfIp1 = new VNFIp();
			VNFIp vnfIp1a = new VNFIp();
			
			vnfName1.setId("politoMailClient");
			vnfIp1.setId("ip_client");
			vnfIp1a.setId("ip_client2");
			
			vnf1.setName(vnfName1);
			vnf1.getIPs().add(vnfIp1);
			vnf1.getIPs().add(vnfIp1a);
			vnf1.setAclFirewallOrDumbNodeOrEndHost(politoMailClient);			
			

			VNF.PolitoAntispam antispam = new VNF.PolitoAntispam();
			VNF vnf2 = new VNF();
			VNFName vnfName2 = new VNFName();
			VNFIp vnfIp2 = new VNFIp();
			vnfName2.setId("politoAntispam");
			vnfIp2.setId("ip_antispam");
			vnf2.setName(vnfName2);
			vnf2.getIPs().add(vnfIp2);
			antispam.getBlacklist().add(1);
			vnf2.setAclFirewallOrDumbNodeOrEndHost(antispam);
			
			
			VNF.PolitoMailServer politoMailServer = new VNF.PolitoMailServer();
			VNF vnf3 = new VNF();
			VNFName vnfName3 = new VNFName();
			VNFIp vnfIp3 = new VNFIp();
			vnfName3.setId("politoMailServer");
			vnfIp3.setId("ip_mailServer");
			vnf3.setName(vnfName3);
			vnf3.getIPs().add(vnfIp3);
			vnf3.setAclFirewallOrDumbNodeOrEndHost(politoMailServer);

			VNF.PolitoErrFunction politoErrFunction = new VNF.PolitoErrFunction();
			VNF vnf4 = new VNF();
			VNFName vnfName4 = new VNFName();
			VNFIp vnfIp4 = new VNFIp();
			vnfName4.setId("politoErrFunction");
			vnfIp4.setId("ip_errFunction");
			vnf4.setName(vnfName4);
			vnf4.getIPs().add(vnfIp4);
			vnf4.setAclFirewallOrDumbNodeOrEndHost(politoErrFunction);

			VNF.RoutingTable rt1 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry1 = new VNF.RoutingTable.Entry();
			entry1.setIP(vnfIp3); entry1.setName(vnfName2);
			VNF.RoutingTable.Entry entry2 = new VNF.RoutingTable.Entry();
			entry2.setIP(vnfIp4); entry2.setName(vnfName4);
			rt1.getEntry().add(entry1);
			rt1.getEntry().add(entry2);
			
			
			VNF.RoutingTable rt2 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry3 = new VNF.RoutingTable.Entry();
			entry3.setIP(vnfIp3); entry3.setName(vnfName3);
			VNF.RoutingTable.Entry entry4 = new VNF.RoutingTable.Entry();
			entry4.setIP(vnfIp1); entry4.setName(vnfName1);
			VNF.RoutingTable.Entry entry5 = new VNF.RoutingTable.Entry();
			entry5.setIP(vnfIp4); entry5.setName(vnfName4);
			rt2.getEntry().add(entry3);
			rt2.getEntry().add(entry4);
			rt2.getEntry().add(entry5);
			
			VNF.RoutingTable rt3 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry6 = new VNF.RoutingTable.Entry();
			entry6.setIP(vnfIp1); entry6.setName(vnfName2);
			rt3.getEntry().add(entry6);
			
			
			VNF.RoutingTable rt4 = new VNF.RoutingTable();
//			VNF.RoutingTable.Entry entry7 = new VNF.RoutingTable.Entry();
//			entry6.setIP(vnfIp4); entry6.setName(vnfName4);
//			rt4.getEntry().add(entry7);
		
			vnf1.setRoutingTable(rt1);
			vnf2.setRoutingTable(rt2);
			vnf3.setRoutingTable(rt3);
			vnf4.setRoutingTable(rt4);
			
			List<VNF> vnf = new ArrayList<VNF>();
			vnf.add(vnf1);
			vnf.add(vnf2);
			vnf.add(vnf3);
			vnf.add(vnf4);

			
			boolean ret = proxy.checkIsolationProperty(vnfName3,vnfName1,vnf);
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
				 System.out.println ("WebService operation error:  "+e3);
		  }catch (Exception e){
			 System.out.println ("Fatal error: "+ e);
		  }
	   }
		
	    public static void main(String[] args) {
			WS_Polito_AntispamTest vnfClient = new WS_Polito_AntispamTest();
	    }
}
