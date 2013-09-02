/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haskell.data.enumerator;

import haskell.data.enumerator.ex.ReadLines;
import haskell.data.enumerator.ex.StringIterEnumerator;
import java.nio.CharBuffer;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author hemantka
 */
public class StringEnumerTest {
    
    public StringEnumerTest() {
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
    public void testChunkSize3() {
        String buff = "firstLine\rSecondLine\r\nThirdLine\nFourthLine\r\n";
        
            StringIterEnumerator<List<String>> enumerator = new StringIterEnumerator(buff);

            Iteratee<CharBuffer, List<String>> lines = new ReadLines();     
            Iteratee<CharBuffer, List<String>> it = enumerator.apply(lines);
         

            List<String> lineList = it.yield();
            assertNotNull(lineList);
            assertEquals(4, lineList.size());
            assertEquals("firstLine", lineList.get(0));
            assertEquals("SecondLine", lineList.get(1));
            assertEquals("ThirdLine", lineList.get(2));
            assertEquals("FourthLine", lineList.get(3));
    }
}
