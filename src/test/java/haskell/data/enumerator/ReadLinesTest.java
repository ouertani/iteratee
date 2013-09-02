/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haskell.data.enumerator;

import haskell.data.enumerator.ex.ReadLines;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author hemantka
 */
public class ReadLinesTest {
    
    public ReadLinesTest() {
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
     public void singleBufferTest() throws Exception{
         String buff = "firstLine\rSecondLine\r\nThirdLine\nFourthLine\r\n";
         Iterator<CharBuffer> stream = Input.el(CharBuffer.wrap(buff));
         Iteratee<CharBuffer,List<String>> lines = new ReadLines();
         while(lines.toContinue()){
            lines = lines.apply(stream);
            if(stream.hasNext()&& !stream.next().hasRemaining())
                stream = Input.EOF ;
         }
         List<String> lineList = lines.yield();
         assertNotNull(lineList);
         assertEquals(4, lineList.size());
         assertEquals("firstLine",lineList.get(0));
         assertEquals("SecondLine",lineList.get(1));
         assertEquals("ThirdLine",lineList.get(2));
         assertEquals("FourthLine",lineList.get(3));
     }
     
     @Test
     public void twoBufferTest() throws Exception{
         String buff1 = "firstLine\rSecondLine\r\nT";
         String buff2 = "hirdLine\nFourthLine\r\n";
         final Iterator<CharBuffer> stream1 = Input.el(CharBuffer.wrap(buff1));
         final Iterator<CharBuffer> stream2 = Input.el(CharBuffer.wrap(buff2));
         boolean isFirst = true;
         Iterator<CharBuffer> stream = stream1;
         Iteratee<CharBuffer,List<String>> lines = new ReadLines();
         while(lines.toContinue()){
            lines = lines.apply(stream);
            if(!stream.hasNext())
                break;
            if(!stream.next().hasRemaining()){
                if(isFirst){
                    stream =  stream2;
                    isFirst = false;
                }else{
                    stream = Input.EOF;    
                }
            }
                
         }
         List<String> lineList = lines.yield();
         assertNotNull(lineList);
         assertEquals(4, lineList.size());
         assertEquals("firstLine",lineList.get(0));
         assertEquals("SecondLine",lineList.get(1));
         assertEquals("ThirdLine",lineList.get(2));
         assertEquals("FourthLine",lineList.get(3));
     }
}
