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
public class GenericClassExample {
    
    public static void main(String[] args) {
        TestGeneric<Integer,String> genericInt = 
                new TestGeneric<Integer,String>(120," Obi");
        
        System.out.println("The name is " +genericInt.getObj1()
                + " And he is "+ genericInt.getObj() +" years old");
        
      genericInt.print();
        
//         TestGeneric<String> genericstring = new TestGeneric<String>("Chisom");
//        System.out.println("My name is " + genericstring.getObj());
//       
    }
}

class TestGeneric<T,B>
{
T obj;
B obj1;

    public TestGeneric(T obj, B obj1) {
        this.obj = obj;
          this.obj1 = obj1;
    }

    
    public T getObj()
    {
    return this.obj;
    }
    
     public B getObj1()
    {
    return this.obj1;
    }
     
     public void print()
     {
         System.out.println(obj);
         System.out.println(obj1);
     }

}
