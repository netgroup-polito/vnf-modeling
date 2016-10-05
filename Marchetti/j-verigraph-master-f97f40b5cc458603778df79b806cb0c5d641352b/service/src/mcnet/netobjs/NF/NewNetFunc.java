package mcnet.netobjs.NF;
import java.util.*;
import com.microsoft.z3.*;
import mcnet.components.*;
public class NewNetFunc extends NetworkObject{
	FuncDecl Blacklist2_func;
	ArrayList<Integer> Blacklist2_list;
	FuncDecl Blacklist_func;
	ArrayList<NetworkObject> Blacklist_list;
	public NewNetFunc(Context ctx, Object[]... args) {
		super(ctx, args);
	}
	@Override
	protected void init(Context ctx, Object[]... args) {
		super.init(ctx, args);
		isEndHost=true;
		Blacklist2_list = new ArrayList<Integer>();
		Blacklist_list = new ArrayList<NetworkObject>();
	}
	public void addBlacklist2_list(ArrayList<Integer> Blacklist2_list){
		this.Blacklist2_list.addAll(Blacklist2_list);
	}
	public void addBlacklist_list(ArrayList<NetworkObject> Blacklist_list){
		this.Blacklist_list.addAll(Blacklist_list);
	}
	@Override
	protected void addConstraints(Solver solver) {
		super.addConstraints(solver);
		Blacklist2_Constraints(solver);
		Blacklist_Constraints(solver);
	}
	private void Blacklist2_Constraints(Solver solver){
		if (Blacklist2_list.size() == 0)
			return;
		IntExpr a_0 = ctx.mkIntConst("NewNetFunc_"+node+"_a_0");
		BoolExpr[] Blacklist2_map = new BoolExpr[Blacklist2_list.size()];
		for(int y=0;y<Blacklist2_list.size();y++){
			int tp = Blacklist2_list.get(y);
			Blacklist2_map[y] = ctx.mkEq(a_0,ctx.mkInt(tp));
		}
		solver.add(ctx.mkForall(new Expr[]{a_0},ctx.mkEq(Blacklist2_func.apply(a_0),ctx.mkOr(Blacklist2_map)),1,null,null,null,null));
	}
	private void Blacklist_Constraints(Solver solver){
		if (Blacklist_list.size() == 0)
			return;
		Expr a_0 = ctx.mkConst("NewNetFunc_"+node+"_a_0",nctx.node);
		BoolExpr[] Blacklist_map = new BoolExpr[Blacklist_list.size()];
		for(int y=0;y<Blacklist_list.size();y++){
			NetworkObject tp = Blacklist_list.get(y);
			Blacklist_map[y] = ctx.mkEq(a_0,tp.getZ3Node());
		}
		solver.add(ctx.mkForall(new Expr[]{a_0},ctx.mkEq(Blacklist_func.apply(a_0),ctx.mkOr(Blacklist_map)),1,null,null,null,null));
	}
	public void NewNetFunc_install(Object ip_mailServer){
		Blacklist2_func = ctx.mkFuncDecl(node+"_Blacklist2_func",ctx.mkIntSort(), ctx.mkBoolSort());  
		Blacklist_func = ctx.mkFuncDecl(node+"_Blacklist_func",nctx.node, ctx.mkBoolSort());  
		IntExpr t_0 = ctx.mkIntConst(node+"_NewNetFunc_t_0");
		IntExpr t_1 = ctx.mkIntConst(node+"_NewNetFunc_t_1");
		IntExpr t_2 = ctx.mkIntConst(node+"_NewNetFunc_t_2");
		Expr packet = ctx.mkConst(node+"_NewNetFunc_packet",nctx.packet);
		Expr n_source = ctx.mkConst(node+"_NewNetFunc_n_source",nctx.node);
		Expr n_0 = ctx.mkConst(node+"_NewNetFunc_n_0",nctx.node);
		constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(ctx.mkAnd(
				(BoolExpr)nctx.send.apply(node, n_0, packet, t_2),
				ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.HTTP_RESPONSE))),
				ctx.mkExists(new Expr[]{t_0,t_1,packet,n_source},ctx.mkAnd(
						ctx.mkLt(t_1, t_2),
						ctx.mkLt(t_0, t_1),
						(BoolExpr)nctx.recv.apply(n_source, node, packet, t_1),
						ctx.mkNot((BoolExpr)Blacklist_func.apply(nctx.pf.get("origin").apply(packet))),
						(BoolExpr)Blacklist2_func.apply((IntExpr)nctx.src_port.apply(packet)),
						ctx.mkOr((BoolExpr)Blacklist2_func.apply((IntExpr)nctx.src_port.apply(packet)),ctx.mkNot((BoolExpr)Blacklist2_func.apply((IntExpr)nctx.src_port.apply(packet))))),1,null,null,null,null)),1,null,null,null,null));
		constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(
				(BoolExpr)nctx.recv.apply(n_0, node, packet, t_0),
				ctx.mkExists(new Expr[]{t_0,t_1},ctx.mkAnd(
						ctx.mkLt(t_1, t_2),
						ctx.mkLt(t_0, t_1),
						(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("dest").apply(packet))),1,null,null,null,null)),1,null,null,null,null));
		constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(
				(BoolExpr)nctx.send.apply(node, n_0, packet, t_0),
				ctx.mkExists(new Expr[]{t_0,t_1},ctx.mkAnd(
						ctx.mkLt(t_1, t_2),
						ctx.mkLt(t_0, t_1),
						(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get("src").apply(packet)),
						ctx.mkEq(nctx.pf.get("body").apply(packet),nctx.pf.get("orig_body").apply(packet)),
						ctx.mkEq(nctx.pf.get("proto").apply(packet), ctx.mkInt(nctx.POP3_REQUEST)),
						ctx.mkEq(nctx.pf.get("origin").apply(packet), ((NetworkObject)ip_mailServer).getZ3Node())),1,null,null,null,null)),1,null,null,null,null));
	}
}
