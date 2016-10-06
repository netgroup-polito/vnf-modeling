package it.polito.parser;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.*;

import it.polito.modelib.RoutingResult;

public class ThenConditionVisitor extends ASTVisitor{
	ArrayList<ArrayList<String>> thenConditions = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> prevThenConditions;
	ArrayList<ArrayList<String>> ifConditions;
	ArrayList<IfBlockConditions> ibcList;
	private NFdefinition nfd;
	int analize = 0;

	public ThenConditionVisitor(ArrayList<ArrayList<String>> ifCondition, ArrayList<ArrayList<String>> prevThenConditions, ArrayList<IfBlockConditions> ibcList, NFdefinition nfd) {
		this.ifConditions = ifCondition;
		this.ibcList = ibcList;
		this.prevThenConditions = prevThenConditions;
		if(prevThenConditions!=null) {
			thenConditions.addAll(prevThenConditions);
		}
		this.nfd = nfd;
	}
	
	@Override
	public boolean visit(ReturnStatement node) { //every if block must end with a return
		if(analize>0) { //when analize==0 the return belongs to the current if statemnet 
			analize--;
			return true;
		}
		ReturnStatementVisitor rv = new ReturnStatementVisitor();
		node.accept(rv);
		if(rv.getAction()==RoutingResult.Result.FORWARD) {
			ArrayList<String> t = new ArrayList<String>();
			t.add("return");
			t.add(rv.getPacketName());
			t.add(rv.getForwardDirection().toString());
			thenConditions.add(t);
			IfBlockConditions ibc = new IfBlockConditions();
			ibc.setIfConditions(ifConditions);
			ibc.setThenConditions(thenConditions);
			if(analize==0)
				this.ibcList.add(ibc); //when analyze the retun if the action is forward it add the ibc to the list
		}
		return true;
	}
	
	@Override
	public boolean visit(IfStatement node) {
		analize++; //it say the level of the if block (I don't want to analyze the statement of the inner blocks)
		if(analize==1) { //it starts the same analysis only for the if block of the first level
	
				if(node.getExpression().toString().contains("||")) {
					System.out.println("Split the OR condition in two IF blocks");
				}
				else
				{
					IfConditionVisitor icv = new IfConditionVisitor(this.nfd);
					node.getExpression().accept(icv);
					ArrayList<ArrayList<String>> t = new ArrayList<ArrayList<String>>();
					t.addAll(this.ifConditions);
					t.addAll(icv.getIfConditions());
					ThenConditionVisitor tcv = new ThenConditionVisitor(t,this.thenConditions,ibcList,this.nfd);  //ifConditions and thenConditions of the next block will be added to the current if block
					node.getThenStatement().accept(tcv);
				}
			return true;
		}
		return true;
		
	}
	
