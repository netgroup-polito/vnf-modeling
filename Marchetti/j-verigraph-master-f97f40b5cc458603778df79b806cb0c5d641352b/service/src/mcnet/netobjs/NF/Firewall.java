package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class Firewall extends NetworkObject{
FuncDecl Acl_func;
ArrayList<Tuple<DatatypeExpr,DatatypeExpr>> Acl_list;
public Firewall(Context ctx, Object[]... args) {
super(ctx, args);
}
@Override
protected void init(Context ctx, Object[]... args) {
super.init(ctx, args);
isEndHost=false;
Acl_list = new ArrayList<Tuple<DatatypeExpr,DatatypeExpr>>();
}
public void addAcl_list(ArrayList<Tuple<DatatypeExpr,DatatypeExpr>> Acl_list){
this.Acl_list.addAll(Acl_list);
}
@Override
protected void addConstraints(Solver solver) {
super.addConstraints(solver);
Acl_Constraints(solver);
}
private void Acl_Constraints(Solver solver){
if (Acl_list.size() == 0)
return;
Expr a_0 = ctx.mkConst("Firewall_"+node+"_a_0",nctx.address);
Expr a_1 = ctx.mkConst("Firewall_"+node+"_a_1",nctx.address);
BoolExpr[] Acl_map = new BoolExpr[Acl_list.size()];
for(int y=0;y<Acl_list.size();y++){
Tuple<DatatypeExpr,DatatypeExpr> tp = Acl_list.get(y);
Acl_map[y] = ctx.mkAnd(ctx.mkEq(a_0,tp._1),ctx.mkEq(a_1,tp._2));
}
solver.add(ctx.mkForall(new Expr[]{a_0,a_1},ctx.mkEq( Acl_func.apply(a_0,a_1),ctx.mkOr(Acl_map)),1,null,null,null,null));
}
public void Firewall_install(){
Acl_func = ctx.mkFuncDecl(node+"_Acl_func",new Sort[]{nctx.address,nctx.address},ctx.mkBoolSort());
IntExpr t_0 = ctx.mkIntConst(node+"_Firewall_t_0");
IntExpr t_1 = ctx.mkIntConst(node+"_Firewall_t_1");
IntExpr t_2 = ctx.mkIntConst(node+"_Firewall_t_2");
Expr packet = ctx.mkConst(node+"_Firewall_packet",nctx.packet);
Expr n_source = ctx.mkConst(node+"_Firewall_n_source",nctx.node);
Expr n_0 = ctx.mkConst(node+"_Firewall_n_0",nctx.node);
constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),
ctx.mkExists(new Expr[]{t_1,n_source},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
ctx.mkNot((BoolExpr)Acl_func.apply(nctx.pf.get("dest").apply(packet),nctx.pf.get("src").apply(packet))),
ctx.mkNot((BoolExpr)Acl_func.apply(nctx.pf.get("src").apply(packet),nctx.pf.get("dest").apply(packet)))),1,null,null,null,null)),1,null,null,null,null));
}
}
