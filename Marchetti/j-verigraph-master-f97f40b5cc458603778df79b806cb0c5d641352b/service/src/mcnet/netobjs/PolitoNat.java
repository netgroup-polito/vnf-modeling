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
 * NAT Model object
 * @author Giacomo Costantini
 *
 */
public class PolitoNat extends NetworkObject{
		
	List<DatatypeExpr> private_addresses;
	List<NetworkObject> private_node;
	FuncDecl private_addr_func ;
	
	public PolitoNat(Context ctx, Object[]... args) {
		super(ctx, args);
	}

	@Override
	protected void init(Context ctx, Object[]... args) {
		super.init(ctx, args);
        isEndHost=false;
        private_addresses = new ArrayList<DatatypeExpr>();
        private_node = new ArrayList<NetworkObject>();
        net.saneSend(this);
        natModel();
    }

	private void addPrivateAdd(List<DatatypeExpr> address){
		private_addresses.addAll(address);
	}
	
	public List<DatatypeExpr> getPrivateAddress(){
		return private_addresses;
	}
	
    public void natModel(){
    	Expr x = ctx.mkConst("x", nctx.node);
    	Expr y = ctx.mkConst("y", nctx.node);
    	Expr z = ctx.mkConst("z", nctx.node);
    	
    	Expr p_0 = ctx.mkConst("p_0", nctx.packet);
    	Expr p_1 = ctx.mkConst("p_1", nctx.packet);
    	Expr p_2 = ctx.mkConst("p_2", nctx.packet);
    	
    	IntExpr t_0 = ctx.mkIntConst("t_0");
    	IntExpr t_1 = ctx.mkIntConst("t_1");
    	IntExpr t_2 = ctx.mkIntConst("t_2");
    	
    	private_addr_func = ctx.mkFuncDecl("private_addr_func", nctx.address, ctx.mkBoolSort());

    	//Constraint1
//    	"send(node, x, p_0, t_0) && !private_addr_func(p_0.dest) -> 
//    	p_0.src == ip_politoNat &&
//    	(exist y, p_1,t_1 : 
//    	   (recv(y, node, p_1, t_1) && t_1 < t_0 &&
//    	    private_addr_func(p1.src) && 
//    	    p_1.origin == p_0.origin && 
//    	    same for p_1.<dest,orig_body,body,seq,proto,emailFrom,url,options> == p_0.<...>) "
    	constraints.add( ctx.mkForall(new Expr[]{t_0, p_0, x}, 
    		ctx.mkImplies(
    				ctx.mkAnd((BoolExpr)nctx.send.apply(node, x, p_0, t_0), 
    						ctx.mkNot((BoolExpr)private_addr_func.apply(nctx.pf.get("dest").apply(p_0)))),
    				ctx.mkAnd(
    						ctx.mkEq(nctx.pf.get("src").apply(p_0),nctx.am.get("ip_politoNat")),
    						ctx.mkExists(new Expr[]{y, p_1, t_1}, 
    								ctx.mkAnd(
    									(BoolExpr)nctx.recv.apply(y, node, p_1, t_1), 
    									ctx.mkLt(t_1 , t_0),
    									(BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(p_1)),
    									ctx.mkEq(nctx.pf.get("origin").apply(p_1),nctx.pf.get("origin").apply(p_0)),
    									ctx.mkEq(nctx.pf.get("dest").apply(p_1),nctx.pf.get("dest").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("orig_body").apply(p_1),nctx.pf.get("orig_body").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("body").apply(p_1),nctx.pf.get("body").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("seq").apply(p_1),nctx.pf.get("seq").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("proto").apply(p_1),nctx.pf.get("proto").apply(p_0)),		        		                                    				
    									ctx.mkEq(nctx.pf.get("emailFrom").apply(p_1),nctx.pf.get("emailFrom").apply(p_0)),		        		                                    				
    									ctx.mkEq(nctx.pf.get("url").apply(p_1),nctx.pf.get("url").apply(p_0)),		        		                                    				
    									ctx.mkEq(nctx.pf.get("options").apply(p_1),nctx.pf.get("options").apply(p_0))),1,null,null,null,null))),1,null,null,null,null));
	              
    	//Constraint2
//    	send(node, x, p_0, t_0) && private_addr_func(p_0.dest) -> 
//    	!private_addr_func(p_0.src) &&
//    	(exist y, p_1,t_1 : 
//    	  (recv(y, node, p_1, t_1) && t_1 < t_0 &&
//    	   !private_addr_func(p1.src) && 
//    	   p_1.dest == ip_politoNat &&
//    	   p_1.origin == p_0.origin &&
//    	   same for p_1.<src,orig_body,body,seq,proto,emailFrom,url,options> == p_0.<...>) 
//    	recv(z,node,p2,t2)
//    	private_addr_func(p2.src)
//    	p1.src = p2.dest
//    	p0.src = p2.dest
//    	p2.src = p0.dest
    	constraints.add( ctx.mkForall(new Expr[]{x, p_0, t_0}, 
    			ctx.mkImplies(
    				ctx.mkAnd((BoolExpr)nctx.send.apply(node, x, p_0, t_0), 
    					(BoolExpr)private_addr_func.apply(nctx.pf.get("dest").apply(p_0))),
    					ctx.mkAnd(
    						ctx.mkNot((BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(p_0))),
    						ctx.mkExists(new Expr[]{y, p_1, t_1}, 
    							ctx.mkAnd(
    									ctx.mkLt(t_1 , t_0),
    									(BoolExpr)nctx.recv.apply(y, node, p_1, t_1), 
    									ctx.mkNot((BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(p_1))),
    									ctx.mkEq(nctx.pf.get("dest").apply(p_1),nctx.am.get("ip_politoNat")),
    									ctx.mkEq(nctx.pf.get("src").apply(p_1),nctx.pf.get("src").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("origin").apply(p_0),nctx.pf.get("origin").apply(p_1)),	
    									ctx.mkEq(nctx.pf.get("orig_body").apply(p_1),nctx.pf.get("orig_body").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("body").apply(p_1),nctx.pf.get("body").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("seq").apply(p_1),nctx.pf.get("seq").apply(p_0)),	
    									ctx.mkEq(nctx.pf.get("proto").apply(p_1),nctx.pf.get("proto").apply(p_0)),		        		                                    				
    									ctx.mkEq(nctx.pf.get("emailFrom").apply(p_1),nctx.pf.get("emailFrom").apply(p_0)),		        		                                    				
    									ctx.mkEq(nctx.pf.get("url").apply(p_1),nctx.pf.get("url").apply(p_0)),		        		                                    				
    									ctx.mkEq(nctx.pf.get("options").apply(p_1),nctx.pf.get("options").apply(p_0)),		        		                                    				
    									ctx.mkExists(new Expr[]{z, p_2, t_2}, 
    										ctx.mkAnd(
    												ctx.mkLt(t_2 , t_1),
    												(BoolExpr)nctx.recv.apply(z, node, p_2, t_2), 
    												(BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(p_2)),
    												ctx.mkEq(nctx.pf.get("src").apply(p_1),nctx.pf.get("dest").apply(p_2)),
    												ctx.mkEq(nctx.pf.get("src").apply(p_0),nctx.pf.get("dest").apply(p_2)),	
    												ctx.mkEq(nctx.pf.get("src").apply(p_2),nctx.pf.get("dest").apply(p_0))),1,null,null,null,null)),1,null,null,null,null))),1,null,null,null,null));
    }	
    
    public void setInternalAddress(ArrayList<DatatypeExpr> internalAddress){
    	List<BoolExpr> constr = new ArrayList<BoolExpr>();
    	Expr n_0 = ctx.mkConst("nat_node", nctx.address);
    	
    	for(DatatypeExpr n : internalAddress){
    		constr.add(ctx.mkEq(n_0,n));
    	}
    	BoolExpr[] constrs = new BoolExpr[constr.size()];
    	//Constraint		private_addr_func(n_0) == or(n_0==n foreach internal address)
    	constraints.add(ctx.mkForall(new Expr[]{n_0}, ctx.mkEq(private_addr_func.apply(n_0),ctx.mkOr(constr.toArray(constrs))),1,null,null,null,null));
    }
}	
	
