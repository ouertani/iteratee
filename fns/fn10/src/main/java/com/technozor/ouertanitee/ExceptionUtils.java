package me.ouertani.fn2.utils;

/**
 *
 * @author ouertani
 */
public interface ExceptionUtils{
     static <T extends Exception>  void  handle(Exception ex) throws T {
       throw (T) ex;
   } 
}
