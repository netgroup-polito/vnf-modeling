package it.polito.parser;

import java.io.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;



import definitions.FieldType;











public class CreateNFClass {

	private NFdefinition nfd;

	public CreateNFClass(NFdefinition nfd) {
		this.nfd = nfd;
	}

	public void writeClass(String path) {
		try {
		    File file = new File(path+File.separator+nfd.getName()+".java");
		    
		    if (file.exists())
		      System.out.println("The file " + path +File.separator+nfd.getName()+".java"+ " exists");
		    else if (file.createNewFile())
		      System.out.println("The file " + path +File.separator+nfd.getName()+".java"+ " has been created");
		    else
		      System.out.println("The file " + path +File.separator+nfd.getName()+".java"+ " cannot be created");
		    FileWriter fw = new FileWriter(file);
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write("package mcnet.netobjs.NF;\r\n");
		    bw.write("import java.util.*;\r\n");
		    bw.write("import com.microsoft.z3.*;\r\n");
		    bw.write("import mcnet.components.*;\r\n");
		    //---------------------------------------------------------------
		    bw.write("public class "+nfd.getName()+" extends NetworkObject{\r\n");
		    
		    if(nfd.getInternalNodesPresence()==true) {
		    	if(nfd.hasPrivateAddresses()==false) {
		    		bw.write("List<DatatypeExpr> private_addresses;\r\n");
		    	}
		    	else {
		    		bw.write("List<DatatypeExpr> private_addresses;\r\n");
		    	}
			    bw.write("FuncDecl private_addr_func;\r\n");
		    }
		    
		    for(String htName : nfd.getHostTableList().keySet()) {
		    	if(nfd.getHostTableStatic().get(htName)==true) {
		    		 bw.write("FuncDecl "+htName+"_func;\r\n"); //FuncDecl Blacklist_func;
		    		 if(nfd.getHostTableList().get(htName).size()==1) { //ArrayList<Integer> Blacklist_list;
		    			 if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_SRC") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_DEST") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN") ) {
		    				 if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN")) {
		    					 bw.write("ArrayList<NetworkObject> "+htName+"_list;\r\n");
		    				 }
		    				 else {
		    					 bw.write("ArrayList<DatatypeExpr> "+htName+"_list;\r\n");
		    				 }
		    			 }
		    			 else {
		    				 bw.write("ArrayList<Integer> "+htName+"_list;\r\n");
		    			 }
		    		 }
		    		 else { //ArrayList<Tuple<DatatypeExpr,DatatypeExpr>> Acl_list;
		    			 bw.write("ArrayList<Tuple<");
		    			 int i = 0;
		    			 for(String field : nfd.getHostTableList().get(htName)) {
		    				 if(i==0) {
		    					 if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						 if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							 bw.write("NetworkObject");
		    						 }
		    						 else {
		    							 bw.write("DatatypeExpr");
		    						 }
		    					 }
		    					 else {
		    						 bw.write("Integer");
		    					 }
		    				 }
		    				 else {
		    					 if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						 if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							 bw.write(",NetworkObject");
		    						 }
		    						 else {
		    							 bw.write(",DatatypeExpr");
		    						 }
		    					 }
		    					 else {
		    						 bw.write(",Integer");
		    					 }
		    				 }
		    				 i++;
		    			 }
		    			 bw.write(">> "+htName+"_list;\r\n");
		    		 }
		    	}
		    }
		    //------------------------------------------------------------------
		    bw.write("public "+nfd.getName()+"(Context ctx, Object[]... args) {\r\n");
		    bw.write("super(ctx, args);\r\n");
		    bw.write("}\r\n");
		    //---------------------------------------------------------------
		    bw.write("@Override\r\n");
		    bw.write("protected void init(Context ctx, Object[]... args) {\r\n");
		    bw.write("super.init(ctx, args);\r\n");
		    
		    bw.write("isEndHost="+nfd.isEndHost+";\r\n");
		    
		    if(nfd.getInternalNodesPresence()==true) {
			    bw.write("private_addresses = new ArrayList<DatatypeExpr>();\r\n");
		    }
		    
		    for(String htName : nfd.getHostTableList().keySet()) {
		    	if(nfd.getHostTableStatic().get(htName)==true) {
		    		 if(nfd.getHostTableList().get(htName).size()==1) { //Blacklist_list = new ArrayList<Integer>();
		    			 if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_SRC") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_DEST") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN") ) {
		    				 if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN")) {
		    					 bw.write(htName+"_list = new ArrayList<NetworkObject>();\r\n");
		    				 }
		    				 else {
		    					 bw.write(htName+"_list = new ArrayList<DatatypeExpr>();\r\n");
		    				 }
		    			 }
		    			 else {
		    				 bw.write(htName+"_list = new ArrayList<Integer>();\r\n");
		    			 }
		    		 }
		    		 else { //Acl_list = new ArrayList<Tuple<DatatypeExpr,DatatypeExpr>>();
		    			 bw.write(htName+"_list = new ArrayList<Tuple<");
		    			 int i = 0;
		    			 for(String field : nfd.getHostTableList().get(htName)) {
		    				 if(i==0) {
		    					 if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						 if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							 bw.write("NetworkObject");
		    						 }
		    						 else {
		    							 bw.write("DatatypeExpr");
		    						 }
		    					 }
		    					 else {
		    						 bw.write("Integer");
		    					 }
		    				 }
		    				 else {
		    					 if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						 if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							 bw.write(",NetworkObject");
		    						 }
		    						 else {
		    							 bw.write(",DatatypeExpr");
		    						 }
		    					 }
		    					 else {
		    						 bw.write(",Integer");
		    					 }
		    				 }
		    				 i++;
		    			 }
		    			 bw.write(">>();\r\n");
		    		 }
		    	}
		    }
		    
		    bw.write("}\r\n");
		    //----------------------------------------------------------------------
		    if(nfd.getInternalNodesPresence()==true) {
		    	if(nfd.hasPrivateAddresses()==false) {
				    bw.write("private void addPrivateAdd(List<DatatypeExpr> address){\r\n");
				    bw.write("private_addresses.addAll(address);\r\n");
				    bw.write("}\r\n");
				    bw.write("public List<DatatypeExpr> getPrivateAddress(){\r\n");
				    bw.write("return private_addresses;\r\n");
				    bw.write("}\r\n");
				    bw.write("public void setInternalAddress(ArrayList<DatatypeExpr> internalAddress){\r\n");
				    bw.write("List<BoolExpr> constr = new ArrayList<BoolExpr>();\r\n");
				    bw.write("Expr n_0 = ctx.mkConst(\""+nfd.getName()+"_node\", nctx.address);\r\n");
				    bw.write("for(DatatypeExpr n : internalAddress){\r\n");
				    bw.write("constr.add(ctx.mkEq(n_0,n));\r\n");
				    bw.write("}\r\n");
				    bw.write("BoolExpr[] constrs = new BoolExpr[constr.size()];\r\n");
				    bw.write("constraints.add(ctx.mkForall(new Expr[]{n_0}, ctx.mkEq(private_addr_func.apply(n_0),ctx.mkOr(constr.toArray(constrs))),1,null,null,null,null));\r\n");
				    bw.write("}\r\n");
		    	}
		    	else {
		    		bw.write("private void addPrivateAdd(List<DatatypeExpr> address){\r\n");
				    bw.write("private_addresses.addAll(address);\r\n");
				    bw.write("}\r\n");
				    bw.write("public List<DatatypeExpr> getPrivateAddress(){\r\n");
				    bw.write("return private_addresses;\r\n");
				    bw.write("}\r\n");
				    bw.write("public void setInternalAddress(ArrayList<NetworkObject> internalAddress){\r\n");
				    bw.write("List<BoolExpr> constr = new ArrayList<BoolExpr>();\r\n");
				    bw.write("Expr n_0 = ctx.mkConst(\""+nfd.getName()+"_node\", nctx.node);\r\n");
				    bw.write("for(NetworkObject n : internalAddress){\r\n");
				    bw.write("constr.add(ctx.mkEq(n_0,n.getZ3Node()));\r\n");
				    bw.write("}\r\n");
				    bw.write("BoolExpr[] constrs = new BoolExpr[constr.size()];\r\n");
				    bw.write("constraints.add(ctx.mkForall(new Expr[]{n_0}, ctx.mkEq(private_addr_func.apply(n_0),ctx.mkOr(constr.toArray(constrs))),1,null,null,null,null));\r\n");
				    bw.write("}\r\n");
		    	}
		    }
		    //------------------------------------------------------------------------------
			boolean staticPresence = false; //I use this variable to detect if there are static table 
		    for(String htName : nfd.getHostTableList().keySet()) {
		    	if(nfd.getHostTableStatic().get(htName)==true) {
		    		staticPresence = true;
		    		bw.write("public void add"+htName+"_list("); //public void addBlacklist_list(
		    		if(nfd.getHostTableList().get(htName).size()==1) { //ArrayList<Integer> Blacklist_list
		    			if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_SRC") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_DEST") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN") ) {
		    				if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN")) {
		    					bw.write("ArrayList<NetworkObject> "+htName+"_list){\r\n");
		    				}
		    				else {
		    					bw.write("ArrayList<DatatypeExpr> "+htName+"_list){\r\n");
		    				}
		    			}
		    			else {
		    				bw.write("ArrayList<Integer> "+htName+"_list){\r\n");
		    			}
		    		}
		    		else { //ArrayList<Tuple<DatatypeExpr,DatatypeExpr>> Acl_list
		    			bw.write("ArrayList<Tuple<");
		    			int i = 0;
		    			for(String field : nfd.getHostTableList().get(htName)) {
		    				if(i==0) {
		    					 if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						 if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							 bw.write("NetworkObject");
		    						 }
		    						 else {
		    							 bw.write("DatatypeExpr");
		    						 }
		    					 }
		    					 else {
		    						 bw.write("Integer");
		    					 }
		    				}
		    				else {
		    					if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							bw.write(",NetworkObject");
		    						}
		    						else {
		    							bw.write(",DatatypeExpr");
		    						}
		    					 }
		    					 else {
		    						 bw.write(",Integer");
		    					 }
		    				}
		    				i++;
		    			}
		    			bw.write(">> "+htName+"_list){\r\n");
		    		}
		    		bw.write("this."+htName+"_list.addAll("+htName+"_list);\r\n");
		    		bw.write("}\r\n");
		    	}
		    }
		    //-----------------------------------------------------------------------------------------------
		    if(staticPresence==true) {
		    	bw.write("@Override\r\n");
		    	bw.write("protected void addConstraints(Solver solver) {\r\n");
		    	bw.write("super.addConstraints(solver);\r\n");
		    	for(String htName : nfd.getHostTableList().keySet()) {
			    	if(nfd.getHostTableStatic().get(htName)==true) {
			    		bw.write(htName+"_Constraints(solver);\r\n");
			    	}
			    }
		    	bw.write("}\r\n");
		    }
		    //----------------------------------------------------------------------------------------------
		    for(String htName : nfd.getHostTableList().keySet()) {
		    	if(nfd.getHostTableStatic().get(htName)==true) {
		    		bw.write("private void "+htName+"_Constraints(Solver solver){\r\n");
		    		bw.write("if ("+htName+"_list.size() == 0)\r\n");
		    		bw.write("return;\r\n");
		    		int i = 0;
		    		for(String field : nfd.getHostTableList().get(htName)) {
		    			if(field.equalsIgnoreCase("FieldType.ORIGIN")) {
		    				bw.write("Expr a_"+i+" = ctx.mkConst(\""+nfd.getName()+"_\"+node+\"_a_"+i+"\",nctx.node);\r\n");
		    			}
		    			if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    					field.equalsIgnoreCase("FieldType.IP_DEST")) {
		    				bw.write("Expr a_"+i+" = ctx.mkConst(\""+nfd.getName()+"_\"+node+\"_a_"+i+"\",nctx.address);\r\n"); //Expr a_0 = ctx.mkConst("Firewall_"+node+"_a_0",nctx.address);
		    			}
		    			if(field.equalsIgnoreCase("FieldType.SRC_PORT") ||
		    					field.equalsIgnoreCase("FieldType.DEST_PORT") ||
		    					field.equalsIgnoreCase("FieldType.URL") ||
		    					field.equalsIgnoreCase("FieldType.PROTOCOL") ||
		    					field.equalsIgnoreCase("FieldType.MAIL_FROM") ||
		    					field.equalsIgnoreCase("FieldType.MAIL_TO") ||
		    					field.equalsIgnoreCase("FieldType.BODY")) {
		    				bw.write("IntExpr a_"+i+" = ctx.mkIntConst(\""+nfd.getName()+"_\"+node+\"_a_"+i+"\");\r\n"); //IntExpr a_0 = ctx.mkIntConst("Antispam_"+node+"_a_0");
		    			}
		    			i++;
	    			}
		    		bw.write("BoolExpr[] "+htName+"_map = new BoolExpr["+htName+"_list.size()];\r\n");
		    		bw.write("for(int y=0;y<"+htName+"_list.size();y++){\r\n");
		    		if(nfd.getHostTableList().get(htName).size()==1) { //int tp = Blacklist_list.get(y);   Blacklist_map[y] = ctx.mkEq(a_0,ctx.mkInt(tp));
		    			if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_SRC") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_DEST") ||
		    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN") ) {
		    				if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN")) {
		    					bw.write("NetworkObject tp = "+htName+"_list.get(y);\r\n");
		    					bw.write(htName+"_map[y] = ctx.mkEq(a_0,tp.getZ3Node());\r\n");
		    				}
		    				else {
		    					bw.write("DatatypeExpr tp = "+htName+"_list.get(y);\r\n");
		    					bw.write(htName+"_map[y] = ctx.mkEq(a_0,tp);\r\n");
		    				}
		    				
		    				
		    			}
		    			else {
		    				bw.write("int tp = "+htName+"_list.get(y);\r\n");
		    				bw.write(htName+"_map[y] = ctx.mkEq(a_0,ctx.mkInt(tp));\r\n");
		    			}
		    			
		    			bw.write("}\r\n");
		    			bw.write("solver.add(ctx.mkForall(new Expr[]{a_0},ctx.mkEq("+htName+"_func.apply(a_0),ctx.mkOr("+htName+"_map)),1,null,null,null,null));\r\n");
		    		}
		    		else {  //Tuple<DatatypeExpr,DatatypeExpr> tp = Acl_list.get(y);   Acl_map[y] = ctx.mkAnd(ctx.mkEq(a_0,tp._1),ctx.mkEq(a_1,tp._2));
		    			bw.write("Tuple<");
		    			i = 0;
		    			for(String field : nfd.getHostTableList().get(htName)) {
		    				if(i==0) {
		    					if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							bw.write("NetworkObject");
		    						}
		    						else {
		    						 bw.write("DatatypeExpr");
		    						}
		    					 }
		    					 else {
		    						 bw.write("Integer");
		    					 }
		    				}
		    				else {
		    					if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							bw.write(",NetworkObject");
		    						}
		    						else {
		    						 bw.write(",DatatypeExpr");
		    						}
		    					 }
		    					 else {
		    						 bw.write(",Integer");
		    					 }
		    				}
		    				i++;
		    			}
		    			bw.write("> tp = "+htName+"_list.get(y);\r\n");
		    			bw.write(htName+"_map[y] = ctx.mkAnd(");
		    			i = 0;
		    			for(String field : nfd.getHostTableList().get(htName)) {
		    				if(i==0) {
		    					if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							bw.write("ctx.mkEq(a_"+i+",tp._"+(i+1)+".getZ3Node())");
		    						}
		    						else {
		    							bw.write("ctx.mkEq(a_"+i+",tp._"+(i+1)+")");
		    						}
		    					}
		    					else {
		    						bw.write("ctx.mkEq(a_"+i+",ctx.mkInt(tp._"+(i+1)+"))");
		    					}
		    				}
		    				else {
		    					if(field.equalsIgnoreCase("FieldType.IP_SRC") ||
		    							 field.equalsIgnoreCase("FieldType.IP_DEST") ||
		    							 field.equalsIgnoreCase("FieldType.IP_ORIGIN") 
		    							 ) {
		    						if(field.equalsIgnoreCase("FieldType.IP_ORIGIN")) {
		    							bw.write(",ctx.mkEq(a_"+i+",tp._"+(i+1)+".getZ3Node())");
		    						}
		    						else {
		    							bw.write(",ctx.mkEq(a_"+i+",tp._"+(i+1)+")");
		    						}
		    					}
		    					else {
		    						bw.write(",ctx.mkEq(a_"+i+",ctx.mkInt(tp._"+(i+1)+"))");
		    					}
		    				}
		    				i++;
		    			}
		    			bw.write(");\r\n");
		    			bw.write("}\r\n");
		    			bw.write("solver.add(ctx.mkForall(new Expr[]{");
		    			i = 0;
		    			for(String field : nfd.getHostTableList().get(htName)) {
		    				if(i==0) {
		    					bw.write("a_"+i);
		    				}
		    				else {
		    					bw.write(",a_"+i);
		    				}
		    				i++;
		    			}
		    			bw.write("},ctx.mkEq( "+htName+"_func.apply(");
		    			i = 0;
		    			for(String field : nfd.getHostTableList().get(htName)) {
		    				if(i==0) {
		    					bw.write("a_"+i);
		    				}
		    				else {
		    					bw.write(",a_"+i);
		    				}
		    				i++;
		    			}
		    			bw.write("),ctx.mkOr("+htName+"_map)),1,null,null,null,null));\r\n");
		    		}
		    		bw.write("}\r\n");
		    	}
		    }
		    
		    //------------------------------------------------------------------------------------
		    
		    bw.write("public void "+nfd.getName()+"_install(");
		    int i = 0;
		    for(String s : nfd.getPropertyList()) {
		    	if(i==0) {
		    		bw.write("Object "+s);
		    	}
		    	else {
		    		bw.write(",Object "+s);
		    	}
		    }
			bw.write("){\r\n");
			if(nfd.getInternalNodesPresence()==true) {
				if(nfd.hasPrivateAddresses()==false) {
					bw.write("private_addr_func = ctx.mkFuncDecl(\"private_addr_func\", nctx.address, ctx.mkBoolSort());\r\n");
				}
				else {
					bw.write("private_addr_func = ctx.mkFuncDecl(\"private_addr_func\", nctx.node, ctx.mkBoolSort());\r\n");
				}
			}
			for(String htName : nfd.getHostTableList().keySet()) {
		    	if(nfd.getHostTableList().get(htName).size()==1 && nfd.getHostTableStatic().get(htName)==true) { //Blacklist_func = ctx.mkFuncDecl(node+"_Blacklist_func",ctx.mkIntSort(), ctx.mkBoolSort());
		    		if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_SRC") ||
	    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.IP_DEST") ) {
		    			bw.write(htName+"_func = ctx.mkFuncDecl(node+\"_"+htName+"_func\",nctx.address, ctx.mkBoolSort());  \r\n");
		    		}
		    		if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.ORIGIN") ) {
		    			bw.write(htName+"_func = ctx.mkFuncDecl(node+\"_"+htName+"_func\",nctx.node, ctx.mkBoolSort());  \r\n");
		    		}
		    		if(nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.SRC_PORT") ||
	    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.DEST_PORT") ||
	    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.URL") ||
	    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.PROTOCOL") ||
	    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.MAIL_FROM") ||
	    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.MAIL_TO") ||
	    					 nfd.getHostTableList().get(htName).get(0).equalsIgnoreCase("FieldType.BODY") ) {
		    			bw.write(htName+"_func = ctx.mkFuncDecl(node+\"_"+htName+"_func\",ctx.mkIntSort(), ctx.mkBoolSort());  \r\n");
		    		}
		    	}
		    	else { //Acl_func = ctx.mkFuncDecl(node+"_Acl_func",new Sort[]{nctx.address,nctx.address},ctx.mkBoolSort());
		    		if(nfd.getHostTableStatic().get(htName)==true) {
		    			bw.write(htName+"_func = ctx.mkFuncDecl(node+\"_"+htName+"_func\",new Sort[]{");
		    		}
		    		else {
		    			bw.write("FuncDecl "+htName+"_func = ctx.mkFuncDecl(node+\"_"+htName+"_func\",new Sort[]{");
		    		}
		    		i = 0;
		    		for(String field : nfd.getHostTableList().get(htName)) {
		    			if(i==0) {
		    				if(field.equalsIgnoreCase("FieldType.IP_SRC") || 
		    						field.equalsIgnoreCase("FieldType.IP_DEST")) {
		    					bw.write("nctx.address");
		    				}
		    				if(field.equalsIgnoreCase("FieldType.ORIGIN")) {
		    					bw.write("nctx.node");
		    				}
		    				if(field.equalsIgnoreCase("FieldType.SRC_PORT") ||
		    						field.equalsIgnoreCase("FieldType.DEST_PORT") ||
		    						field.equalsIgnoreCase("FieldType.URL") ||
		    						field.equalsIgnoreCase("FieldType.PROTOCOL") ||
		    						field.equalsIgnoreCase("FieldType.MAIL_FROM") ||
		    						field.equalsIgnoreCase("FieldType.MAIL_TO") ||
		    						field.equalsIgnoreCase("FieldType.BODY")) {
		    					bw.write("ctx.mkIntSort()");
		    				}
		    			}
		    			else {
		    				if(field.equalsIgnoreCase("FieldType.IP_SRC") || 
		    						field.equalsIgnoreCase("FieldType.IP_DEST")) {
		    					bw.write(",nctx.address");
		    				}
		    				if(field.equalsIgnoreCase("FieldType.ORIGIN")) {
		    					bw.write(",nctx.node");
		    				}
		    				if(field.equalsIgnoreCase("FieldType.SRC_PORT") ||
		    						field.equalsIgnoreCase("FieldType.DEST_PORT") ||
		    						field.equalsIgnoreCase("FieldType.URL") ||
		    						field.equalsIgnoreCase("FieldType.PROTOCOL") ||
		    						field.equalsIgnoreCase("FieldType.MAIL_FROM") ||
		    						field.equalsIgnoreCase("FieldType.MAIL_TO") ||
		    						field.equalsIgnoreCase("FieldType.BODY")) {
		    					bw.write(",ctx.mkIntSort()");
		    				}
		    				
		    			}
		    			i++;
		    		}
		    		if(nfd.getHostTableStatic().get(htName)==true) {
		    			bw.write("},ctx.mkBoolSort());\r\n");
		    		}
		    		else {
		    			bw.write(",ctx.mkIntSort()},ctx.mkBoolSort());\r\n");
		    		}
		    	}	
		    }
			HashMap<String,String> beforeHM = new HashMap<String,String>();
			HashMap<String,String> afterHM = new HashMap<String,String>();
			HashMap<String,String> storeC = new HashMap<String,String>();
			for(RightFormImplication rfi : nfd.getRightFormImplication()) { //detect all the variable that must be created
				for(Condition c : rfi.getBeforeImplication()) {
					switch(c.getType()) {
					case internal_node:
						break;
					case is_in_table:
						break;
					case node_has_addr:
						break;
					case packet_body:
						break;
					case packet_destination:
						break;
					case packet_email_from:
						break;
					case packet_email_to:
						break;
					case packet_origin:
						break;
					case packet_port_destination:
						break;
					case packet_port_source:
						break;
					case packet_protocol:
						break;
					case packet_source:
						break;
					case packet_url:
						break;
					case recv:
						if(!((RecvCondition)c).getSourceNode().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((RecvCondition)c).getSourceNode(), "nctx.node");
						}
						if(!((RecvCondition)c).getDestNode().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((RecvCondition)c).getDestNode(), "nctx.node");
						}
						beforeHM.put(((RecvCondition)c).getPacket(), "nctx.packet");
						break;
					case return_type:
						break;
					case send:
						if(!((SendCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((SendCondition)c).getSource(), "nctx.node");
						}
						if(!((SendCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((SendCondition)c).getDestination(), "nctx.node");
						}
						beforeHM.put(((SendCondition)c).getPacket(), "nctx.packet");
						break;
					case store_in_table:
						int f_i = 0;
						for(String f : ((StoreTableCondition)c).field) {
							beforeHM.put("f_"+f_i, "int");
							storeC.put("f_"+f_i, f);
							f_i++;
						}
						break;
					default:
						break;
					
					}
				}
				for(Condition c : rfi.getAfterImplication()) {
					switch(c.getType()) {
					case internal_node:
						break;
					case is_in_table:
						break;
					case node_has_addr:
						break;
					case packet_body:
						break;
					case packet_destination:
						break;
					case packet_email_from:
						break;
					case packet_email_to:
						break;
					case packet_origin:
						break;
					case packet_port_destination:
						break;
					case packet_port_source:
						break;
					case packet_protocol:
						break;
					case packet_source:
						break;
					case packet_url:
						break;
					case recv:
						if(!((RecvCondition)c).getSourceNode().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((RecvCondition)c).getSourceNode(), "nctx.node");
						}
						if(!((RecvCondition)c).getDestNode().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((RecvCondition)c).getDestNode(), "nctx.node");
						}
						beforeHM.put(((RecvCondition)c).getPacket(), "nctx.packet");
						break;
					case return_type:
						break;
					case send:
						if(!((SendCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((SendCondition)c).getSource(), "nctx.node");
						}
						if(!((SendCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((SendCondition)c).getDestination(), "nctx.node");
						}
						beforeHM.put(((SendCondition)c).getPacket(), "nctx.packet");
						break;
					case store_in_table:
						int f_i = 0;
						for(String f : ((StoreTableCondition)c).field) {
							beforeHM.put("f_"+f_i, "int");
							storeC.put("f_"+f_i, f);
							f_i++;
						}
						break;
					default:
						break;
					
					}
				}
			}
			bw.write("IntExpr t_0 = ctx.mkIntConst(node+\"_"+nfd.getName()+"_t_0\");\r\n");
			bw.write("IntExpr t_1 = ctx.mkIntConst(node+\"_"+nfd.getName()+"_t_1\");\r\n");
			bw.write("IntExpr t_2 = ctx.mkIntConst(node+\"_"+nfd.getName()+"_t_2\");\r\n");
			for(String s : beforeHM.keySet()) {
				if(beforeHM.get(s).equalsIgnoreCase("int")) {
					bw.write("IntExpr "+s+" = ctx.mkIntConst(node+\"_"+nfd.getName()+"_"+s+"\");\r\n");
				}
				else {
					bw.write("Expr "+s+" = ctx.mkConst(node+\"_"+nfd.getName()+"_"+s+"\","+beforeHM.get(s)+");\r\n");
				}
			}
			for(String s : afterHM.keySet()) {
				if(afterHM.get(s).equalsIgnoreCase("int")) {
					bw.write("IntExpr "+s+" = ctx.mkIntConst(node+\"_"+nfd.getName()+"_"+s+"\");\r\n");
				}
				else {
					bw.write("Expr "+s+" = ctx.mkConst(node+\"_"+nfd.getName()+"_"+s+"\","+afterHM.get(s)+");\r\n");
				}
			}
			for(RightFormImplication rfi : nfd.getRightFormImplication()) {
				beforeHM = new HashMap<String,String>();
				afterHM = new HashMap<String,String>();
				storeC = new HashMap<String,String>();
				for(Condition c : rfi.getBeforeImplication()) { //detect all the constants for corrent implication before block
					switch(c.getType()) {
					case internal_node:
						if(!((InternalNodeCondition)c).getNode().equalsIgnoreCase(nfd.getName()) && nfd.hasPrivateAddresses()==true) {
							beforeHM.put(((InternalNodeCondition)c).getNode(), "nctx.node");
						}
						break;
					case is_in_table:
						break;
					case node_has_addr:
						break;
					case packet_body:
						break;
					case packet_destination:
						break;
					case packet_email_from:
						break;
					case packet_email_to:
						break;
					case packet_origin:
						break;
					case packet_port_destination:
						break;
					case packet_port_source:
						break;
					case packet_protocol:
						break;
					case packet_source:
						break;
					case packet_url:
						break;
					case recv:
						if(!((RecvCondition)c).getSourceNode().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((RecvCondition)c).getSourceNode(), "nctx.node");
						}
						if(!((RecvCondition)c).getDestNode().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((RecvCondition)c).getDestNode(), "nctx.node");
						}
						beforeHM.put(((RecvCondition)c).getPacket(), "nctx.packet");
						break;
					case return_type:
						break;
					case send:
						if(!((SendCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((SendCondition)c).getSource(), "nctx.node");
						}
						if(!((SendCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
							beforeHM.put(((SendCondition)c).getDestination(), "nctx.node");
						}
						beforeHM.put(((SendCondition)c).getPacket(), "nctx.packet");
						break;
					case store_in_table:
						int f_i = 0;
						for(String f : ((StoreTableCondition)c).field) {
							beforeHM.put("f_"+f_i, "int");
							storeC.put("f_"+f_i, f);
							f_i++;
						}
						break;
					default:
						break;
					
					}
				}
				bw.write("constraints.add(ctx.mkForall(new Expr[]{t_2"); //constraints.add(ctx.mkForall(new Expr[]{t_2,packet,n_0},ctx.mkImplies(ctx.mkAnd(
				for(String s : beforeHM.keySet()) {
					bw.write(","+s);
				}
				if(rfi.getBeforeImplication().size()>1) {
					bw.write("},ctx.mkImplies(ctx.mkAnd(\r\n");
				}
				else {
					bw.write("},ctx.mkImplies(\r\n");
				}
				int j = 0;
				int z = 0;
				for(Condition c : rfi.getBeforeImplication()) { //insert all the condition of the before block
					switch(c.getType()) {
					case internal_node:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(nfd.hasPrivateAddresses()==false) {
								if(c.getValidity()==false) {
									bw.write("ctx.mkNot(");
									String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
									}
									else {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
									}
									bw.write(")");
								}
								else {
									String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
									}
									else {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
									}
								}
							}
							if(nfd.hasPrivateAddresses()==true) {
								if(c.getValidity()==false) {
									bw.write("ctx.mkNot(");
									bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
									bw.write(")");
								}
								else {
									bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
								}
							}
							z++;
						break;
					case is_in_table:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								int x = 0;
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write("))");
							}
							else {
								int x = 0;
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write(")");
							}
							z++;
						break;
					case node_has_addr:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
							}
							z++;
						break;
					case packet_body:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
							}
							z++;
						//}
						break;
					case packet_destination:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
							z++;
						break;
					case packet_email_from:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						break;
					case packet_origin:
							if(z!=0) {
								bw.write(",\r\n");
							}

							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						break;
					case packet_port_destination:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						break;
					case packet_port_source:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						break;
					case packet_protocol:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						break;
					case packet_source:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
							z++;
						break;
					case packet_url:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						break;
					case recv:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
							}
							z++;
						break;
					case return_type:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								
								bw.write(")");
							}
							else {
								
							}
							z++;
						break;
					case send:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
							}
							z++;
						break;
					case store_in_table:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
								bw.write(")");
							}
							else {
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
							}
							z++;
						break;
					default:
						break;
					
					}
					j++;
				}
				if(rfi.getBeforeImplication().size()>1) {
					bw.write("),\r\n");
				}
				else {
					bw.write(",\r\n");
				}
				bw.write("ctx.mkExists(new Expr[]{t_1");
				boolean t_0_presence = false;
				for(Condition c:rfi.getAfterImplication()) {
					if(c.getType()==ConditionType.send) {
						if(((SendCondition)c).getTime().equalsIgnoreCase("t_0")) {
							t_0_presence = true;
						}
					}
					if(c.getType()==ConditionType.recv) {
						if(((RecvCondition)c).getTime().equalsIgnoreCase("t_0")) {
							t_0_presence = true;
						}
					}
				}
				if(t_0_presence==true) {
					bw.write(",t_0"); //write t_0 only if it is necessary
				}
				
				HashMap<String, String> BHM = new HashMap<String,String>();
				HashMap<String, String> AHM = new HashMap<String,String>();
				BHM = beforeHM;
				AHM = afterHM;
				beforeHM = new HashMap<String,String>();
				afterHM = new HashMap<String,String>();
				for(Condition c : rfi.getAfterImplication()) { //detect all the constants of current implication after block
					switch(c.getType()) {
					case internal_node:
						if(!((InternalNodeCondition)c).getNode().equalsIgnoreCase(nfd.getName()) && nfd.hasPrivateAddresses()==true) {
							beforeHM.put(((InternalNodeCondition)c).getNode(), "nctx.node");
						}
						break;
					case is_in_table:
						break;
					case node_has_addr:
						break;
					case packet_body:
						break;
					case packet_destination:
						break;
					case packet_email_from:
						break;
					case packet_email_to:
						break;
					case packet_origin:
						break;
					case packet_port_destination:
						break;
					case packet_port_source:
						break;
					case packet_protocol:
						break;
					case packet_source:
						break;
					case packet_url:
						break;
					case recv:
						if(!((RecvCondition)c).getSourceNode().equalsIgnoreCase(nfd.getName())) {
							afterHM.put(((RecvCondition)c).getSourceNode(), "nctx.node");
						}
						if(!((RecvCondition)c).getDestNode().equalsIgnoreCase(nfd.getName())) {
							afterHM.put(((RecvCondition)c).getDestNode(), "nctx.node");
						}
						afterHM.put(((RecvCondition)c).getPacket(), "nctx.packet");
						break;
					case return_type:
						break;
					case send:
						if(!((SendCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
							afterHM.put(((SendCondition)c).getSource(), "nctx.node");
						}
						if(!((SendCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
							afterHM.put(((SendCondition)c).getDestination(), "nctx.node");
						}
						afterHM.put(((SendCondition)c).getPacket(), "nctx.packet");
						break;
					case store_in_table:
						int f_i = 0;
						for(String f : ((StoreTableCondition)c).field) {
							afterHM.put("f_"+f_i, "int");
							storeC.put("f_"+f_i, f);
							f_i++;
						}
						break;
					default:
						break;
					
					}
				}
				
				for(String s : afterHM.keySet()) {
					boolean trov = false;
					for(String s1 : BHM.keySet()) {
						if(s1.equalsIgnoreCase(s)) {
							trov = true;
						}
					}
					if(!trov) { //don't rewrite the variable already present in before block
						bw.write(","+s);
					}
				}
				bw.write("},ctx.mkAnd(\r\n ctx.mkLt(t_1, t_2),\r\n");
				if(t_0_presence==true) {
					bw.write("ctx.mkLt(t_0, t_1),\r\n");
				}
				for( String s : storeC.keySet()) { //ctx.mkEq(nctx.pf.get("url").apply(p2), f_0),
					String[] parts = storeC.get(s).split("[.]");
					switch(parts[1]) {
					case "getSourceAddress":
						bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getDestinationAddress":
						bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getSourcePort":
						bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getDestinationPort":
						bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getProtocol":
						bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getUrl":
						bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getMailSource":
						bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getMailDestination":
						//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
						break;
					case "getBody":
						bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getOrigBody":
						bw.write("ctx.mkEq(nctx.pf.get(\"orig_body\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					case "getOrigin":
						bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+parts[0]+"), "+s+"),\r\n");
						break;
					}
				}
				j = 0;
				z = 0;
				
				for(Condition c : rfi.getAfterImplication()) { //insert all the condition of after block
					switch(c.getType()) {
					case internal_node:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(nfd.hasPrivateAddresses()==false) {
								if(c.getValidity()==false) {
									bw.write("ctx.mkNot(");
									String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
									}
									else {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
									}
									bw.write(")");
								}
								else {
									String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
									}
									else {
										bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
									}
								}
							}
							if(nfd.hasPrivateAddresses()==true) {
								if(c.getValidity()==false) {
									bw.write("ctx.mkNot(");
									bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
									bw.write(")");
								}
								else {
									bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
								}
							}
							z++;
						}
						break;
					case is_in_table:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								int x = 0;
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write("))");
							}
							else {
								int x = 0;
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write(")");
							}
							z++;
						}
						break;
					case node_has_addr:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
							}
							z++;
						}
						break;
					case packet_body:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
							}
							z++;
						}
						break;
					case packet_destination:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
							z++;
						}
						break;
					case packet_email_from:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						}
						break;
					case packet_origin:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						}
						break;
					case packet_port_destination:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						}
						break;
					case packet_port_source:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						}
						break;
					case packet_protocol:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						}
						break;
					case packet_source:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
							z++;
						}
						break;
					case packet_url:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
							}
							z++;
						}
						break;
					case recv:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
							}
							z++;
						}
						break;
					case return_type:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								
								bw.write(")");
							}
							else {
								
							}
							z++;
						break;
					case send:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
							}
							z++;
						}
						break;
					case store_in_table:
						if(rfi.getOrCondition()==-1 || ( j!=rfi.getOrCondition() && j!=rfi.getBeforeImplication().size()-1)) {
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
								bw.write(")");
							}
							else {
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
							}
							z++;
						}
						break;
					default:
						break;
					
					}
					j++;
				}
				if(rfi.getOrCondition()!=-1) { //if there are two condition in OR they are written at the end
					bw.write(",\r\n ctx.mkOr(");
					Condition c = rfi.getAfterImplication().get(rfi.getAfterImplication().size()-1);
					switch(c.getType()) {
					case internal_node:
						if(nfd.hasPrivateAddresses()==false) {
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
								}
								else {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
								}
								else {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
								}
							}
						}
						if(nfd.hasPrivateAddresses()==true) {
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
								bw.write(")");
							}
							else {
								bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
							}
						}
						break;
					case is_in_table:
							if(c.getValidity()==false) {
								int x = 0;
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write("))");
							}
							else {
								int x = 0;
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write(")");
							}
						break;
					case node_has_addr:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
							}
						break;
					case packet_body:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
							}
						break;
					case packet_destination:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
						break;
					case packet_email_from:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_origin:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_port_destination:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_port_source:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_protocol:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_source:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
						break;
					case packet_url:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case recv:
							if(c.getValidity()==false) {
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
							}
						break;
					case return_type:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								
								bw.write(")");
							}
							else {
								
							}
							z++;
						break;
					case send:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
							}
						break;
					case store_in_table:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
								bw.write(")");
							}
							else {
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
							}
						break;
					default:
						break;
					
					}
					bw.write(",");
					c = rfi.getAfterImplication().get(rfi.getOrCondition());
					switch(c.getType()) {
					case internal_node:
						if(nfd.hasPrivateAddresses()==false) {
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
								}
								else {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((InternalNodeCondition)c).getNode().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"dest\").apply("+parts[0]+")))");
								}
								else {
									bw.write("((BoolExpr)private_addr_func.apply(nctx.pf.get(\"src\").apply("+parts[0]+")))");
								}
							}
						}
						if(nfd.hasPrivateAddresses()==true) {
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
								bw.write(")");
							}
							else {
								bw.write("((BoolExpr)private_addr_func.apply("+((InternalNodeCondition)c).getNode()+"))");
							}
						}
						break;
					case is_in_table:
							if(c.getValidity()==false) {
								int x = 0;
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write("))");
							}
							else {
								int x = 0;
								bw.write("(BoolExpr)"+((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : ((IsInTableCondition)c).getField()) {
									if(x!=0) {
										bw.write(",");
									}
									x++;
									String[] parts = s.split("[.]");
									switch(parts[1]) {
									case "getSourceAddress":
										bw.write("nctx.pf.get(\"src\").apply("+parts[0]+")");
										break;
									case "getDestinationAddress":
										bw.write("nctx.pf.get(\"dest\").apply("+parts[0]+")");
										break;
									case "getSourcePort":
										bw.write("(IntExpr)nctx.src_port.apply("+parts[0]+")");
										break;
									case "getDestinationPort":
										bw.write("(IntExpr)nctx.dest_port.apply("+parts[0]+")");
										break;
									case "getProtocol":
										bw.write("nctx.pf.get(\"proto\").apply("+parts[0]+")");
										break;
									case "getUrl":
										bw.write("nctx.pf.get(\"url\").apply("+parts[0]+")");
										break;
									case "getMailSource":
										bw.write("nctx.pf.get(\"emailFrom\").apply("+parts[0]+")");
										break;
									case "getMailDestination":
										//bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+parts[0]+"), "+s+"),\n\r");
										break;
									case "getBody":
										bw.write("nctx.pf.get(\"body\").apply("+parts[0]+")");
										break;
									case "getOrigBody":
										bw.write("nctx.pf.get(\"orig_body\").apply("+parts[0]+")");
										break;
									case "getOrigin":
										bw.write("nctx.pf.get(\"origin\").apply("+parts[0]+")");
										break;
									}
								}
								if(nfd.getHostTableStatic().get(((IsInTableCondition)c).getTableName().substring(1, ((IsInTableCondition)c).getTableName().length()-1))==false) {
									bw.write(",t_1");
								}
								bw.write(")");
							}
						break;
					case node_has_addr:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((NodeAddressCondition)c).getAddress().split("[.]");
								if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+parts[0]+"))");
								}
								else {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+parts[0]+"))");
								}
							}
						break;
					case packet_body:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
								bw.write(")");
							}
							else {
								String[] parts = ((BodyCondition)c).getBody().split("[.]");
								if(parts[1].equalsIgnoreCase("getBody")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"body\").apply("+parts[0]+"))");
								}
								else {
									bw.write("ctx.mkEq(nctx.pf.get(\"body\").apply("+((BodyCondition)c).getPacket()+"),nctx.pf.get(\"orig_body\").apply("+parts[0]+"))");
								}
							}
						break;
					case packet_destination:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((DestinationCondition)c).getDestination().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"dest\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((DestinationCondition)c).getDestination().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"dest\").apply("+((DestinationCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
						break;
					case packet_email_from:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((EmailFromCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getMailSource")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"emailFrom\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_origin:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								if(((OriginCondition)c).getOrigin().equalsIgnoreCase(nfd.getName())) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),node)");
									z++;
									break;
								}
								String[] parts = ((OriginCondition)c).getOrigin().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"), ((NetworkObject)"+parts[1].substring(1, parts[1].length()-1)+").getZ3Node())");
								}
								else {
									if(parts[1].equalsIgnoreCase("getOrigin")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"origin\").apply("+((OriginCondition)c).getPacket()+"),nctx.pf.get(\"origin\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"emailFrom\").apply("+((EmailFromCondition)c).getPacket()+"),nctx.pf.get(\"eMailTo\").apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_port_destination:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((DestPortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.dest_port.apply("+((DestPortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_port_source:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((SourcePortCondition)c).getPort().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationPort")) {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.dest_port.apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq((IntExpr)nctx.src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)nctx.src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_protocol:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((ProtocolCondition)c).getProtocol().split("[.]");
								if(parts[0].equalsIgnoreCase("Constants")) {
									String[] parts2 = parts[1].split("[_]");
									bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"), ctx.mkInt(nctx."+parts2[0]+"_"+parts2[1]+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getProtocol")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+((ProtocolCondition)c).getPacket()+"),nctx.pf.get(\"proto\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq((IntExpr)src_port.apply("+((SourcePortCondition)c).getPacket()+"),(IntExpr)src_port.apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case packet_source:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									bw.write(")");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
								bw.write(")");
							}
							else {
								if(((SourceCondition)c).getSource().equalsIgnoreCase(nfd.getName())) {
									bw.write("(BoolExpr)nctx.nodeHasAddr.apply(node,nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"))");
									z++;
									break;
								}
								String[] parts = ((SourceCondition)c).getSource().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"), ((DatatypeExpr)"+parts[1].substring(1, parts[1].length()-1)+"))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getDestinationAddress")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"dest\").apply("+parts[0]+"))");
									}
									else {
										bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
									}
								}
							}
						break;
					case packet_url:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
								bw.write(")");
							}
							else {
								String[] parts = ((UrlCondition)c).getUrl().split("[.]");
								if(parts[0].equalsIgnoreCase("hostPropertyList")) {
									bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"), ctx.mkInt(((Integer)"+parts[1].substring(1, parts[1].length()-1)+")))");
								}
								else {
									if(parts[1].equalsIgnoreCase("getUrl")) {
										bw.write("ctx.mkEq(nctx.pf.get(\"url\").apply("+((UrlCondition)c).getPacket()+"),nctx.pf.get(\"url\").apply("+parts[0]+"))");
									}
									else {
										//bw.write("ctx.mkEq(nctx.pf.get(\"src\").apply("+((SourceCondition)c).getPacket()+"),nctx.pf.get(\"src\").apply("+parts[0]+"))");
										;
									}
								}
							}
						break;
					case recv:
							if(c.getValidity()==false) {
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((RecvCondition)c).getSourceNode();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((RecvCondition)c).getDestNode();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.recv.apply("+source+", "+dest+", "+((RecvCondition)c).getPacket()+", "+((RecvCondition)c).getTime()+")");
							}
						break;
					case return_type:
							if(z!=0) {
								bw.write(",\r\n");
							}
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								
								bw.write(")");
							}
							else {
								
							}
							z++;
						break;
					case send:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
								bw.write(")");
							}
							else {
								String source = ((SendCondition)c).getSource();
								if(source.equalsIgnoreCase(nfd.getName())) {
									source = "node";
								}
								String dest = ((SendCondition)c).getDestination();
								if(dest.equalsIgnoreCase(nfd.getName())) {
									dest = "node";
								}
								bw.write("(BoolExpr)nctx.send.apply("+source+", "+dest+", "+((SendCondition)c).getPacket()+", "+((SendCondition)c).getTime()+")");
							}
						break;
					case store_in_table:
							if(c.getValidity()==false) {
								bw.write("ctx.mkNot(");
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
								bw.write(")");
							}
							else {
								bw.write("(BoolExpr)"+((StoreTableCondition)c).getTableName().substring(1, ((StoreTableCondition)c).getTableName().length()-1)+"_func.apply(");
								for(String s : storeC.keySet()) {
									bw.write(s+",");
								}
								bw.write(" t_2)");
							}
						break;
					default:
						break;
					
					}
					bw.write(")");
				}
				bw.write("),1,null,null,null,null)),1,null,null,null,null));\r\n");
				
			}
			if(nfd.getProtocolHM().size()>0) {
				int y = 0;
				String p = null;
				for(String s : nfd.getProtocolHM().keySet()) {
					if(y==0) {
						bw.write("constraints.add(ctx.mkForall(new Expr[]{n_0, "+nfd.getProtocolHM().get(s)+", t_2},ctx.mkImplies(\r\n");
						bw.write("(BoolExpr)nctx.send.apply(node, n_0, "+nfd.getProtocolHM().get(s)+", t_2),ctx.mkOr(\r\n");
						p=nfd.getProtocolHM().get(s);
					}
					if(y!=0) {
						bw.write(",");
					}
					y++;
					if(s.equalsIgnoreCase("Constants.HTTP_REQUEST_PROTOCOL")) {
						bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+p+"), ctx.mkInt(nctx.HTTP_REQUEST))\r\n");
					}
					if(s.equalsIgnoreCase("Constants.HTTP_RESPONSE_PROTOCOL")) {
						bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+p+"), ctx.mkInt(nctx.HTTP_RESPONSE))\r\n");
					}
					if(s.equalsIgnoreCase("Constants.POP3_REQUEST_PROTOCOL")) {
						bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+p+"), ctx.mkInt(nctx.POP3_REQUEST))\r\n");
					}
					if(s.equalsIgnoreCase("Constants.POP3_RESPONSE_PROTOCOL")) {
						bw.write("ctx.mkEq(nctx.pf.get(\"proto\").apply("+p+"), ctx.mkInt(nctx.POP3_RESPONSE))\r\n");
					}
				}
				bw.write(")),1,null,null,null,null));\r\n");
			}
			bw.write("}\r\n");
			bw.write("}\r\n");
			//---------------------------------------------------------------------------
		    bw.flush();
		    bw.close();
		  }
		  catch (IOException e) {
		    e.printStackTrace();
		  }
		
	}

}
