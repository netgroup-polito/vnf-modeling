package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class NAT extends NetworkObject{
List<DatatypeExpr> private_addresses;
FuncDecl private_addr_func;
public NAT(Context ctx, Object[]... args) {
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
public void setInternalAddress(ArrayList<DatatypeExpr> internalAddress){
List<BoolExpr> constr = new ArrayList<BoolExpr>();
Expr n_0 = ctx.mkConst("NAT_node", nctx.address);
for(DatatypeExpr n : internalAddress){
constr.add(ctx.mkEq(n_0,n));
}
BoolExpr[] constrs = new BoolExpr[constr.size()];
constraints.add(ctx.mkForall(new Expr[]{n_0}, ctx.mkEq(private_addr_func.apply(n_0),ctx.mkOr(constr.toArray(constrs))),1,null,null,null,null));
}
public void NAT_install(Object public_ip){
private_addr_func = ctx.mkFuncDecl("private_addr_func", nctx.address, ctx.mkBoolSort());
IntExpr t_0 = ctx.mkIntConst(node+"_NAT_t_0");
IntExpr t_1 = ctx.mkIntConst(node+"_NAT_t_1");
IntExpr t_2 = ctx.mkIntConst(node+"_NAT_t_2");
Expr p1 = ctx.mkConst(node+"_NAT_p1",nctx.packet);
Expr n_1 = ctx.mkConst(node+"_NAT_n_1",nctx.node);
Expr p3 = ctx.mkConst(node+"_NAT_p3",nctx.packet);
Expr packet = ctx.mkConst(node+"_NAT_packet",nctx.packet);
Expr n_source = ctx.mkConst(node+"_NAT_n_source",nctx.node);
Expr n_0 = ctx.mkConst(node+"_NAT_n_0",nctx.node);
constraints.add(ctx.mkForall(new Expr[]{t_2,p1,n_0},ctx.mkImplies(ctx.mkAnd(
(BoolExpr)nctx.send.apply(node, n_0, p1, t_2),
ctx.mkNot(((BoolExpr)private_addr_func.apply(nctx.pf.get("dest").apply(p1))))),
ctx.mkExists(new Expr[]{t_1,packet,n_source},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
((BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(packet))),
ctx.mkNot(((BoolExpr)private_addr_func.apply(nctx.pf.get("dest").apply(packet)))),
ctx.mkEq(nctx.pf.get("origin").apply(p1),nctx.pf.get("origin").apply(packet)),
ctx.mkEq(nctx.pf.get("dest").apply(p1),nctx.pf.get("dest").apply(packet)),
ctx.mkEq(nctx.pf.get("body").apply(p1),nctx.pf.get("body").apply(packet)),
ctx.mkEq(nctx.pf.get("proto").apply(p1),nctx.pf.get("proto").apply(packet)),
ctx.mkEq(nctx.pf.get("emailFrom").apply(p1),nctx.pf.get("emailFrom").apply(packet)),
ctx.mkEq(nctx.pf.get("url").apply(p1),nctx.pf.get("url").apply(packet)),
ctx.mkEq(nctx.pf.get("src").apply(p1), ((DatatypeExpr)public_ip))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{t_2,p3,n_0},ctx.mkImplies(ctx.mkAnd(
(BoolExpr)nctx.send.apply(node, n_0, p3, t_2),
ctx.mkNot(((BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(p3)))),
((BoolExpr)private_addr_func.apply(nctx.pf.get("dest").apply(p3)))),
ctx.mkExists(new Expr[]{t_1,t_0,n_1,p1,packet,n_source},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
ctx.mkLt(t_0, t_1),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
ctx.mkNot(((BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(packet)))),
ctx.mkEq(nctx.pf.get("dest").apply(packet), ((DatatypeExpr)public_ip)),
ctx.mkEq(nctx.pf.get("dest").apply(p1),nctx.pf.get("src").apply(packet)),
(BoolExpr)nctx.recv.apply(n_1, node, p1, t_0),
ctx.mkEq(nctx.pf.get("src").apply(packet),nctx.pf.get("dest").apply(p1)),
((BoolExpr)private_addr_func.apply(nctx.pf.get("src").apply(p1))),
ctx.mkEq(nctx.pf.get("origin").apply(p3),nctx.pf.get("origin").apply(packet)),
ctx.mkEq(nctx.pf.get("src").apply(p3),nctx.pf.get("src").apply(packet)),
ctx.mkEq(nctx.pf.get("body").apply(p3),nctx.pf.get("body").apply(packet)),
ctx.mkEq(nctx.pf.get("proto").apply(p3),nctx.pf.get("proto").apply(packet)),
ctx.mkEq(nctx.pf.get("emailFrom").apply(p3),nctx.pf.get("emailFrom").apply(packet)),
ctx.mkEq(nctx.pf.get("url").apply(p3),nctx.pf.get("url").apply(packet)),
ctx.mkEq(nctx.pf.get("dest").apply(p3),nctx.pf.get("src").apply(p1))),1,null,null,null,null)),1,null,null,null,null));
}
}
