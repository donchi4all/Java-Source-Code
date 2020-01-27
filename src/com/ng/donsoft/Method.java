/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.donsoft;

/**
 *
 * @author NIIT PC
 */
public class Method {
    
    private static int num =0, num2=0;
    
     public Method(int num , int num2)
     {
         this.num =num;
         this.num2=num2;
     }
     
     int add()
     {
         return  num + num2;
     }
     
     public static void main(String[] args)
     {
         int n = Divide.getNum();
         int n2 =Divide.getNum();
        Method m = new Method(n, n2);
        
        int result =  m.add();
        
         System.out.println(num);
        Divide.printMe("The result is : "+result);
     
    }
}
