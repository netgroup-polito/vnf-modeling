package it.polito.vnfchainverification;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;
/**
 * 
 * @author Giacomo Costantini
 * <p/>
 * Custom test										<p/>
 *    | A | ----| FW1 |----- | FW2 |----- | C |		<p/>
 *    .............|								<p/>
 *    | B | -------									<p/>
 *
 */
public class WS_Polito_DoubleFwTest {

	public WS_Polito_DoubleFwTest() {
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

			VNF.EndHost c = new VNF.EndHost();
			VNF vnf3 = new VNF();
			VNFName vnfName3 = new VNFName();
			VNFIp vnfIp3 = new VNFIp();
			vnfName3.setId("c");
			vnfIp3.setId("ip_c");
			vnf3.setName(vnfName3);
			vnf3.getIPs().add(vnfIp3);
			vnf3.setAclFirewallOrDumbNodeOrEndHost(c);			

			VNF.PolitoNF fw1 = new VNF.PolitoNF();
			VNF vnf4 = new VNF();
			VNFName vnfName4 = new VNFName();
			VNFIp vnfIp4 = new VNFIp();
			vnfName4.setId("fw1");
			vnfIp4.setId("ip_fw1");
			vnf4.setName(vnfName4);
			vnf4.getIPs().add(vnfIp4);
			VNF.PolitoNF.NFRule nr1 = new VNF.PolitoNF.NFRule();
			nr1.setIP1(vnfIp1); nr1.setIP2(vnfIp3);
			fw1.getNFRule().add(nr1);
			vnf4.setAclFirewallOrDumbNodeOrEndHost(fw1);

			VNF.PolitoNF fw2 = new VNF.PolitoNF();
			VNF vnf5 = new VNF();
			VNFName vnfName5 = new VNFName();
			VNFIp vnfIp5 = new VNFIp();
			vnfName5.setId("fw2");
			vnfIp5.setId("ip_fw2");
			vnf5.setName(vnfName5);
			vnf5.getIPs().add(vnfIp5);
			VNF.PolitoNF.NFRule nr2 = new VNF.PolitoNF.NFRule();
			nr2.setIP1(vnfIp2); nr2.setIP2(vnfIp3);
			fw2.getNFRule().add(nr2);
			vnf5.setAclFirewallOrDumbNodeOrEndHost(fw2);
			
		
			VNF.RoutingTable rt1 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry1 = new VNF.RoutingTable.Entry();
			entry1.setIP(vnfIp1); entry1.setName(vnfName4);
			VNF.RoutingTable.Entry entry2 = new VNF.RoutingTable.Entry();
			entry2.setIP(vnfIp2); entry2.setName(vnfName4);
			VNF.RoutingTable.Entry entry3 = new VNF.RoutingTable.Entry();
			entry3.setIP(vnfIp3); entry3.setName(vnfName4);
			rt1.getEntry().add(entry1);
			rt1.getEntry().add(entry2);
			rt1.getEntry().add(entry3);
			
			
			VNF.RoutingTable rt2 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry4 = new VNF.RoutingTable.Entry();
			entry4.setIP(vnfIp1); entry4.setName(vnfName5);
			VNF.RoutingTable.Entry entry5 = new VNF.RoutingTable.Entry();
			entry5.setIP(vnfIp2); entry5.setName(vnfName5);
			VNF.RoutingTable.Entry entry6 = new VNF.RoutingTable.Entry();
			entry6.setIP(vnfIp3); entry6.setName(vnfName5);
			rt2.getEntry().add(entry4);
			rt2.getEntry().add(entry5);
			rt2.getEntry().add(entry6);
			
			VNF.RoutingTable rt3 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry7 = new VNF.RoutingTable.Entry();
			entry7.setIP(vnfIp1); entry7.setName(vnfName1);
			VNF.RoutingTable.Entry entry8 = new VNF.RoutingTable.Entry();
			entry8.setIP(vnfIp2); entry8.setName(vnfName2);
			VNF.RoutingTable.Entry entry9 = new VNF.RoutingTable.Entry();
			entry9.setIP(vnfIp3); entry9.setName(vnfName5);
			rt3.getEntry().add(entry7);
			rt3.getEntry().add(entry8);
			rt3.getEntry().add(entry9);
			
			
			VNF.RoutingTable rt4 = new VNF.RoutingTable();
			VNF.RoutingTable.Entry entry10 = new VNF.RoutingTable.Entry();
			entry10.setIP(vnfIp1); entry10.setName(vnfName4);
			VNF.RoutingTable.Entry entry11 = new VNF.RoutingTable.Entry();
			entry11.setIP(vnfIp2); entry11.setName(vnfName4);
			VNF.RoutingTable.Entry entry12 = new VNF.RoutingTable.Entry();
			entry12.setIP(vnfIp3); entry12.setName(vnfName3);
			rt4.getEntry().add(entry10);
			rt4.getEntry().add(entry11);
			rt4.getEntry().add(entry12);
			
			
			
			vnf1.setRoutingTable(rt1);
			vnf2.setRoutingTable(rt1);
			vnf3.setRoutingTable(rt2);
			vnf4.setRoutingTable(rt3);
			vnf5.setRoutingTable(rt4);
			
			
			List<VNF> vnf = new ArrayList<VNF>();
			vnf.add(vnf1);
			vnf.add(vnf2);
			vnf.add(vnf3);
			vnf.add(vnf4);
			vnf.add(vnf5);
			
			boolean ret = proxy.checkIsolationProperty(vnfName1,vnfName3,vnf);
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
			WS_Polito_DoubleFwTest vnfClient = new WS_Polito_DoubleFwTest();
	    }
}
