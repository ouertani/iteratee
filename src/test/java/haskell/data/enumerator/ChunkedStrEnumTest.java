/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haskell.data.enumerator;

import haskell.data.enumerator.ex.ChunkedStringEnumerator;
import haskell.data.enumerator.ex.ReadLines;
import java.nio.CharBuffer;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hemantka
 */
public class ChunkedStrEnumTest {

    public ChunkedStrEnumTest() {
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
    public void indicesTest() {
        String buff = "firstLine\rSecondLine\r\nThirdLine\nFourthLine\r\n";

        for (int i = 2; i < buff.length(); i++) {
            ChunkedStringEnumerator<List<String>> enumerator = new ChunkedStringEnumerator<>(buff, i);
            Iteratee<CharBuffer, List<String>> lines = new ReadLines();
            Iteratee<CharBuffer, List<String>> res = enumerator.apply(lines);

            assertFalse(res.toContinue());

            List<String> lineList = res.yield();
            assertNotNull(lineList);
            StringBuilder strb = new StringBuilder();
            lineList.stream().forEach((s) -> {
                strb.append(s);
            });

            assertEquals("firstLineSecondLineThirdLineFourthLine", strb.toString());
            System.out.println(" tested with " + i);
        }
    }
}