	@Override
	public boolean visit(ExpressionStatement node) {
		if(analize==0) { //it analyze only the statement of the current if block (not the inner)
			ThenStatementVisitor tsv = new ThenStatementVisitor(this.nfd);
			node.accept(tsv);
			this.thenConditions.add(tsv.getCondition());
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(Assignment node) { //insert the new variable into nfd
		if(node.getRightHandSide()!=null)
			nfd.setVariable(node.getLeftHandSide().toString(), node.getRightHandSide().toString());
		else
			nfd.setVariable(node.getLeftHandSide().toString(), null);
		return super.visit(node);
	}


	@Override
	public boolean visit(VariableDeclarationStatement node) { //insert the new variable into nfd
		VariableDeclarationFragment vdf = (VariableDeclarationFragment) node.fragments().get(0);
		if(vdf.getInitializer()!=null)
			nfd.setVariable(vdf.getName().toString(), vdf.getInitializer().toString());
		else
			nfd.setVariable(vdf.getName().toString(), null);
		return super.visit(node);
	}
	/*
	public boolean visit(AssertStatement node) {
		System.out.println("ok");
		System.out.println(node.toString());
		return true;
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok1");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok2");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok3");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayAccess node) {
		// TODO Auto-generated method stub
		System.out.println("ok4");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayCreation node) {
		// TODO Auto-generated method stub
		System.out.println("ok5");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayInitializer node) {
		// TODO Auto-generated method stub
		System.out.println("ok6");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayType node) {
		// TODO Auto-generated method stub
		System.out.println("ok7");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(Assignment node) {
		// TODO Auto-generated method stub
		System.out.println("ok8");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(Block node) {
		// TODO Auto-generated method stub
		System.out.println("ok9");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		// TODO Auto-generated method stub
		System.out.println("ok10");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(BooleanLiteral node) {
		// TODO Auto-generated method stub
		System.out.println("ok11");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(BreakStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok12");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(CastExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok13");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(CatchClause node) {
		// TODO Auto-generated method stub
		System.out.println("ok14");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(CharacterLiteral node) {
		// TODO Auto-generated method stub
		System.out.println("ok15");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		// TODO Auto-generated method stub
		System.out.println("ok16");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(CompilationUnit node) {
		// TODO Auto-generated method stub
		System.out.println("ok17");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ConditionalExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok18");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ConstructorInvocation node) {
		// TODO Auto-generated method stub
		System.out.println("ok19");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ContinueStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok20");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(CreationReference node) {
		// TODO Auto-generated method stub
		System.out.println("ok21");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(Dimension node) {
		// TODO Auto-generated method stub
		System.out.println("ok22");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(DoStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok23");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(EmptyStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok24");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok25");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok26");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok27");
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ExpressionMethodReference node) {
		// TODO Auto-generated method stub
		System.out.println("ok28");
		System.out.println(node.toString());
		return super.visit(node);
	}

	

	

	
	@Override
	public boolean visit(FieldAccess node) {
		// TODO Auto-generated method stub
		System.out.println("ok30"); System.out.println(node.toString());
		return super.visit(node);
	}
	

	@Override
	public boolean visit(FieldDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok31"); System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok32");
		System.out.println(node.toString()); return super.visit(node);
	}

	

	@Override
	public boolean visit(ImportDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok34");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(InfixExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok35");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(Initializer node) {
		// TODO Auto-generated method stub
		System.out.println("ok36");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(InstanceofExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok37");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(IntersectionType node) {
		// TODO Auto-generated method stub
		System.out.println("ok38");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(Javadoc node) {
		// TODO Auto-generated method stub
		System.out.println("ok39");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(LabeledStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok40");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(LambdaExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok41");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(LineComment node) {
		// TODO Auto-generated method stub
		System.out.println("ok42");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(MarkerAnnotation node) {
		// TODO Auto-generated method stub
		System.out.println("ok43");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(MemberRef node) {
		// TODO Auto-generated method stub
		System.out.println("ok44");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(MemberValuePair node) {
		// TODO Auto-generated method stub
		System.out.println("ok45");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok46");
		System.out.println(node.toString()); return super.visit(node);
	}

	
	@Override
	public boolean visit(MethodInvocation node) {
		// TODO Auto-generated method stub
		System.out.println("ok47");
		
		System.out.println(node.toString()); return super.visit(node);
	}
	

	@Override
	public boolean visit(MethodRef node) {
		// TODO Auto-generated method stub
		System.out.println("ok48");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(MethodRefParameter node) {
		// TODO Auto-generated method stub
		System.out.println("ok49");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(Modifier node) {
		// TODO Auto-generated method stub
		System.out.println("ok50");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(NameQualifiedType node) {
		// TODO Auto-generated method stub
		System.out.println("ok51");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		// TODO Auto-generated method stub
		System.out.println("ok52");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(NullLiteral node) {
		// TODO Auto-generated method stub
		System.out.println("ok53");
		System.out.println(node.toString()); return super.visit(node);
	}

	
	@Override
	public boolean visit(NumberLiteral node) {
		// TODO Auto-generated method stub
		System.out.println("ok54");
		System.out.println(node.toString()); return super.visit(node);
	}
	

	@Override
	public boolean visit(PackageDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok55");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(ParameterizedType node) {
		// TODO Auto-generated method stub
		System.out.println("ok56");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(ParenthesizedExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok57");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(PostfixExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok58");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(PrefixExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok59");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(PrimitiveType node) {
		// TODO Auto-generated method stub
		System.out.println("ok60");
		System.out.println(node.toString()); return super.visit(node);
	}

	
	@Override
	public boolean visit(QualifiedName node) {
		// TODO Auto-generated method stub
		System.out.println("ok61");
		System.out.println(node.toString()); return super.visit(node);
	}
	

	@Override
	public boolean visit(QualifiedType node) {
		// TODO Auto-generated method stub
		System.out.println("ok62");
		System.out.println(node.toString()); return super.visit(node);
	}

	

	
	@Override
	public boolean visit(SimpleName node) {
		// TODO Auto-generated method stub
		System.out.println("ok64");
		System.out.println(node.toString()); return super.visit(node);
	}
	

	@Override
	public boolean visit(SimpleType node) {
		// TODO Auto-generated method stub
		System.out.println("ok65");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SingleMemberAnnotation node) {
		// TODO Auto-generated method stub
		System.out.println("ok66");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok67");
		System.out.println(node.toString()); return super.visit(node);
	}

	
	@Override
	public boolean visit(StringLiteral node) {
		// TODO Auto-generated method stub
		System.out.println("ok68");
		System.out.println(node.toString()); return super.visit(node);
	}
	

	@Override
	public boolean visit(SuperConstructorInvocation node) {
		// TODO Auto-generated method stub
		System.out.println("ok69");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SuperFieldAccess node) {
		// TODO Auto-generated method stub
		System.out.println("ok70");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SuperMethodInvocation node) {
		// TODO Auto-generated method stub
		System.out.println("ok71");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SuperMethodReference node) {
		// TODO Auto-generated method stub
		System.out.println("ok72");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		// TODO Auto-generated method stub
		System.out.println("ok73");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SwitchStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok74");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok75");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TagElement node) {
		// TODO Auto-generated method stub
		System.out.println("ok76");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TextElement node) {
		// TODO Auto-generated method stub
		System.out.println("ok77");
		System.out.println(node.toString()); return super.visit(node);
	}

	
	@Override
	public boolean visit(ThisExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok78");
		System.out.println(node.toString()); return super.visit(node);
	}
	

	@Override
	public boolean visit(ThrowStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok79");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TryStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok80");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		System.out.println("ok81");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclarationStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok82");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TypeLiteral node) {
		// TODO Auto-generated method stub
		System.out.println("ok83");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TypeMethodReference node) {
		// TODO Auto-generated method stub
		System.out.println("ok84");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(TypeParameter node) {
		// TODO Auto-generated method stub
		System.out.println("ok85");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(UnionType node) {
		// TODO Auto-generated method stub
		System.out.println("ok86");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		// TODO Auto-generated method stub
		System.out.println("ok87");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		// TODO Auto-generated method stub
		System.out.println("ok88");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok89");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		// TODO Auto-generated method stub
		System.out.println("ok90");
		System.out.println(node.toString()); return super.visit(node);
	}

	@Override
	public boolean visit(WildcardType node) {
		// TODO Auto-generated method stub
		System.out.println("ok91");
		System.out.println(node.toString()); return super.visit(node);
	}

	/**/
}
