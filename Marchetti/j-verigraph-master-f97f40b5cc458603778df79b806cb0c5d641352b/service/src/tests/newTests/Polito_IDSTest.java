package tests.newTests;

import java.util.HashMap;

import com.microsoft.z3.Context;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Model;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

import mcnet.components.IsolationResult;
import tests.examples.PolitoIDSTest;

public class Polito_IDSTest {

	Context ctx;
	
	public void resetZ3() throws Z3Exception{
	    HashMap<String, String> cfg = new HashMap<String, String>();
	    cfg.put("model", "true");
	     ctx = new Context(cfg);
	}
	
	public void printModel (Model model) throws Z3Exception{
	    for (FuncDecl d : model.getFuncDecls()){
	    	System.out.println(d.getName() +" = "+ d.toString());
	    	  System.out.println("");
	    }
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
	
	public static void main(String[] args) throws Z3Exception
    {
    	Polito_IDSTest p = new Polito_IDSTest();
    	IsolationResult ret;
    	
    	p.resetZ3(); 	
    	PolitoIDSTest model = new PolitoIDSTest(p.ctx);
    	
    	System.out.println("Wait and be faithful, this program will terminate, sooner or later...");
    	
    	ret = model.check.checkIsolationProperty(model.server,model.hostA );
    	
    	//p.printVector(ret.assertions);
    	if (ret.result == Status.UNSATISFIABLE){
     	   System.out.println("UNSAT");
    	}else if(ret.result == Status.SATISFIABLE){
     		System.out.println("SAT ");
//     		System.out.print( "Model -> "); p.printModel(ret.model);
//    	    System.out.println( "Violating packet -> " +ret.violating_packet);
//    	    System.out.println("Last hop -> " +ret.last_hop);
//    	    System.out.println("Last send_time -> " +ret.last_send_time);
//    	    System.out.println( "Last recv_time -> " +ret.last_recv_time);
     	}else
     		System.out.println("UNKNOWN");
    }

}
