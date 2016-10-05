package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class MailServer extends NetworkObject{
public MailServer(Context ctx, Object[]... args) {
super(ctx, args);
}
@Override
protected void init(Context ctx, Object[]... args) {
super.init(ctx, args);
isEndHost=false;
}
public void MailServer_install(){
IntExpr t_0 = ctx.mkIntConst(node+"_MailServer_t_0");
IntExpr t_1 = ctx.mkIntConst(node+"_MailServer_t_1");
IntExpr t_2 = ctx.mkIntConst(node+"_MailServer_t_2");
Expr p1 = ctx.mkConst(node+"_MailServer_p1",nctx.packet);
Expr packet = ctx.mkConst(node+"_MailServer_packet",nctx.packet);
Expr n_source = ctx.mkConst(node+"_MailServer_n_source",nctx.node);
Expr n_0 = ctx.mkConst(node+"_MailServer_n_0",nctx.node);
constraints.add(ctx.mkForall(new Expr[]{t_2,p1,n_0},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, p1, t_2),
ctx.mkExists(new Expr[]{t_1,packet,n_source},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_REQUEST)),
ctx.mkEq(nctx.pf.get("proto").apply(p1), ctx.mkInt(nctx.POP3_RESPONSE)),
ctx.mkEq(nctx.pf.get("dest").apply(p1),nctx.pf.get("src").apply(packet)),
(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("src").apply(p1)),
ctx.mkEq(nctx.pf.get("origin").apply(p1),node),
ctx.mkEq(nctx.pf.get("body").apply(p1),nctx.pf.get("orig_body").apply(p1))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{n_0, p1, t_2},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, p1, t_2),ctx.mkOr(
ctx.mkEq(nctx.pf.get("proto").apply(p1), ctx.mkInt(nctx.POP3_RESPONSE))
)),1,null,null,null,null));
}
}
