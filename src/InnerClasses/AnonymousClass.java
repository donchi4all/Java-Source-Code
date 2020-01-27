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
public class AnonymousClass {

    //An Anonymous class with a base class r a super Class A
    static A a = new A() {
    @Override
    void show() 
    {
    super.show();
        System.out.println("I am Anonymous class");
    }
    };
    
    
    public static void main(String[] args) {
        a.show();
    }
            
}

class A {

    void show() {
        System.out.println("method of a super class");
    }
}
