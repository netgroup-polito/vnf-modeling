package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class WebClient extends NetworkObject{
public WebClient(Context ctx, Object[]... args) {
super(ctx, args);
}
@Override
protected void init(Context ctx, Object[]... args) {
super.init(ctx, args);
isEndHost=true;
}
public void WebClient_install(Object ipServer){
IntExpr t_0 = ctx.mkIntConst(node+"_WebClient_t_0");
IntExpr t_1 = ctx.mkIntConst(node+"_WebClient_t_1");
IntExpr t_2 = ctx.mkIntConst(node+"_WebClient_t_2");
Expr packet = ctx.mkConst(node+"_WebClient_packet",nctx.packet);
Expr n_0 = ctx.mkConst(node+"_WebClient_n_0",nctx.node);
constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(
(BoolExpr)nctx.recv.apply(n_0, node, packet, t_2),
ctx.mkExists(new Expr[]{t_1},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("dest").apply(packet))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),
ctx.mkExists(new Expr[]{t_1},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("src").apply(packet)),
ctx.mkEq(nctx.pf.get("origin").apply(packet),node),
ctx.mkEq(nctx.pf.get("body").apply(packet),nctx.pf.get("orig_body").apply(packet)),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_REQUEST)),
ctx.mkEq(nctx.pf.get("dest").apply(packet), ((DatatypeExpr)ipServer))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{n_0, packet, t_2},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),ctx.mkOr(
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_REQUEST))
)),1,null,null,null,null));
}
}
