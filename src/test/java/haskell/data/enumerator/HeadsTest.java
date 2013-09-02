package haskell.data.enumerator;


import haskell.data.enumerator.ex.HeadsStep;
import org.junit.*;
import static org.junit.Assert.*;


import java.nio.CharBuffer;

/**
 *
 * @author hemantka
 */
public class HeadsTest {
    public HeadsTest() {}

    @BeforeClass
    public static void setUpClass() throws Exception {}

    @AfterClass
    public static void tearDownClass() throws Exception {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void emptyNonMatchingTest() {
        char[]     begins = { 'H', 'E', 'L', 'L', 'O' };
        HeadsStep  step   = new HeadsStep(begins);
        String     mylist = "helloWorld";
        CharBuffer cb     = CharBuffer.allocate(mylist.length());

        assertEquals(Step.IterateeState.CONTINUE, step.getState());
        assert(cb.hasRemaining());
        step.readBuffer(cb);
        assertEquals(Step.IterateeState.DONE, step.getState());
        assertEquals(new Integer(0),step.getResult());
    }
    
    @Test
    public void nonEmptyNonMatchingTest() {
        char[]     begins = { 'H', 'E', 'L', 'L', 'O' };
        HeadsStep  step   = new HeadsStep(begins);
        String     mylist = "helloWorld";
        CharBuffer cb     = CharBuffer.wrap(mylist);

        assertEquals(Step.IterateeState.CONTINUE, step.getState());
        assert(cb.hasRemaining());
        step.readBuffer(cb);
        assertEquals(Step.IterateeState.DONE, step.getState());
        assertEquals(new Integer(0),step.getResult());

        for (int i = 0; i < mylist.length(); i++) {
            char c = cb.get();

            assertEquals(c, mylist.charAt(i));
        }
        assert(!cb.hasRemaining());
    }
    
    @Test
    public void nonEmptyMatchingTest() {
        char[]     begins = { 'H', 'E', 'L', 'L', 'O' };
        HeadsStep  step   = new HeadsStep(begins);
        String     mylist = "helloWorld";
        CharBuffer cb     = CharBuffer.wrap(mylist.toUpperCase());

        assertEquals(Step.IterateeState.CONTINUE, step.getState());
        assert(cb.hasRemaining());
        step.readBuffer(cb);
        assertEquals(Step.IterateeState.DONE, step.getState());
        assertEquals(new Integer(5),step.getResult());

        for (int i = 5; i < mylist.length(); i++) {
            char c = cb.get();
            assertEquals(c, Character.toUpperCase(mylist.charAt(i)));
        }
        assert(!cb.hasRemaining());
    }
}
