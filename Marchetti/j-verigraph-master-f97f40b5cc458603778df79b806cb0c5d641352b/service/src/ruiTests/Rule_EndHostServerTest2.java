
package ruiTests;

import java.util.ArrayList;
import java.util.HashMap;

import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;
import com.microsoft.z3.EnumSort;
import com.microsoft.z3.IntNum;

import mcnet.components.Checker;
import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;
import mcnet.components.Tuple;
import mcnet.netobjs.PolitoWebServer;
import ruiNFs.Rule_EndHost;
import ruiNFs.Rule_WebServer;


/**
 * EndHost - DNSserver - GlobalBalancer - cdnCache  - PolitoWebServer   test												<p/>
 * 
 *    | HOST_A | ---| DNSserver |--- | GlobalBalancer|---| cdnCache_1 | ----| PolitoWebServer |	<p/>
 *    										|---------------------------------------|
 *    	   									|------------| cdnCache_2 |-------------	|	<p/>
 */
public class Rule_EndHostServerTest2 {
	
	public Checker checker;
	public Rule_EndHost hostA;
	//public PolitoWebServer webServer;
	public Rule_WebServer webServer;
	public IntNum port;
	
	public	Rule_EndHostServerTest2(Context ctx){
	
		port = ctx.mkInt(99);
		
		
		NetContext nctx = new NetContext (ctx,new String[]{"hostA", "webServer"},
				new String[]{"ip_hostA", "ip_webServer"});
		Network net = new Network (ctx,new Object[]{nctx});

		hostA = new Rule_EndHost(ctx, new Object[]{nctx.nm.get("hostA"), net, nctx});
		
		webServer = new Rule_WebServer(ctx, new Object[]{nctx.nm.get("webServer"), net, nctx});
	
		ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>> adm = new ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>>();
		ArrayList<DatatypeExpr> al1 = new ArrayList<DatatypeExpr>();
			
			ArrayList<DatatypeExpr> al5 = new ArrayList<DatatypeExpr>();
			
			al1.add(nctx.am.get("ip_hostA"));
			
			al5.add(nctx.am.get("ip_webServer"));
		
		adm.add(new Tuple<>(hostA, al1));
	   
	    adm.add(new Tuple<>(webServer, al5));
	 

	    net.setAddressMappings(adm);
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtHostA = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtHostA.add(new Tuple<>(nctx.am.get("ip_webServer"), webServer));
    
    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtWebServer = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
    	rtWebServer.add(new Tuple<>(nctx.am.get("ip_hostA"), hostA));   //assume
    	
    	net.routingTable(hostA, rtHostA);
	    net.routingTable(webServer, rtWebServer);
	    
	    net.attach(hostA, webServer); 
	
	/*    ArrayList<DatatypeExpr> ia1 = new ArrayList<DatatypeExpr>();
	    ia1.add(nctx.am.get("ip_webServer"));
	    ia1.add(nctx.am.get("ip_hostA"));*/   // no use isInternal() function  in cache and server 
	
	    hostA.installEndHost(nctx.am.get("ip_hostA"),port, nctx.am.get("ip_webServer"));
	    webServer.addEntry(ctx.mkInt(nctx.REQUESTED_URL),null);
	    webServer.installWebServer(ctx.mkInt(nctx.REQUESTED_URL));
	    
	    checker = new Checker(ctx,nctx,net);
	}
	
}
