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
 *  MailClient objects
 * @author Giacomo Costantini
 *
 */   
public class PolitoMailClient extends NetworkObject{
		
		FuncDecl isInBlacklist;
	
		public PolitoMailClient(Context ctx, Object[]... args) {
			super(ctx, args);
		}

		@Override
		protected void init(Context ctx, Object[]... args) {
			super.init(ctx, args);
	        isEndHost=true;
	        mailClientRules();
	        //net.saneSend(this);
	    }
		
	    private void mailClientRules (){
	    	Expr n_0 = ctx.mkConst("PolitoMailClient_"+node+"_n_0", nctx.node);
	    	Expr p_0 = ctx.mkConst("PolitoMailClient_"+node+"_p_0", nctx.packet);
	    	IntExpr t_0 = ctx.mkIntConst("PolitoMailClient_"+node+"_t_0");
	   
//            Constraint1		send(node, n_0, p, t_0) -> nodeHasAddr(node,p.src)
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("src").apply(p_0))),1,null,null,null,null));

//            Constraint2		send(node, n_0, p, t_0) -> p.origin == node
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		ctx.mkEq(nctx.pf.get("origin").apply(p_0),node)),1,null,null,null,null));

//            Constraint3		send(node, n_0, p, t_0) -> p.orig_body == p.body
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                    		ctx.mkEq(nctx.pf.get("orig_body").apply(p_0),nctx.pf.get("body").apply(p_0))),1,null,null,null,null));
			
//            Constraint4		recv(n_0, node, p, t_0) -> nodeHasAddr(node,p.dest)
			constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.recv.apply(n_0,node, p_0, t_0), 
                    		(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("dest").apply(p_0))),1,null,null,null,null));

//            Constraint5		This client is only able to produce POP3 requests	
//								send(node, n_0, p, t_0) -> p.proto == POP3_REQ
            constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                        ctx.mkEq(nctx.pf.get("proto").apply(p_0), ctx.mkInt(nctx.POP3_REQUEST))),1,null,null,null,null));

//            Constraint6		send(node, n_0, p, t_0) -> p.dest == ip_mailServer
            constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                            ctx.mkEq(nctx.pf.get("dest").apply(p_0), nctx.am.get("ip_mailServer"))),1,null,null,null,null));
		}
}	
	
