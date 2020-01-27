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
public class MethodLocalInnerClass {

    public static void main(String[] args) {
        Outer2 out = new Outer2();
        out.outermethod();

    }
}

class Outer2 {
    //Inner class  is local to outer class Class

    void outermethod() {

        class LocalInner {

            public void showMethod() {
                System.out.println(" Inside Inner method");
            }
        }

        LocalInner y = new LocalInner();
        y.showMethod();
    }
}
