package view;

import controller.Controller;
import model.Apple;
import model.Book;
import model.Cake;
import model.Item;
import repository.ArrayRepository;
import repository.IRepository;

public class Main{
    public static void main(String[] args){
        IRepository repo = new ArrayRepository();
        Controller controller = new Controller(repo);
        controller.addItem(new Apple(250f,"ionatan"));
        //the upcast is performed automatically
        controller.addItem(new Book(12f,230));
        controller.addItem(new Cake(135f,"vanilla"));
        System.out.println("the size of the controller is "+controller.size());
    //the toString method is called by default when concatenating
        Item[] array = controller.filter(100);
        for (int i=0;i<array.length;i++){
            /*if (array[i] instanceof Apple)
                System.out.println(array[i]);
            if(array[i] instanceof Cake)
                System.out.println(array[i]);
            if(array[i] instanceof Book)
                System.out.println(array[i]);*/
            System.out.println(array[i]);

        }
        //toString is called from the Item class

    }
}
