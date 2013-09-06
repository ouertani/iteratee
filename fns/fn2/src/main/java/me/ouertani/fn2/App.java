package me.ouertani.fn2;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
         Iteratee<String> consume = new ConsumeString();
         Iteratee<Integer> summer = new SumIteratee();
       
        for (int i = 0; i < 4; i++) {
            consume = consume.handle(Input.el(""+i));
            summer= summer.handle(Input.el(i));
        }
        System.out.println("-----------");
        System.out.println(""+consume);
        System.out.println("s"+ summer);
        System.out.println("-----------");
        
                
    }
}
