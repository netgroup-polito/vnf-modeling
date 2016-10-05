package tests.examples;

import java.util.ArrayList;

import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;

import mcnet.components.Checker;
import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;
import mcnet.components.Tuple;
import mcnet.netobjs.EndHost;
import mcnet.netobjs.PolitoNat;
import mcnet.netobjs.PolitoWebClient;
import mcnet.netobjs.PolitoWebServer;

public class PolitoMyTest {

	public Checker check;
	public PolitoWebClient hostA;
	public PolitoWebServer server;
	public PolitoNat politoNat;
	
	public	PolitoMyTest(Context ctx){
		
		NetContext nctx = new NetContext (ctx,new String[]{"hostA", "politoNat","server"},
												new String[]{"ip_hostA","ip_politoNat","ip_server"});
		Network net = new Network (ctx,new Object[]{nctx});
		hostA = new PolitoWebClient(ctx, new Object[]{nctx.nm.get("hostA"), net, nctx,nctx.am.get("ip_server")});
		server = new PolitoWebServer(ctx, new Object[]{nctx.nm.get("server"), net, nctx});
		politoNat = new PolitoNat(ctx, new Object[]{nctx.nm.get("politoNat"), net, nctx});
		
		ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>> adm = new ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>>();
		ArrayList<DatatypeExpr> al1 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al2 = new ArrayList<DatatypeExpr>();
		ArrayList<DatatypeExpr> al3 = new ArrayList<DatatypeExpr>();
		
		al1.add(nctx.am.get("ip_hostA"));
		al2.add(nctx.am.get("ip_server"));
		al3.add(nctx.am.get("ip_politoNat"));
		
		adm.add(new Tuple<>(hostA, al1));
	    adm.add(new Tuple<>(server, al2));
	    adm.add(new Tuple<>(politoNat, al3));
	    
	    net.setAddressMappings(adm);
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtA = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtA.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"), politoNat));
	    rtA.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_politoNat"), politoNat));
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtServer = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtServer.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_hostA"), politoNat));
	    rtServer.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_politoNat"), politoNat));
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtNat = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtNat.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_hostA"), hostA));
	    rtNat.add(new Tuple<DatatypeExpr,NetworkObject>(nctx.am.get("ip_server"), server));
	    
	    net.routingTable(politoNat, rtNat);
	    net.routingTable(server, rtServer);
	    net.routingTable(hostA, rtA);
	    
	    net.attach(hostA, server, politoNat);
	    
	    ArrayList<DatatypeExpr> ia = new ArrayList<DatatypeExpr>();
	    ia.add(nctx.am.get("ip_server"));
	    //ia.add(nctx.am.get("ip_hostA"));
	    
	    politoNat.setInternalAddress(ia);
	    
	    check = new Checker(ctx,nctx,net);
	}
}
