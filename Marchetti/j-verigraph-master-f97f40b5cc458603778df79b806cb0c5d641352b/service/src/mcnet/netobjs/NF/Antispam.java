package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class Antispam extends NetworkObject{
FuncDecl Blacklist_func;
ArrayList<Integer> Blacklist_list;
public Antispam(Context ctx, Object[]... args) {
super(ctx, args);
}
@Override
protected void init(Context ctx, Object[]... args) {
super.init(ctx, args);
isEndHost=false;
Blacklist_list = new ArrayList<Integer>();
}
public void addBlacklist_list(ArrayList<Integer> Blacklist_list){
this.Blacklist_list.addAll(Blacklist_list);
}
@Override
protected void addConstraints(Solver solver) {
super.addConstraints(solver);
Blacklist_Constraints(solver);
}
private void Blacklist_Constraints(Solver solver){
if (Blacklist_list.size() == 0)
return;
IntExpr a_0 = ctx.mkIntConst("Antispam_"+node+"_a_0");
BoolExpr[] Blacklist_map = new BoolExpr[Blacklist_list.size()];
for(int y=0;y<Blacklist_list.size();y++){
int tp = Blacklist_list.get(y);
Blacklist_map[y] = ctx.mkEq(a_0,ctx.mkInt(tp));
}
solver.add(ctx.mkForall(new Expr[]{a_0},ctx.mkEq(Blacklist_func.apply(a_0),ctx.mkOr(Blacklist_map)),1,null,null,null,null));
}
public void Antispam_install(){
Blacklist_func = ctx.mkFuncDecl(node+"_Blacklist_func",ctx.mkIntSort(), ctx.mkBoolSort());  
IntExpr t_0 = ctx.mkIntConst(node+"_Antispam_t_0");
IntExpr t_1 = ctx.mkIntConst(node+"_Antispam_t_1");
IntExpr t_2 = ctx.mkIntConst(node+"_Antispam_t_2");
Expr packet = ctx.mkConst(node+"_Antispam_packet",nctx.packet);
Expr n_source = ctx.mkConst(node+"_Antispam_n_source",nctx.node);
Expr n_0 = ctx.mkConst(node+"_Antispam_n_0",nctx.node);
constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(ctx.mkAnd(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_REQUEST))),
ctx.mkExists(new Expr[]{t_1,n_source},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_REQUEST))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(ctx.mkAnd(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_RESPONSE))),
ctx.mkExists(new Expr[]{t_1,n_source},ctx.mkAnd(
 ctx.mkLt(t_1, t_2),
(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_RESPONSE)),
ctx.mkNot((BoolExpr)Blacklist_func.apply(nctx.pf.get("emailFrom").apply(packet)))),1,null,null,null,null)),1,null,null,null,null));
constraints.add(ctx.mkForall(new Expr[]{n_0, packet, t_2},ctx.mkImplies(
(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),ctx.mkOr(
ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_REQUEST))
,ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_RESPONSE))
)),1,null,null,null,null));
}
}
