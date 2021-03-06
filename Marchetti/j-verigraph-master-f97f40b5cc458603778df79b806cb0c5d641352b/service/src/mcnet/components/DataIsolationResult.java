package mcnet.components;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Status;


/**Data structure for the response to a check request for data isolation property
 * 
 * @author Giacomo Costantini
 *
 */
public class DataIsolationResult {

	Context ctx;
	public NetContext nctx;
	public Status result;
	public Model model;
	public Expr violating_packet,last_hop,last_time,t_1;
	public BoolExpr [] assertions;

	public DataIsolationResult(Context ctx,Status result, Expr violating_packet, Expr last_hop, Expr last_time, NetContext nctx, BoolExpr[] assertions, Model model){
            this.ctx = ctx;
            this.result = result;
            this.violating_packet = violating_packet;
            this.last_hop = last_hop;
            this.model = model;
            this.last_time = last_time;
            this.assertions = assertions;
	}
}

