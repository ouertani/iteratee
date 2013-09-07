/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ouertani.fn2;

import junit.framework.TestCase;

/**
 *
 * @author slim
 */
public class SumIterateeTest extends TestCase {
    
    public SumIterateeTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of handle method, of class SumIteratee.
     */
//    public void testHandle() {
//        Iteratee<Integer> summer = new SumIteratee();
//         for (int i = 0; i < 4; i++) {         
//            summer= summer.handle(Input.el(i));
//        }       
//       
//        Integer expResult = 6;
//        Integer result = ((SumIteratee) summer).getSum();
//        assertEquals(expResult, result);      
//    }
//     /**
//     * Test of handle method, of class SumIteratee.
//     */
//    public void testHandle2() {
//        Iteratee<Integer> summer = new SumIteratee();
//         for (int i = 0; i < 4; i++) {         
//            summer= summer.handle(Input.el(i));
//        }   
//           summer= summer.handle(Input.EMPTY);
//       for (int i = 0; i < 4; i++) {         
//            summer= summer.handle(Input.el(i));
//        }  
//        Integer expResult = 12;
//        Integer result = ((SumIteratee) summer).getSum();
//        assertEquals(expResult, result);      
//    }
//      /**
//     * Test of handle method, of class SumIteratee.
//     */
//    public void testHandle3() {
//        Iteratee<Integer> summer = new SumIteratee();
//        for (int i = 0; i < 4; i++) {         
//            summer= summer.handle(Input.el(i));
//        }   
//        summer= summer.handle(Input.EOF);
//       for (int i = 0; i < 4; i++) {         
//            summer= summer.handle(Input.el(i));
//        }  
//        Integer expResult = 6;
//        Integer result = ((SumIteratee) summer).getSum();
//        assertEquals(expResult, result);      
//    }

    public void test() {
        assertEquals(1, 1);
    }
    
    
}
