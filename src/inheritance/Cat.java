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
public class Cat implements AnimalInterface{

    @Override
    public void eat() {
        System.out.println("Cat is eating");
            }

    @Override
    public void sound() {
        System.out.println("Cat is making noice");       
    }
    
    public static void main(String[] args) {
        Cat a = new Cat();
        a.eat();
        a.sound();
    }
}
