/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import haskell.data.enumerator.IterEnumerator;
import haskell.data.enumerator.Iteratee;
import haskell.data.enumerator.ex.ChunkedStringEnumerator;
import haskell.data.enumerator.ex.ReadLines;
import java.nio.CharBuffer;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author slim
 */
public class Main {
    public static void main(String[] args) {
         String buff = "firstLine\rSecondLine\r\nThirdLine\nFourthLine\r\n";
         
         System.out.println("Hello");
         for(int i=2;i<buff.length();i++){
            IterEnumerator<CharBuffer, List<String>> enumerator = new ChunkedStringEnumerator<>(buff, i);
            Iteratee<CharBuffer, List<String>> lines = new ReadLines();
            Iteratee<CharBuffer, List<String>> res = enumerator.apply(lines);
            
            assertFalse(res.toContinue());
            
            List<String> lineList = res.yield();
            assertNotNull(lineList);
            StringBuilder strb = new StringBuilder();
            lineList.stream().forEach((s) -> {
                 strb.append(s);
             });
             System.out.println("::::::::::::>"+strb.toString());
            assertEquals("firstLineSecondLineThirdLineFourthLine", strb.toString());
            System.out.println(" tested with " + i);
         }
    }
  
}
