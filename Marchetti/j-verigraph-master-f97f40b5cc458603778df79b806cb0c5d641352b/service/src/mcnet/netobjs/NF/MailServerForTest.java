package mcnet.netobjs.NF;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;

public class MailServerForTest extends MailServer{

	public MailServerForTest(Context ctx, Object[] objects) {
		super(ctx, objects);
	}

	public void addConstraintForTest() {
		IntExpr t_2 = ctx.mkIntConst(node+"_MailServer_t_2");
		Expr p1 = ctx.mkConst(node+"_MailServer_p1",nctx.packet);
		Expr n_0 = ctx.mkConst(node+"_MailServer_n_0",nctx.node);
		constraints.add(ctx.mkForall(new Expr[]{t_2,p1,n_0},ctx.mkImplies(
				(BoolExpr)nctx.send.apply(node, n_0, p1, t_2),
						ctx.mkEq(nctx.pf.get("emailFrom").apply(p1), ctx.mkInt(1))),1,null,null,null,null));
	}


}
