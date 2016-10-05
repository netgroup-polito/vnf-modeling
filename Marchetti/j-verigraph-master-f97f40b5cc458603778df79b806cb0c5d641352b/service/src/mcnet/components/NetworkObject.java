package mcnet.components;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.DatatypeExpr;
import com.microsoft.z3.Solver;

/** Represents a generic network object.
 * 
 * @author Giacomo Costantini
 *
 */
public abstract class NetworkObject extends Core{
	
	protected List<BoolExpr> constraints; 
	protected Context ctx;
	protected DatatypeExpr node;
	protected Network net;
	protected NetContext nctx;
	
	public NetworkObject(Context ctx,Object[]... args) {
		super(ctx,args);
	}

	protected DatatypeExpr z3Node;
	protected boolean isEndHost;
	/**
	 * Get a reference to the z3 node this class wraps around
	 * @return
	 */
	
	//abstract public DatatypeExpr getZ3Node();
	
	public DatatypeExpr getZ3Node() {
		return node;
	}
	@Override
	protected void addConstraints(Solver solver) {
			BoolExpr[] constr = new BoolExpr[constraints.size()];
		    solver.add(constraints.toArray(constr));
	}
	
	public String toString(){
	        return z3Node.toString();
	}
	
	//There is probably an error: z3Node.hashCode = 0 because AST.hashCode() has always hash=0 
	/*public int hashCode(){
		return z3Node.hashCode();
	}*/
	        	
	/**
	 * A simple way to determine the set of endhosts
	 * @return
	 */
	public boolean isEndHost(){
	    return isEndHost;
	}
	
	/**
	 * Wrap methods to set policy
	 * @param policy
	 * @throws UnsupportedOperationException
	 */
	void setPolicy (Object policy) throws UnsupportedOperationException{
	     throw new UnsupportedOperationException();
	}
	
	@Override
	protected void init(Context ctx, Object[]... args) {
		
		this.ctx = ctx;
   		constraints = new ArrayList<BoolExpr>();
        z3Node = ((NetworkObject)args[0][0]).getZ3Node();
        node = z3Node;
        net = (Network)args[0][1];
        nctx = (NetContext)args[0][2];
        
    }
}
