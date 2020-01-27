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

 class Outer
 {
 //Regular Inner Class
    class RegularInner
    {
     public void showMethod()
     {
         System.out.println(" this is a regular Inner Class");
     }
    }
 }
public class RegularInnerClassExample {
    public static void main(String[] args) {
        Outer.RegularInner inner = new Outer().new RegularInner();
        inner.showMethod();
    }
    
}
