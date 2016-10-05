package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class EndHost extends NetworkObject{
public EndHost(Context ctx, Object[]... args) {
super(ctx, args);
}
@Override
protected void init(Context ctx, Object[]... args) {
super.init(ctx, args);
isEndHost=true;
}
public void EndHost_install(){
IntExpr t_0 = ctx.mkIntConst(node+"_EndHost_t_0");
IntExpr t_1 = ctx.mkIntConst(node+"_EndHost_t_1");
IntExpr t_2 = ctx.mkIntConst(node+"_EndHost_t_2");
Expr p = ctx.mkConst(node+"_EndHost_p",nctx.packet);
Expr n_0 = ctx.mkConst(node+"_EndHost_n_0",nctx.node);
constraints.add(ctx.mkForall(new Expr[]{t_2,p,n_0},ctx.mkImplies(
(BoolExpr)nctx.recv.apply(n_0, node, p, t_2),
ctx.mkExists(new Expr[]{t_1},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("dest").apply(p))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{t_2,p,n_0},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, p, t_2),
ctx.mkExists(new Expr[]{t_1},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("src").apply(p)),
ctx.mkEq(nctx.pf.get("origin").apply(p),node),
ctx.mkEq(nctx.pf.get("body").apply(p),nctx.pf.get("orig_body").apply(p))),1,null,null,null,null)),1,null,null,null,null));
}
}
