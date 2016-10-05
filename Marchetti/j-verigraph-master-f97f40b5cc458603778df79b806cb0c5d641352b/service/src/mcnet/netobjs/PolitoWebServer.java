package mcnet.netobjs;


import java.util.ArrayList;
import java.util.List;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;

import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;
/**
 * WebServer object
 * @author Giacomo Costantini
 *
 */
public class PolitoWebServer extends NetworkObject{
		
		FuncDecl isInBlacklist;
		
		public PolitoWebServer(Context ctx, Object[]... args) {
			super(ctx, args);
		}

		@Override
		protected void init(Context ctx, Object[]... args) {
			super.init(ctx, args);
	        isEndHost=true;
	        webServerRules();
	    }
		

	    private void webServerRules (){
	    	Expr n_0 = ctx.mkConst("webserver_"+node+"_n_0", nctx.node);
	    	Expr p_0 = ctx.mkConst("webserver_"+node+"_p_0", nctx.packet);
	    	Expr p_1 = ctx.mkConst("webserver_"+node+"_p_1", nctx.packet);
	    	IntExpr t_0 = ctx.mkIntConst("webserver_"+node+"_t_0");
	    	IntExpr t_1 = ctx.mkIntConst("webserver_"+node+"_t_1");
	    	
	    	//Constraint1		send(politoWebServer, n_0, p, t_0) -> nodeHasAddr(politoWebServer,p.src)    	
	    	constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("src").apply(p_0))),1,null,null,null,null));

	    	//Constraint2		send(politoWebServer, n_0, p, t_0) -> p.origin == politoWebServer
	    	constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		ctx.mkEq(nctx.pf.get("origin").apply(p_0),node)),1,null,null,null,null));
			
	    	//Constraint3		send(politoWebServer, n_0, p, t_0) -> p.orig_body == p.body && p.body == 1 //added
	    	constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		ctx.mkAnd(ctx.mkEq(nctx.pf.get("orig_body").apply(p_0),nctx.pf.get("body").apply(p_0)),
                    				  ctx.mkEq(nctx.pf.get("body").apply(p_0), ctx.mkInt(PolitoIDS.DROGA)))),
                    1,null,null,null,null));
	        
	    	//Constraint4		recv(n_0, politoWebServer, p, t_0) -> nodeHasAddr(politoWebServer,p.dest)
	    	constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.recv.apply(n_0,node, p_0, t_0), 
                    		(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("dest").apply(p_0))),1,null,null,null,null));
			
	    	//Constraint5		
			//	    			send(politoWebServer, n_0, p, t_0)  -> 
			//	    			(exist p_1,t_1 : 
			//	    	    		(t_1 < t_0 && recv(n_0, politoWebServer, p_1, t_1) &&  
			//	    	     		p_0.proto == HTTP_RESP &&  p_1.proto == HTTP_REQ && 
			//	    	     		p_0.dest == p_1.src && p_0.src == p_1.dest &&  p_0.url == p_1.url)
	    	constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                        ctx.mkExists(new Expr[]{p_1, t_1}, 
                            ctx.mkAnd(
                            		ctx.mkLt(t_1, t_0),
                            		ctx.mkEq(nctx.pf.get("url").apply(p_0), nctx.pf.get("url").apply(p_1)),
                            		(BoolExpr)nctx.recv.apply(n_0, node, p_1, t_1),
                            		ctx.mkEq(nctx.pf.get("proto").apply(p_0), ctx.mkInt(nctx.HTTP_RESPONSE)),
                            		ctx.mkEq(nctx.pf.get("proto").apply(p_1), ctx.mkInt(nctx.HTTP_REQUEST)),
                            		ctx.mkEq(nctx.pf.get("dest").apply(p_0), nctx.pf.get("src").apply(p_1)),
                            		ctx.mkEq(nctx.pf.get("src").apply(p_0), nctx.pf.get("dest").apply(p_1))),
                            1,null,null,null,null)),1,null,null,null,null));    	        	            	        
	    }
}	
	
