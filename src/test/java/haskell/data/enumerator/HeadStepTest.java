/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haskell.data.enumerator;

import haskell.data.enumerator.ex.HeadStep;
import java.nio.CharBuffer;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author hemantka
 */
public class HeadStepTest {
    
    public HeadStepTest() {
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
         HeadStep hs = new HeadStep();
         String mylist = "helloWorld";
         CharBuffer cb = CharBuffer.wrap(mylist);
         assertEquals(Step.IterateeState.CONTINUE,hs.getState());
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.DONE, hs.getState());
         assertEquals(new Character(mylist.charAt(0)),hs.getResult());
         for(int i=1;i<mylist.length();i++){
             char c = cb.get();
             assertEquals(c, mylist.charAt(i));
         }
         assert(!cb.hasRemaining());
     }
     
     @Test
     public void emptyBufferTest(){
         HeadStep hs = new HeadStep();
         String mylist = "helloWorld";
         CharBuffer cb = CharBuffer.allocate(mylist.length());
         assertEquals(Step.IterateeState.CONTINUE,hs.getState());
         cb.flip();
         assert(!cb.hasRemaining());
         hs.readBuffer(cb);
         assertEquals(Step.IterateeState.CONTINUE, hs.getState());
     }
}
