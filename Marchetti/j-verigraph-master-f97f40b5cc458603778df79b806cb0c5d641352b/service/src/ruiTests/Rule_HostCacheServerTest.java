
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
import ruiNFs.Rule_GlobalDnsBalancer;
import ruiNFs.Rule_LocalDnsServer;
import ruiNFs.Rule_WebServer;
import ruiNFs.Rule_CDNcache;

/**
 * EndHost - DNSserver - GlobalBalancer - cdnCache  - PolitoWebServer   test												<p/>
 * 
 *    | HOST_A | ------| cdnCache_1 | ----| PolitoWebServer |	<p/>
 *    	   |---------------------------------------|
 *    	   |------------| cdnCache_2 |-------------	|	<p/>
 */
public class Rule_HostCacheServerTest {
	
	public Checker checker;
	public Rule_EndHost hostA;
	public Rule_CDNcache cdnCache_1,cdnCache_2;
	public Rule_WebServer webServer;
	public IntNum port;
	
	public	Rule_HostCacheServerTest(Context ctx){
	
		port = ctx.mkInt(99);
		
		
		NetContext nctx = new NetContext (ctx,new String[]{"hostA", "cdnCache_1", "cdnCache_2", "webServer"},
				new String[]{"ip_hostA", "ip_cdnCache_1", "ip_cdnCache_2","ip_webServer"});
		Network net = new Network (ctx,new Object[]{nctx});

		hostA = new Rule_EndHost(ctx, new Object[]{nctx.nm.get("hostA"), net, nctx});
		cdnCache_1 = new Rule_CDNcache(ctx, new Object[]{nctx.nm.get("cdnCache_1"), net, nctx});
		cdnCache_2 = new Rule_CDNcache(ctx, new Object[]{nctx.nm.get("cdnCache_2"), net, nctx});
		webServer = new Rule_WebServer(ctx, new Object[]{nctx.nm.get("webServer"), net, nctx});
	
		ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>> adm = new ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>>();
			ArrayList<DatatypeExpr> al1 = new ArrayList<DatatypeExpr>();			
			ArrayList<DatatypeExpr> al3 = new ArrayList<DatatypeExpr>();
			ArrayList<DatatypeExpr> al4 = new ArrayList<DatatypeExpr>();
			ArrayList<DatatypeExpr> al5 = new ArrayList<DatatypeExpr>();
			
			al1.add(nctx.am.get("ip_hostA"));
			al3.add(nctx.am.get("ip_cdnCache_1"));
			al4.add(nctx.am.get("ip_cdnCache_2"));
			al5.add(nctx.am.get("ip_webServer"));
			
		adm.add(new Tuple<>(hostA, al1));
	    adm.add(new Tuple<>(cdnCache_1, al3));
	    adm.add(new Tuple<>(cdnCache_2, al4));
	    adm.add(new Tuple<>(webServer, al5));

	    net.setAddressMappings(adm);
	    
	    ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtHostA = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    rtHostA.add(new Tuple<>(nctx.am.get("ip_cdnCache_1"), cdnCache_1));
	    rtHostA.add(new Tuple<>(nctx.am.get("ip_cdnCache_2"), cdnCache_2));
	    rtHostA.add(new Tuple<>(nctx.am.get("ip_webServer"), cdnCache_1));
  
    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtCdnCache_1 = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
    	rtCdnCache_1.add(new Tuple<>(nctx.am.get("ip_hostA"), hostA));
    	rtCdnCache_1.add(new Tuple<>(nctx.am.get("ip_webServer"), webServer));

    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtCdnCache_2 = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
    	rtCdnCache_2.add(new Tuple<>(nctx.am.get("ip_hostA"), hostA));
    	rtCdnCache_2.add(new Tuple<>(nctx.am.get("ip_webServer"), webServer));

    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtWebServer = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
    	rtWebServer.add(new Tuple<>(nctx.am.get("ip_cdnCache_1"), cdnCache_1));
    	rtWebServer.add(new Tuple<>(nctx.am.get("ip_cdnCache_2"), cdnCache_2));
    	rtWebServer.add(new Tuple<>(nctx.am.get("ip_hostA"), cdnCache_1));   //assume

    	net.routingTable(hostA, rtHostA);
	    net.routingTable(cdnCache_1, rtCdnCache_1);
	    net.routingTable(cdnCache_2, rtCdnCache_2);
	    net.routingTable(webServer, rtWebServer);
	    
	    net.attach(hostA, cdnCache_1, cdnCache_2, webServer); //aggiunge i nodi alla rete
	
	    hostA.installEndHost(nctx.am.get("ip_hostA"),port, nctx.am.get("ip_webServer"));
 
	    ArrayList<DatatypeExpr> ia = new ArrayList<DatatypeExpr>();
	    ia.add(nctx.am.get("ip_hostA"));
	    ia.add(nctx.am.get("ip_cdnCache_1"));
	    ia.add(nctx.am.get("ip_cdnCache_2"));
	    
	    cdnCache_1.setInternalAddress(ia);
	    cdnCache_2.setInternalAddress(ia);
	    
	    cdnCache_1.installCDNcache();
	    cdnCache_2.installCDNcache();
	    
	    webServer.addEntry(ctx.mkInt(nctx.REQUESTED_URL),null);
	    webServer.installWebServer(ctx.mkInt(nctx.REQUESTED_URL));
	    
	    checker = new Checker(ctx,nctx,net);
	}
	
}
