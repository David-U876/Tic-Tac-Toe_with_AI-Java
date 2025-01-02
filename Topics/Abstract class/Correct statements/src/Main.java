// You can experiment here, it won’t be checked


interface Animal {
  String makeSound();
}
class Dog implements Animal {
  @Override
  public String makeSound() {
    return "Woof!";
  }
}
public class Main {
  public static void main(String[] args) {
    Animal dog = new Dog();
    System.out.println(dog.makeSound());
  }
}