package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class WebCache extends NetworkObject{
List<DatatypeExpr> private_addresses;
FuncDecl private_addr_func;
public WebCache(Context ctx, Object[]... args) {
super(ctx, args);
}
@Override
protected void init(Context ctx, Object[]... args) {
super.init(ctx, args);
isEndHost=false;
private_addresses = new ArrayList<DatatypeExpr>();
}
private void addPrivateAdd(List<DatatypeExpr> address){
private_addresses.addAll(address);
}
public List<DatatypeExpr> getPrivateAddress(){
return private_addresses;
}
public void setInternalAddress(ArrayList<NetworkObject> internalAddress){
List<BoolExpr> constr = new ArrayList<BoolExpr>();
Expr n_0 = ctx.mkConst("WebCache_node", nctx.node);
for(NetworkObject n : internalAddress){
constr.add(ctx.mkEq(n_0,n.getZ3Node()));
}
BoolExpr[] constrs = new BoolExpr[constr.size()];
constraints.add(ctx.mkForall(new Expr[]{n_0}, ctx.mkEq(private_addr_func.apply(n_0),ctx.mkOr(constr.toArray(constrs))),1,null,null,null,null));
}
public void WebCache_install(){
private_addr_func = ctx.mkFuncDecl("private_addr_func", nctx.node, ctx.mkBoolSort());
FuncDecl ResorceCache_func = ctx.mkFuncDecl(node+"_ResorceCache_func",new Sort[]{ctx.mkIntSort(),ctx.mkIntSort()},ctx.mkBoolSort());
IntExpr t_0 = ctx.mkIntConst(node+"_WebCache_t_0");
IntExpr t_1 = ctx.mkIntConst(node+"_WebCache_t_1");
IntExpr t_2 = ctx.mkIntConst(node+"_WebCache_t_2");
Expr p1 = ctx.mkConst(node+"_WebCache_p1",nctx.packet);
Expr n_1 = ctx.mkConst(node+"_WebCache_n_1",nctx.node);
Expr p2 = ctx.mkConst(node+"_WebCache_p2",nctx.packet);
IntExpr f_0 = ctx.mkIntConst(node+"_WebCache_f_0");
Expr packet = ctx.mkConst(node+"_WebCache_packet",nctx.packet);
Expr n_source = ctx.mkConst(node+"_WebCache_n_source",nctx.node);
Expr n_0 = ctx.mkConst(node+"_WebCache_n_0",nctx.node);
constraints.add(ctx.mkForall(new Expr[]{t_2,p1,n_0},ctx.mkImplies(ctx.mkAnd(
(BoolExpr)nctx.send.apply(node, n_0, p1, t_2),
((BoolExpr)private_addr_func.apply(n_0))),
ctx.mkExists(new Expr[]{t_1,packet},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.recv.apply(n_0, node, packet, t_1),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_REQUEST)),
(BoolExpr)ResorceCache_func.apply(nctx.pf.get("url").apply(packet),t_1),
((BoolExpr)private_addr_func.apply(n_0)),
ctx.mkEq(nctx.pf.get("src").apply(p1),nctx.pf.get("dest").apply(packet)),
ctx.mkEq(nctx.pf.get("dest").apply(p1),nctx.pf.get("src").apply(packet)),
ctx.mkEq(nctx.pf.get("url").apply(p1),nctx.pf.get("url").apply(packet)),
ctx.mkEq(nctx.pf.get("proto").apply(p1), ctx.mkInt(nctx.HTTP_RESPONSE)),
ctx.mkEq(nctx.pf.get("proto").apply(p1), ctx.mkInt(nctx.HTTP_RESPONSE))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_source,n_0},ctx.mkImplies(ctx.mkAnd(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),
((BoolExpr)private_addr_func.apply(n_source)),
ctx.mkNot(((BoolExpr)private_addr_func.apply(n_0)))),
ctx.mkExists(new Expr[]{t_1},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_REQUEST)),
((BoolExpr)private_addr_func.apply(n_source)),
ctx.mkNot((BoolExpr)ResorceCache_func.apply(nctx.pf.get("url").apply(packet),t_1)),
ctx.mkNot(((BoolExpr)private_addr_func.apply(n_0))),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_REQUEST))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{t_2,f_0},ctx.mkImplies(
(BoolExpr)ResorceCache_func.apply(f_0, t_2),
ctx.mkExists(new Expr[]{t_1,t_0,n_1,p2,packet,n_source},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
ctx.mkLt(t_0, t_1),
ctx.mkEq(nctx.pf.get("url").apply(p2), f_0),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_RESPONSE)),
ctx.mkNot(((BoolExpr)private_addr_func.apply(n_source))),
(BoolExpr)nctx.recv.apply(n_1, node, p2, t_0),
ctx.mkEq(nctx.pf.get("proto").apply(p2), ctx.mkInt(nctx.HTTP_REQUEST)),
((BoolExpr)private_addr_func.apply(n_1)),
ctx.mkEq(nctx.pf.get("url").apply(p2),nctx.pf.get("url").apply(packet))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{n_0, packet, t_2},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),ctx.mkOr(
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_REQUEST))
,ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_RESPONSE))
)),1,null,null,null,null));
}
}
