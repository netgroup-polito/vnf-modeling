package tests.examples.newTest;


import java.util.ArrayList;

import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;

import mcnet.components.Checker;
import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;
import mcnet.components.Tuple;
import mcnet.netobjs.AclFirewall;
import mcnet.netobjs.EndHost;
import mcnet.netobjs.PolitoCache;
import mcnet.netobjs.PolitoWebServer;
import mcnet.netobjs.NF.Firewall;
import mcnet.netobjs.NF.WebCache;
import mcnet.netobjs.NF.WebServer;
/**
 * @author Giacomo Costantini
 * <p/>
 * Custom test	<p/>
 *	| A | <------> | CACHE | <------> | FW | <------> | PolitoWebServer |
 */
public class PolitoCacheFwTest {
	
	public Checker check;
	public EndHost a;
	public WebCache politoCache;
	public WebServer server;
	public Firewall fw;
	
	public	PolitoCacheFwTest(Context ctx){
	
		NetContext nctx = new NetContext (ctx,new String[]{"a", "server", "politoCache","fw"},
												new String[]{"ip_a", "ip_server", "ip_cache","ip_fw"});
		Network net = new Network (ctx,new Object[]{nctx});
		
		a = new EndHost(ctx, new Object[]{nctx.nm.get("a"), net, nctx});
		server = new WebServer(ctx, new Object[]{nctx.nm.get("server"), net, nctx});
		politoCache = new WebCache(ctx, new Object[]{nctx.nm.get("politoCache"), net, nctx});
		fw = new Firewall(ctx, new Object[]{nctx.nm.get("fw"), net, nctx});
		  
	    
	    ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>> adm = new ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>>();
		ArrayList<DatatypeExpr> al1 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al2 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al3 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al4 = new ArrayList<DatatypeExpr>();
		al1.add(nctx.am.get("ip_a"));
		al2.add(nctx.am.get("ip_server"));
		al3.add(nctx.am.get("ip_cache"));
		al4.add(nctx.am.get("ip_fw"));
		adm.add(new Tuple<>(a, al1));
	    adm.add(new Tuple<>(server, al2));
	    adm.add(new Tuple<>(politoCache, al3));
	    adm.add(new Tuple<>(fw, al4));
	    
	    
	    net.setAddressMappings(adm);
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rt1 = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rt1.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"), politoCache));

	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rt2 = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rt2.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_a"), fw));
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rt3 = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rt3.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_a"),a));
	    rt3.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"),fw));

	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rt4 = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rt4.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_a"),politoCache));
	    rt4.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"),server));

	    	
	    net.routingTable(a, rt1);
	    net.routingTable(server, rt2);
	    net.routingTable(politoCache, rt3);
	    net.routingTable(fw, rt4);
	    net.attach(a, server, politoCache, fw);

	    //Configuring middleboxes
//	    politoCache.installCache(new NetworkObject[]{nctx.nm.get("a")}); //a nodo interno
	    politoCache.WebCache_install();
	    ArrayList<DatatypeExpr> list = new ArrayList<DatatypeExpr>();
		list .add(nctx.am.get("ip_a"));
	    //politoCache.setInternalAddress(list);
	    
	    ArrayList<NetworkObject> list2 = new ArrayList<NetworkObject>();
		list2.add(a);
	    politoCache.setInternalAddress(list2);
	
	    ArrayList<Tuple<DatatypeExpr,DatatypeExpr>> acl = new ArrayList<Tuple<DatatypeExpr,DatatypeExpr>>();
	    acl.add(new Tuple<DatatypeExpr,DatatypeExpr>(nctx.am.get("ip_server"),nctx.am.get("ip_a"))); //comunicazione vietata tra a e server
	    
//	    fw.addAcls(acl); //aggiunge la regola al fw
	    fw.Firewall_install();
	    fw.addAcl_list(acl);
	    server.WebServer_install();
	
	    check = new Checker(ctx,nctx,net);
	}
}
