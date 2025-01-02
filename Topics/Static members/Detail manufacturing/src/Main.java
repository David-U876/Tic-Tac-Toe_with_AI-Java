import java.util.Scanner;

class ManufacturingController {
    // here you may declare a field
    private static int productCounter = 0;

    public static String requestProduct(String product) {
        productCounter++; // Increment the product counter
        return productCounter + ". Requested " + product; // Return the formatted string
    }

    public static int getNumberOfProducts() {
        return productCounter;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String product = scanner.nextLine();
            System.out.println(ManufacturingController.requestProduct(product));
            System.out.println(ManufacturingController.getNumberOfProducts());
        }
    }
}