package mcnet.netobjs.NF;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;

public class WebServerForTest extends WebServer{

	public WebServerForTest(Context ctx, Object[] objects) {
		super(ctx, objects);
	}

	public void addConstraintForTest() {
		IntExpr t_2 = ctx.mkIntConst(node+"_WebServer_t_2");
		Expr p1 = ctx.mkConst(node+"_WebServer_p1",nctx.packet);
		Expr n_0 = ctx.mkConst(node+"_WebServer_n_0",nctx.node);
		constraints.add(ctx.mkForall(new Expr[]{t_2,p1,n_0},ctx.mkImplies(
				(BoolExpr)nctx.send.apply(node, n_0, p1, t_2),
						ctx.mkEq(nctx.pf.get("body").apply(p1), ctx.mkInt(1))
						),1,null,null,null,null));
	}

}
