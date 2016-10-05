package tests.newTests;

import java.util.HashMap;

import com.microsoft.z3.Context;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Model;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

import mcnet.components.DataIsolationResult;
import mcnet.components.IsolationResult;
import tests.examples.PolitoCacheFwTest;

/**
 * 
 * @author Giacomo Costantini
 *
 */
public class Polito_CacheFwTest{

	Context ctx;
	
	public void resetZ3() throws Z3Exception{
	    HashMap<String, String> cfg = new HashMap<String, String>();
	    cfg.put("model", "true");
	     ctx = new Context(cfg);
	}
	
	public void printVector (Object[] array){
	    int i=0;
	    System.out.println( "*** Printing vector ***");
	    for (Object a : array){
	        i+=1;
	        System.out.println( "#"+i);
	        System.out.println(a);
	        System.out.println(  "*** "+ i+ " elements printed! ***");
	    }
	}
	
	public void printModel (Model model) throws Z3Exception{
	    for (FuncDecl d : model.getFuncDecls()){
	    	System.out.println(d.getName() +" = "+ d.toString());
	    	  System.out.println("");
	    }
	}


    public static void main(String[] args) throws Z3Exception
    {
    	Polito_CacheFwTest p = new Polito_CacheFwTest();
    	p.resetZ3();
    	PolitoCacheFwTest model = new PolitoCacheFwTest(p.ctx);
    	IsolationResult ret;
    	DataIsolationResult ret2;
    	
    	ret =model.check.checkIsolationProperty(model.a,model.server);
//    	ret =model.check.checkIsolationProperty(model.fw,model.a);
//    	ret2 = model.check.CheckDataIsolationProperty(model.server, model.a);
//    	ret = model.check.CheckNodeTraversalProperty(model.fw, model.a, model.server);
//    	ret = model.check.CheckLinkTraversalProperty(model.a, model.server, model.politoCache, model.fw);
    	
    	//p.printVector(ret.assertions);
    	if (ret.result == Status.UNSATISFIABLE){
     	   System.out.println("UNSAT"); // Nodes a and b are isolated
    	}else{
     		System.out.println("SAT ");
//     		System.out.print( "Model -> "); p.printModel(ret.model);
//    	    System.out.println( "Violating packet -> " +ret.violating_packet);
//    	    System.out.println("Last hop -> " +ret.last_hop);
//    	    System.out.println("Last send_time -> " +ret.last_send_time);
//    	    System.out.println( "Last recv_time -> " +ret.last_recv_time);
    	}
    }

    
}
