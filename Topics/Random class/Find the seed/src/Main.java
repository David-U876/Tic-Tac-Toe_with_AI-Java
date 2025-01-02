import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Example: Replace this line with your desired input parsing.
        Scanner scanner = new Scanner(System.in);
        int A = scanner.nextInt(); // Lower bound of seed
        int B = scanner.nextInt(); // Upper bound of seed
        int N = scanner.nextInt(); // Number of random numbers to generate
        int K = scanner.nextInt(); // Upper bound (exclusive) of random numbers
        scanner.close();

        int minSeed = A; // Seed that produces the smallest maximum
        int minMax = Integer.MAX_VALUE; // Minimum maximum value across all seeds

        // Iterate through all seeds in the range [A, B]
        for (int seed = A; seed <= B; seed++) {
            Random random = new Random(seed); // Initialize Random with this seed
            int currentMax = Integer.MIN_VALUE; // Max of the current sequence

            // Generate N random numbers in the range [0, K) and track the maximum
            for (int i = 0; i < N; i++) {
                int num = random.nextInt(K);
                currentMax = Math.max(currentMax, num);
            }

            // Update the seed with the smallest maximum
            if (currentMax < minMax || (currentMax == minMax && seed < minSeed)) {
                minSeed = seed;
                minMax = currentMax;
            }
        }

        // Output the result
        System.out.println(minSeed);
        System.out.println(minMax);
    }
}