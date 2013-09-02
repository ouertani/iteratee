package haskell.data.enumerator;

import haskell.data.enumerator.ex.PeekStep;
import java.nio.CharBuffer;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author hemantka
 */
public class PeekStepTest {
    
    public PeekStepTest() {
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

    @Test
     public void nonEmptyBufferTest() {
         PeekStep hs = new PeekStep();
         String mylist = "helloWorld";
         CharBuffer cb = CharBuffer.wrap(mylist);
         assertEquals(Step.IterateeState.CONTINUE,hs.getState());
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.DONE, hs.getState());
         assertEquals(new Character(mylist.charAt(0)),hs.getResult());
         for(int i=0;i<mylist.length();i++){
             char c = cb.get();
             assertEquals(c, mylist.charAt(i));
         }
         assert(!cb.hasRemaining());
     }
     
     @Test
     public void emptyBufferTest(){
         PeekStep hs = new PeekStep();
         String mylist = "helloWorld";
         CharBuffer cb = CharBuffer.allocate(mylist.length());
         assertEquals(Step.IterateeState.CONTINUE,hs.getState());
         cb.flip();
         assert(!cb.hasRemaining());
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.CONTINUE, hs.getState());
     }
}
