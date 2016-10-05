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
/**Error Function objects
 * 
 * @author Giacomo Costantini
 *
 */
public class PolitoErrFunction extends NetworkObject{
		
		FuncDecl isInBlacklist;
		
		public PolitoErrFunction(Context ctx, Object[]... args) {
			super(ctx, args);
		}

		@Override
		protected void init(Context ctx, Object[]... args) {
			super.init(ctx, args);     
			isEndHost=true;
	        errFunctionRules();
	        //net.saneSend(this);
	    }

	    private void errFunctionRules (){
	    	Expr n_0 = ctx.mkConst("PolitoErrFunction_"+node+"_n_0", nctx.node);
	    	Expr p_0 = ctx.mkConst("PolitoErrFunction_"+node+"_p_0", nctx.packet);
	    	IntExpr t_0 = ctx.mkIntConst("PolitoErrFunction_"+node+"_t_0");
	    			 
//	    	 constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
//	    			 ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
//	                 (BoolExpr)nctx.nodeHasAddr.apply(node, nctx.pf.get("src").apply(p_0))),1,null,null,null,null));
//	    	 constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
//	    			 ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
//	                 ctx.mkEq(nctx.pf.get("origin").apply(p_0), node)),1,null,null,null,null));
//	    	 
//	    	 constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
//	    			 ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
//	    		                ctx.mkEq(nctx.pf.get("orig_body").apply(p_0),nctx.pf.get("body").apply(p_0))),1,null,null,null,null));
	   	
	    	
//            Constraint1  	We want the ErrFunction not to send out any packet
//	    					send(node, n_0, p, t_0) -> 1 == 2 
            constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
                    ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
                        			ctx.mkEq(ctx.mkInt(1),ctx.mkInt(2))),1,null,null,null,null));
    	        
//            constraints.add( ctx.mkForall(new Expr[]{n_0, p_0, t_0}, 
//            		ctx.mkImplies(	(BoolExpr)nctx.send.apply(n_0, node, p_0, t_0), 
//                            		(BoolExpr)nctx.nodeHasAddr.apply(node, nctx.pf.get("dest").apply(p_0))),1,null,null,null,null));
	             
	    }
	}	
	
