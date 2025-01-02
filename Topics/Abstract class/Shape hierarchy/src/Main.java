abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Rectangle extends Shape {
    double width;
    double height;

    double getPerimeter() {
        return this.width * 2 + this.height * 2;
    }
    double getArea() {
        return this.width * this.height;
    }

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
}

class Circle extends Shape {
    double radius;
    Circle(double radius) {
        this.radius = radius;
    }
    double getPerimeter() {
        return 2 * Math.PI * this.radius;
    }
    double getArea() {
        return Math.PI * this.radius * this.radius;
    }
}

class Triangle extends Shape {
    double side1;
    double side2;
    double side3;
    Triangle(double side1, double side2, double side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    double getPerimeter() {
        return this.side1 + this.side2 + this.side3;
    }
    double getArea() {
        double s = (this.side1 + this.side2 + this.side3)/2;
        return Math.sqrt(s*(s-this.side1)*(s-this.side2)*(s-this.side3));
    }
}