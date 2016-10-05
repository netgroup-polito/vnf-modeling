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
 * WebClient 
 */
public class PolitoWebClient extends NetworkObject{
		
		FuncDecl isInBlacklist;
		
		public PolitoWebClient(Context ctx, Object[]... args) {
			super(ctx, args);
		}

		@Override
		protected void init(Context ctx, Object[]... args) {
			super.init(ctx, args);
			isEndHost=true;
			DatatypeExpr ipServer = (DatatypeExpr) args[0][3];
			webClientRules(ipServer);
			//net.saneSend(this);
	    }
		
	    private void webClientRules (DatatypeExpr ipServer){
	    	Expr n_0 = ctx.mkConst("PolitoWebClient_"+node+"_n_0", nctx.node);
	    	Expr p_0 = ctx.mkConst("PolitoWebClient_"+node+"_p_0", nctx.packet);
	    	IntExpr t_0 = ctx.mkIntConst("PolitoWebClient_"+node+"_t_0");
	    	
	    	//Constraint1		send(node, n_0, p, t_0) -> nodeHasAddr(node,p.src)
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("src").apply(p_0))),1,null,null,null,null));

			//Constraint2		send(node, n_0, p, t_0) -> p.origin == node
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		ctx.mkEq(nctx.pf.get("origin").apply(p_0),node)),1,null,null,null,null));

			//Constraint3		send(node, n_0, p, t_0) -> p.orig_body == p.body
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		ctx.mkEq(nctx.pf.get("orig_body").apply(p_0),nctx.pf.get("body").apply(p_0))),1,null,null,null,null));
			
			//Constraint4		recv(n_0, node, p, t_0) -> nodeHasAddr(node,p.dest)
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.recv.apply(n_0,node, p_0, t_0), 
                    		(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("dest").apply(p_0))),1,null,null,null,null));

			
			//Constraint5		This client is only able to produce HTTP requests
			//					send(node, n_0, p, t_0) -> p.proto == HTTP_REQ	
            constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                        ctx.mkEq(nctx.pf.get("proto").apply(p_0), ctx.mkInt(nctx.HTTP_REQUEST))),1,null,null,null,null));

            //Constraint6		send(node, n_0, p, t_0) -> p.dest == ipServer
            constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                            ctx.mkEq(nctx.pf.get("dest").apply(p_0), ipServer)),1,null,null,null,null));
		 	     
	    }
	}	
	
