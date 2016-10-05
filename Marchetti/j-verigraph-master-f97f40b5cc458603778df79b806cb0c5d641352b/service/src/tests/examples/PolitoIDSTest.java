package tests.examples;

import java.util.ArrayList;

import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;

import mcnet.components.Checker;
import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;
import mcnet.components.Tuple;
import mcnet.netobjs.PolitoCache;
import mcnet.netobjs.PolitoIDS;
import mcnet.netobjs.PolitoWebClient;
import mcnet.netobjs.PolitoWebServer;

// | WebClient | ---------- | Cache | ---------- | PolitoIDS | ---------- | WebServer |


public class PolitoIDSTest {

	public Checker check;
	public PolitoIDS politoIDS;
	public PolitoWebClient hostA;
	public PolitoWebServer server;
	public PolitoCache politoCache;
	
	public PolitoIDSTest(Context ctx){
				
		NetContext nctx = new NetContext (ctx,new String[]{"hostA", "politoIDS","server", "politoCache"},
						new String[]{"ip_hostA", "ip_politoIDS","ip_server", "ip_politoCache"});
		
		Network net = new Network (ctx,new Object[]{nctx});
		
		hostA = new PolitoWebClient(ctx, new Object[]{nctx.nm.get("hostA"), net, nctx,nctx.am.get("ip_server")});
		server = new PolitoWebServer(ctx, new Object[]{nctx.nm.get("server"), net, nctx});
		politoIDS = new PolitoIDS(ctx, new Object[]{nctx.nm.get("politoIDS"), net, nctx});
		politoCache = new PolitoCache(ctx,new Object[]{nctx.nm.get("politoCache"),net , nctx});
		
		ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>> adm = new ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>>();
		ArrayList<DatatypeExpr> al1 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al2 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al3 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al4 = new ArrayList<DatatypeExpr>();
		
		al1.add(nctx.am.get("ip_hostA"));
		al2.add(nctx.am.get("ip_server"));
		al3.add(nctx.am.get("ip_politoIDS"));
		al4.add(nctx.am.get("ip_politoCache"));
		
		adm.add(new Tuple<>(hostA,al1));
		adm.add(new Tuple<>(server,al2));
		adm.add(new Tuple<>(politoIDS,al3));
		adm.add(new Tuple<>(politoCache,al4));
		
		net.setAddressMappings(adm);
		
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtA = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtA.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"), politoCache));
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtIDS = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtIDS.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_hostA"), politoCache));
	    rtIDS.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"), server));
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtServer = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtServer.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_hostA"), politoIDS));
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtCache = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtCache.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_hostA"), hostA));
	    rtCache.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"), politoIDS));
	    
	    net.routingTable(hostA, rtA);
	    net.routingTable(server, rtServer);
	    net.routingTable(politoIDS, rtIDS);
	    net.routingTable(politoCache, rtCache);

	    net.attach(hostA, politoCache, politoIDS, server);
	    
	    
	    politoIDS.installIDS(new int[]{PolitoIDS.DROGA});
	    politoCache.installCache(new NetworkObject[]{hostA});
	    
	    check = new Checker(ctx, nctx, net);
	}
}
