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
import com.microsoft.z3.Sort;

import mcnet.components.NetContext;
import mcnet.components.Network;
import mcnet.components.NetworkObject;

/** First example of custom network function: a simple filter
 * 
 * @author Giacomo Costantini
 *
 */
public class PolitoNF extends NetworkObject{
		
		FuncDecl isInBlacklist;
		
		public PolitoNF(Context ctx, Object[]... args) {
			super(ctx, args);
		}

		@Override
		protected void init(Context ctx, Object[]... args) {
			super.init(ctx, args);
	        isEndHost=false;
	        //net.saneSend(this);
	    }

	    public void politoNFRules (DatatypeExpr ipA,DatatypeExpr ipB){
//	    	System.out.println("[PolitoNf] Installing rules");
	    	Expr n_0 = ctx.mkConst("politoNF_"+node+"_n_0", nctx.node);
	    	Expr n_1 = ctx.mkConst("politoNF_"+node+"_n_1", nctx.node);
	    	Expr p_0 = ctx.mkConst("politoNF_"+node+"_p_0", nctx.packet);
	    	IntExpr t_0 = ctx.mkIntConst("politoNF_"+node+"_t_0");
	    	IntExpr t_1 = ctx.mkIntConst("politoNF_"+node+"_t_1");
	    	Expr a_0 = ctx.mkConst(node+"_politoNF_a_0", nctx.address);
	    	Expr a_1 = ctx.mkConst(node+"_politoNF_a_1", nctx.address);
	    	
	    	FuncDecl myFunction = ctx.mkFuncDecl(node+"_myFunction", new Sort[]{nctx.address,nctx.address}, ctx.mkBoolSort());
	    	
	    	BoolExpr myConstraint = ctx.mkForall(new Expr[]{n_0, p_0, t_0},
	    			 ctx.mkImplies((BoolExpr)nctx.send.apply(node, n_0, p_0, t_0), 
	    					 ctx.mkExists(new Expr[]{n_1, t_1}, 
	    							 ctx.mkAnd((BoolExpr)nctx.recv.apply(n_1, node, p_0, t_1),
	    									 	ctx.mkLt(t_1 , t_0),
	    									 	(BoolExpr)myFunction.apply(nctx.pf.get("src").apply(p_0), nctx.pf.get("dest").apply(p_0))),1,null,null,null,null)),1,null,null,null,null);
 
	    	BoolExpr funcConstraint = ctx.mkOr(ctx.mkAnd(ctx.mkEq(a_0, ipA), ctx.mkEq(a_1, ipB)), ctx.mkAnd(ctx.mkEq(a_0,ipB), ctx.mkEq(a_1,ipA)));

	    	// Constraint1		myFunction(a_0,a_1) == ((a_0 == ipA && a_1 == ipB) || (a_0 == ipB && a_1 == ipA))
			 constraints.add(
					 ctx.mkForall(new Expr[]{a_0,a_1}, 
							 ctx.mkEq(myFunction.apply(a_0, a_1), funcConstraint),1,null,null,null,null));
			
			//Constraint2		send(node, n_0, p, t_0)  -> 
			 //					(exist n_1,t_1 : (t_1 < t_0 && recv(n_1, node, p, t_1) && myFunction(p.src,p.dest))
			 constraints.add(myConstraint);
	             
	    }

	   
	
	}	
	
