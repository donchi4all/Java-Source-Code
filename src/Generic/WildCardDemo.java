/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generic;

/**
 *
 * @author NIIT PC
 */
public class WildCardDemo<T> {

    private T t;

    public void setValue(T t) {
        this.t = t;
    }

    public T getValue() {
        return t;
    }
    public boolean compare(WildCardDemo<? extends Number> wcd)
    {
        if(t == wcd.t)
        {
            return true;
            
        }
        else
        {
            return false;
        }
        
    }

    public static void main(String[] args) 
    {
        WildCardDemo<Integer> obj1 = new WildCardDemo<Integer>();
        obj1.setValue(10);
        
        WildCardDemo<String> obj2 = new WildCardDemo<String>();
        obj2.setValue("Test");
        
        System.out.println("Value of first object: " +obj1.getValue());
        System.out.println("Value of second object: " +obj2.getValue());
        
        
        System.out.println("Are both equal? " + obj2.compare(obj1));  //Compilation Error
         
    }
}

