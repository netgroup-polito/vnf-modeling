package tests.newTests;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.microsoft.z3.Context;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Model;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

import mcnet.components.IsolationResult;
import tests.examples.PolitoCacheNatFwTest;

/**
 * @author Giacomo Costantini
 */
public class Polito_CacheNatFwTest{

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
    	Polito_CacheNatFwTest p = new  Polito_CacheNatFwTest();
    	
    	int k=0;
    	long t=0;
    	
    	for(;k<1;k++){
			p.resetZ3();
			PolitoCacheNatFwTest model = new PolitoCacheNatFwTest(p.ctx);
		
			Calendar cal = Calendar.getInstance();
		    Date start_time = cal.getTime();
			     
			IsolationResult ret =model.check.checkIsolationProperty(model.hostA,model.hostB );
		
			Calendar cal2 = Calendar.getInstance();
			t = t+(cal2.getTime().getTime() - start_time.getTime()); 
		
//			p.printVector(ret.assertions);
			if (ret.result == Status.UNSATISFIABLE){
		 	   System.out.println("UNSAT"); // Nodes a and b are isolated
			}else{
		 		System.out.println("SAT ");
//	     		System.out.print( "Model -> "); p.printModel(ret.model);
//	    	    System.out.println( "Violating packet -> " +ret.violating_packet);
//	    	    System.out.println("Last hop -> " +ret.last_hop);
//	    	    System.out.println("Last send_time -> " +ret.last_send_time);
//	    	    System.out.println( "Last recv_time -> " +ret.last_recv_time);
			}
			//System.out.print( "Model -> "); p.printModel(ret.model);
			 System.out.println( "Violating packet -> " +ret.violating_packet);
    	}
    	System.out.println("Mean execution time hostA-> hostB: "+(float)(t/1000)/k);
    	
    	
    	k=0;t=0;
    	for(;k<10;k++){
			p.resetZ3();
			PolitoCacheNatFwTest model = new  PolitoCacheNatFwTest(p.ctx);
		
			Calendar cal = Calendar.getInstance();
		    Date start_time = cal.getTime();
		 
			IsolationResult ret =model.check.checkIsolationProperty(model.hostB,model.hostA );
		    //IsolationResult ret =model.check.checkIsolationProperty(model.hostB,model.hostC );
		
			Calendar cal2 = Calendar.getInstance();
			t = t+(cal2.getTime().getTime() - start_time.getTime()); 
			
			if (ret.result == Status.UNSATISFIABLE){
		 	   System.out.println("UNSAT"); // Nodes a and b are isolated
			}else{
		 		System.out.println("SAT ");
//	     		System.out.print( "Model -> "); p.printModel(ret.model);
//	    	    System.out.println( "Violating packet -> " +ret.violating_packet);
//	    	    System.out.println("Last hop -> " +ret.last_hop);
//	    	    System.out.println("Last send_time -> " +ret.last_send_time);
//	    	    System.out.println( "Last recv_time -> " +ret.last_recv_time);
			}
    	
    	}
    	System.out.println("Mean execution time hostB-> hostA: "+(float)(t/1000)/k);
    }

    
}
