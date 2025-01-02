// Java code template starts here
import java.util.*;

public class Main {

    // Define Animal class here
    static class Animal {
        String name;
        public void makeSound() {
            System.out.println("Generic sound");
        }
    }
    // Define Dog class here
    static class Dog extends Animal {
        public void makeSound() {
            System.out.println("Bark!");
        }
    }
    // Define Cat class here
    static class Cat extends Animal {
        public void makeSound() {
            System.out.println("Meow!");
        }
    }
    public static void main(String[] args) {

        // In this section, you need to add code to receive input
        // and create the corresponding animal object
        Scanner scanner = new Scanner(System.in);
        String animalType = scanner.nextLine();
        String animalName = scanner.nextLine();

        
        // Replace the placeholders with the appropriate calls
        if ("Dog".equalsIgnoreCase(animalType)) {
            Dog dog = new Dog();
            dog.name = animalName;
            dog.makeSound();
        } else if ("Cat".equalsIgnoreCase(animalType)) {
            Cat cat = new Cat();
            cat.name = animalName;
            cat.makeSound();
        } else {
            System.out.println("Animal type not recognized");
        }
    }
} 

// Java code template ends here