package mcnet.netobjs;

import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;
import com.microsoft.z3.Solver;

import mcnet.components.NetworkObject;

/**
 * This is just a wrapper around z3 instances. The idea is that by using this we perhaps need to have 
 * fewer (or no) ifs to deal with the case where we don't instantiate an object for a node
 * @author Giacomo Costantini
 *
 */
public class DumbNode extends NetworkObject {
	public DumbNode(Context ctx, Object[]... args){
		super(ctx,args);
	}
	
	@Override
	protected void addConstraints(Solver solver) {
		return;	
	}

	@Override
	protected void init(Context ctx, Object[]... args) {
		isEndHost=true;
		this.z3Node = (DatatypeExpr)args[0][0];
	}
	@Override
	public DatatypeExpr getZ3Node() {
		return z3Node;
	}
}
