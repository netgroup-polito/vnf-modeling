package tests.examples;


import java.util.ArrayList;

import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;

import mcnet.components.Checker;
import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;
import mcnet.components.Tuple;
import mcnet.netobjs.AclFirewall;
import mcnet.netobjs.PolitoAntispam;
import mcnet.netobjs.PolitoErrFunction;
import mcnet.netobjs.PolitoMailClient;
import mcnet.netobjs.PolitoMailServer;

/**
 * @author Giacomo Costantini
 * <p/>
 * Antispam test													<p/>
 *| CLIENT | --------- | ANTISPAM | --------- | MAIL SERVER |		<p/>
 *..........................|										<p/>
 *...................| ERR FUNCTION |								<p/>
 */
public class PolitoAntispamTest {
	
	public Checker check;
	public AclFirewall fw;
	public PolitoAntispam politoAntispam;
	public PolitoMailClient politoMailClient;
	public PolitoMailServer politoMailServer;
	public PolitoErrFunction politoErrFunction;
	
	public	PolitoAntispamTest(Context ctx){
	
			NetContext nctx = new NetContext (ctx,
					new String[]{"politoMailClient", "politoAntispam", "politoMailServer", "politoErrFunction"},
					new String[]{"ip_client", "ip_antispam", "ip_mailServer", "ip_errFunction"});  //crea lista di nodi e indirizzi
			
			Network net = new Network (ctx,new Object[]{nctx});
			
			//crea nodi e li aggiunge alla rete
			politoMailClient = new PolitoMailClient(ctx, new Object[]{nctx.nm.get("politoMailClient"), net, nctx}); 
			politoAntispam = new PolitoAntispam(ctx, new Object[]{nctx.nm.get("politoAntispam"), net, nctx});
			politoMailServer = new PolitoMailServer(ctx, new Object[]{nctx.nm.get("politoMailServer"), net, nctx});
			politoErrFunction = new PolitoErrFunction(ctx, new Object[]{nctx.nm.get("politoErrFunction"), net, nctx});
			
			//crea le coppie nodi indirizzi
			ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>> adm = new ArrayList<Tuple<NetworkObject,ArrayList<DatatypeExpr>>>();
			ArrayList<DatatypeExpr> al1 = new ArrayList<DatatypeExpr>();
  			ArrayList<DatatypeExpr> al2 = new ArrayList<DatatypeExpr>();
  			ArrayList<DatatypeExpr> al3 = new ArrayList<DatatypeExpr>();
  			ArrayList<DatatypeExpr> al4 = new ArrayList<DatatypeExpr>();
  			al1.add(nctx.am.get("ip_client"));
  			al2.add(nctx.am.get("ip_antispam"));
  			al3.add(nctx.am.get("ip_mailServer"));
  			al4.add(nctx.am.get("ip_errFunction"));
			adm.add(new Tuple<>(politoMailClient, al1));
		    adm.add(new Tuple<>(politoAntispam, al2));
		    adm.add(new Tuple<>(politoMailServer, al3));
		    adm.add(new Tuple<>(politoErrFunction, al4));

		    net.setAddressMappings(adm); //associa nodi e indirizzi
		
		    //crea le tabelle di routing
			ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtClient = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    	rtClient.add(new Tuple<>(nctx.am.get("ip_mailServer"), politoAntispam));
	    	rtClient.add(new Tuple<>(nctx.am.get("ip_errFunction"), politoErrFunction));
	    
	    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtAnti = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    	rtAnti.add(new Tuple<>(nctx.am.get("ip_mailServer"), politoMailServer));
	    	rtAnti.add(new Tuple<>(nctx.am.get("ip_client"), politoMailClient));
	    	rtAnti.add(new Tuple<>(nctx.am.get("ip_errFunction"), politoErrFunction));
	    
	    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtServ = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
	    	rtServ.add(new Tuple<>(nctx.am.get("ip_client"), politoAntispam));
	    
//	    	ArrayList<Tuple<DatatypeExpr,NetworkObject>> rtErr = new ArrayList<Tuple<DatatypeExpr,NetworkObject>>();
//	    	rtErr.add(new Tuple(nctx.am.get("ip_errFunction"), politoErrFunction));	    	
//	        net.routingTable(politoAntispam, rtErr);

	    	net.routingTable(politoMailClient, rtClient);
		    net.routingTable(politoAntispam, rtAnti);
		    net.routingTable(politoMailServer, rtServ);
		    
		    net.attach(politoMailClient, politoAntispam, politoMailServer, politoErrFunction); //aggiunge i nodi alla rete
		    
		    /*System.out.println(net.EndHosts());*/ //stampa la lista degli endhost
		    politoAntispam.installAntispam(new int[]{1}); //mette il nodo 1 nella blacklist
		    check = new Checker(ctx,nctx,net);
	}
}
