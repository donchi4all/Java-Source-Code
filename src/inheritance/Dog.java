/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inheritance;

/**
 *
 * @author NIIT PC
 */
public class Dog extends Animal{

  
    void eat() {
        System.out.println("bone");
    }

   
    void sound() {
        System.out.println(" Barking");
       }
    
    public static void main(String[] args) 
    {
      Dog d = new Dog();
      d.eat();
      d.sound();
    }
    
}
