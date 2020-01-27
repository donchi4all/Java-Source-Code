/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.donsoft;

import java.util.Scanner;

/**
 *
 * @author NIIT PC
 */
public class Divide {
    
    
    double getInput()
    {
        Scanner sc = new Scanner(System.in);
        printMe("Enter Number : >> ");
        return sc.nextDouble();
    }
    
    public static int getNum()
    {
        Scanner sc = new Scanner(System.in);
        printMe("Enter Number : >> ");
        return sc.nextInt();
    }
    
    double divideNum(double a,double b,double c)
    {
        double add = a + b + c;
        double result = add/2;
        return result;
    }
    
    
    public int divideNum(int b,int c)
    {
        int add = b + c;
        int result = add/2;
        return result;
    }
    
   public static void printMe(String input)
    {
        System.out.println(input);
    }
     public static void main(String[] args)
     {
        Divide d = new Divide();
        
        double num = d.getInput();
        double num1= d.getInput();
        double num2=d.getInput();
        
        double rs = d.divideNum(num, num1, num2);
         Divide.printMe(" tHE RUSLT IS  : " + rs);
    }
    
    
                        
}
