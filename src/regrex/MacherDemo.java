/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regrex;

import java.util.regex.Pattern;

/**
 *
 * @author NIIT PC
 */
public class MacherDemo {
    
    
    public static void main(String[] args) {
       String search 
            = "Niit2will3come2together";
    Pattern  pattern =
            Pattern.compile("\\d",Pattern.CASE_INSENSITIVE);
    
       String[] data = pattern.split(search);
//       for(int i=0; i<data.length;i++)
//            System.out.println(data[i]);
//    

for(String a :data)
            System.out.println(a);
    }
    
}

