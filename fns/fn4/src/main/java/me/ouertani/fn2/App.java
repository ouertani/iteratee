package me.ouertani.fn2;

import static java.lang.StrictMath.E;
import java.util.function.Function;
import static me.ouertani.fn2.Input.InputState.EL;
import me.ouertani.fn2.generator.Enumerator;
import me.ouertani.fn2.generator.SumEnumerator;


public class App {

    public static void main(String[] args) {
  
        Input[] in =  {Input.el(5),Input.el(2) };
  
        Enumerator lit = Enumerator.enumInput(in);
        Iteratee<Integer,Integer> summer = new SumIteratee(); 
        Input run = lit.run((Step<Integer,?> )summer);
        
        System.out.println("run" + run);
        switch(run.state()){
            case EL : Input.El<Integer> el = (Input.El) run;
                System.out.println(""+el.getE());
                break;
            
            default:
                System.out.println("---------------"+run.state());
                
        }
        
//        Step apply = lit.apply((Step<Integer,?> )summer);
//        for (int i = 0; i < 5; i++) {
//           
//              System.out.println("-----------");
//   
//        System.out.println("s" + apply);
//        System.out.println(""+apply.onState());
//  
//        System.out.println("-----------");
//        apply = lit.apply((Step<Integer,?> )apply);
//
//        }
//      
//          System.out.println("-----------!!!!!!!!!!!!!!!!!");
//   
//        System.out.println("s" + apply);
//        System.out.println(""+apply.onState());
//       Step.Done d = (Step.Done)apply;
//        System.out.println("-----------"+d.getA());
       
       

      
            // consume = consume.handle(Input.el(""+i));
//           
//            for (int i = 0; i < 4; i++) {
//              SumEnumerator     sumGenerator = new SumEnumerator(summer,Input.el(i));
//            summer.<Integer>handle(sumGenerator);
//                 System.out.println("--" + handle);
//            
//        }
          
        
      
    }

  
}
