package me.ouertani.fn2;

import me.ouertani.fn2.utils.SumIteratee;
import static me.ouertani.fn2.Input.InputState.EL;
import me.ouertani.fn2.utils.MaxIteratee;


public class App {

    public static void main(String[] args) {
  
        Input[] in1 =  {Input.el(5),Input.el(2) };
        Input[] in2 =  {Input.el(5),Input.el(2), Input.el(3) };
  
        Enumerator lit1 = Enumerator.enumInput(in1);
        Enumerator lit2 = Enumerator.enumInput(in2);
        
      //  Iteratee<Integer,Integer> summer = new SumIteratee(); 
        Iteratee<Integer,Integer> maxer = new MaxIteratee(); 
//        Input run = lit1.run((Iteratee<Integer,?> )summer);
//        Input run2 = lit2.run((Iteratee<Integer,?> )summer);
        
         Input run3 = lit1.run((Iteratee<Integer,?> )maxer);
         Input run4 = lit2.run((Iteratee<Integer,?> )maxer); 
//        
//        System.out.println("run" + run);
//        switch(run.state()){
//            case EL : Input.El<Integer> el = (Input.El) run;
//                System.out.println(""+el.getE());
//                break;
//            
//            default:
//                System.out.println("---------------"+run.state());
//                
//        }   
//        
//        switch(run2.state()){
//            case EL : Input.El<Integer> el = (Input.El) run2;
//                System.out.println(""+el.getE());
//                break;
//            
//            default:
//                System.out.println("---------------"+run2.state());
//                
//        }
         switch(run3.state()){
            case EL : Input.El<Integer> el = (Input.El) run3;
                System.out.println("::::"+el.getE());
                break;
            
            default:
                System.out.println("---------------"+run3.state());
                
        }
          switch(run4.state()){
            case EL : Input.El<Integer> el = (Input.El) run4;
                System.out.println(""+el.getE());
                break;
            
            default:
                System.out.println("---------------"+run4.state());
                
        }
    }

  
}
