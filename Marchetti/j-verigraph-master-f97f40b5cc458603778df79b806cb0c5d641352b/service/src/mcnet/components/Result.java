package mcnet.components;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Model;

/**Data structure for the core of the response to a check request for data isolation property
 * 
 * @author Giacomo Costantini
 *
 */
public class Result {
	Context ctx;
	public Model model;
	public BoolExpr[] unsat_core;

/**
 * 	
 * @param ctx
 * @param model
 */
    public Result(Context ctx, Model model){
            this.ctx = ctx;
            this.model = model;
	}
    
/**
 * 
 * @param ctx
 * @param unsat_core
 */
    public Result(Context ctx, BoolExpr[] unsat_core){
        this.ctx = ctx;
        this.unsat_core = unsat_core;
}
}
