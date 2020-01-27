/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InnerClasses;



/**
 *
 * @author NIIT PC
 */

//static inner 
public class StaticInnerClass {
   
  
     public static void showStaticMethod()
     {
         System.out.println("Inside outer class");
     }
    



//static inner 
static class InnerClass {
    public static void main(String[] args) {
        System.out.println("Inside inner Class method");
       showStaticMethod();
    }
}

 }