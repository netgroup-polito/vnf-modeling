package myTests.examples;

import java.util.ArrayList;

import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;

import mcnet.components.Checker;
import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;
import mcnet.components.Tuple;
import mcnet.netobjs.PolitoAntispam;
import mcnet.netobjs.PolitoMailClient;
import mcnet.netobjs.PolitoMailServer;
import mcnet.netobjs.NF.*;

/**
 * <p/>
 * Antispam test													<p/>
 *| CLIENT | --------- | ANTISPAM | --------- | MAIL SERVER |		<p/>
																	<p/>
 */
public class PolitoAntispamTest {
	
	public Checker check;
	public Firewall fw;
	public Antispam politoAntispam;
	public MailClient politoMailClient;
	public MailServerForTest politoMailServer;

	
	public	PolitoAntispamTest(Context ctx){
	
			NetContext nctx = new NetContext (ctx,
					new String[]{"politoMailClient", "politoAntispam", "politoMailServer"},
					new String[]{"ip_client", "ip_antispam", "ip_mailServer"});  //create the lists of nodes and addresses
			
			Network net = new Network (ctx,new Object[]{nctx}); //create the network
			
			//create the nodes and add them to the network
			politoMailClient = new MailClient(ctx, new Object[]{nctx.nm.get("politoMailClient"), net, nctx}); 
			politoAntispam = new Antispam(ctx, new Object[]{nctx.nm.get("politoAntispam"), net, nctx});
			politoMailServer = new MailServerForTest(ctx, new Object[]{nctx.nm.get("politoMailServer"), net, nctx});
			
			//create the couples node-address
			ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>> adm = new ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>>();
			ArrayList<DatatypeExpr> al1 = new ArrayList<DatatypeExpr>();
  			ArrayList<DatatypeExpr> al2 = new ArrayList<DatatypeExpr>();
  			ArrayList<DatatypeExpr> al3 = new ArrayList<DatatypeExpr>();
  			al1.add(nctx.am.get("ip_client"));
  			al2.add(nctx.am.get("ip_antispam"));
  			al3.add(nctx.am.get("ip_mailServer"));
			adm.add(new Tuple<>(politoMailClient, al1));
		    adm.add(new Tuple<>(politoAntispam, al2));
		    adm.add(new Tuple<>(politoMailServer, al3));

		    net.setAddressMappings(adm); //link nodes and addresses
		
		    //create routing tables
		    /**/
			ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtClient = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    	rtClient.add(new Tuple<>(nctx.am.get("ip_mailServer"), politoAntispam));
	    
	    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtAnti = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    	rtAnti.add(new Tuple<>(nctx.am.get("ip_mailServer"), politoMailServer));
	    	rtAnti.add(new Tuple<>(nctx.am.get("ip_client"), politoMailClient));
	    
	    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtServ = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    	rtServ.add(new Tuple<>(nctx.am.get("ip_client"), politoAntispam));
	    	/**/

	    	//set the routing tables
	    	net.routingTable(politoMailClient, rtClient);
		    net.routingTable(politoAntispam, rtAnti);
		    net.routingTable(politoMailServer, rtServ);
		    
		    net.attach(politoMailClient, politoAntispam, politoMailServer); //add the nodes to the network
		    
		    /*System.out.println(net.EndHosts());*/ //stampa la lista degli endhost
		    
		    ArrayList<Integer> list = new ArrayList<Integer>();
		    list.add(1); //insert 1 in the blacklist (packet with emailFrom==1 are blocked)
		    politoAntispam.addBlacklist_list(list);
		    politoAntispam.Antispam_install();
		    politoMailClient.MailClient_install(nctx.am.get("ip_mailServer"));
		    politoMailServer.MailServer_install();
		    
		    politoMailServer.addConstraintForTest(); //add the constraint of wrapper (to test antispam)
		    check = new Checker(ctx,nctx,net);
	}
}
