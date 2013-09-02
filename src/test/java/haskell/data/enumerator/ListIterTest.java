package haskell.data.enumerator;

import java.util.*;

/**
 *
 * @author hemantka
 */
public class ListIterTest {
    
    

    public static void main(String []args){
        List<Integer> list = new ArrayList<Integer>();
        
        for(int i=1;i<100;i++){
            list.add(i);
        }
        
        Random r = new Random(System.currentTimeMillis());
        int toRead = 10 + r.nextInt(90);
        int toUnread = 1 + r.nextInt(toRead);
        Stack<Integer> list2 = new Stack<Integer>();
        ListIterator<Integer> iter = list.listIterator();
        int i=0;
        while((i < toRead) && iter.hasNext()){
            list2.push(i);
            i++;
        }
        System.out.println("Read " + i + " elements  with "+ toRead + " and " + list2.size() );
        i=toUnread;
        while((i>0) && iter.hasPrevious()){
            iter.previous();
            list2.pop();
            i--;
        }
        System.out.println("UnRead " + toUnread + " elements  and " + list2.size() );
        
        while(iter.hasNext()){
            list2.push(iter.next());
        }
        
        for(Integer num : list2){
            System.out.print(num);
            System.out.print(" ");
        }
        System.out.println("");
    }
}
