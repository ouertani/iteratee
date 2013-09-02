package haskell.data.enumerator;

import haskell.data.enumerator.ex.BreakStep;
import java.nio.CharBuffer;


import java.util.function.Predicate;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author hemantka
 */
public class BreakStepTest {
    
    public BreakStepTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    class MatchCapital implements Predicate<Character>{
       
        @Override
        public boolean test(Character t) {
           return Character.isUpperCase(t.charValue());
        }
        
    }
     @Test
     public void breakTestNonEmptyWithMatchingBuffer() {
         BreakStep hs = new BreakStep(new MatchCapital());
         String mylist = "helloWorld";
         CharBuffer cb = CharBuffer.wrap(mylist);
         assertEquals(Step.IterateeState.CONTINUE,hs.getState());
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.DONE, hs.getState());
         assertEquals("hello",hs.getResult());
         for(int i=5;i<mylist.length();i++){
             char c = cb.get();
             assertEquals(c, mylist.charAt(i));
         }
         assert(!cb.hasRemaining());
     }
     
     @Test
     public void breakTestNonEmptyWithMatchInSecBuffer() {
         BreakStep hs = new BreakStep(new MatchCapital());
         String mylist = "helloWorld";
         CharBuffer cb = CharBuffer.wrap(mylist.toLowerCase());
         assertEquals(Step.IterateeState.CONTINUE,hs.getState());
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.CONTINUE, hs.getState());
         assert(!cb.hasRemaining());
         cb = CharBuffer.wrap(mylist);
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.DONE, hs.getState());
         assertEquals(mylist.toLowerCase()+"hello",hs.getResult());

         for(int i=5;i<mylist.length();i++){
             char c = cb.get();
             assertEquals(c, mylist.charAt(i));
         }
         assert(!cb.hasRemaining());
     }
     
     @Test
     public void breakTestWithEmptyBuffer(){
         BreakStep hs = new BreakStep(new MatchCapital());
         String mylist = "helloWorld";
         CharBuffer cb = CharBuffer.allocate(mylist.length());
         assertEquals(Step.IterateeState.CONTINUE,hs.getState());
         cb.flip();//empty the buffer.
         assert(!cb.hasRemaining());
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.CONTINUE, hs.getState());
     }
}
