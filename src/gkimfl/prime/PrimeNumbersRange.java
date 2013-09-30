package gkimfl.prime;

import static gkimfl.prime.Math.getSquareRoot;
import java.math.BigInteger;
import java.util.ArrayList;

public class PrimeNumbersRange {

    /**
     * Generate a list of numbers with potential prime factors.
     * 
     * The list consists of products of prime numbers from two to the square
     * root of val. The numbers are ordered by likelihood; the lowest primes are
     * in the first product, and the highest primes are in the last product.
     */
    public static BigInteger[] getFactoringValues(BigInteger val) {
        BigInteger sqrtVal = getSquareRoot(val);
        ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
        BigInteger current = BigInteger.ONE;
        val = val.pow(50);
        for (BigInteger prime : new SieveOfEratosthenes()) {
            if (prime.compareTo(sqrtVal) > 0) {
                break;
            }
            if (current.compareTo(val) < 0) {
                current = current.multiply(prime);
            }
            else {
                factors.add(current);
                current = prime;
            }
        }
        factors.add(current);
        BigInteger[] values = factors.toArray(new BigInteger[factors.size()]);
        return values;
    }

    /**
     * Test is val shares a common factor with factors.
     */
    public static boolean isPrime(BigInteger val, BigInteger factors) {
        if (val.equals(BigInteger.ONE)) {
            return false;
        }
        return factors.gcd(val).equals(BigInteger.ONE);
    }

    /**
     * Test if val shares a common factor with any factors in factoring.
     */
    public static boolean isPrime(BigInteger val, BigInteger[] factoring) {
        for (BigInteger factors : factoring) {
            if (!isPrime(val, factors)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test if a single number is prime.
     * 
     * This is inefficient! It is much better to reuse the factoring values over
     * a large pool of candidate primes.
     */
    public static boolean isPrime(BigInteger val) {
        return isPrime(val, getFactoringValues(val));
    }

    /**
     * Ahh, yes! Run the algorithm and print some stats.
     */
    public static void main(String[] argv) {
        // look for primes around this value
        BigInteger around = new BigInteger("1000000000");
        BigInteger howmany = new BigInteger("10000");

        // print prime numbers that are found
        boolean show = false;

        // factoring will be valid in this range
        // below start, may miss primes that are factors of the composite number
        // above end, may find composites with larger prime factors
        BigInteger start = getSquareRoot(around);
        BigInteger end = start.add(BigInteger.ONE);
        start = start.multiply(start);
        end = end.multiply(end);
        if (howmany != null && end.subtract(start).compareTo(howmany) > 0) {
            if (end.subtract(howmany).compareTo(around) > 0) {
                start = around;
            }
            end = start.add(howmany);
        }

        System.out.format("Starting value %s%n", start);
        System.out.format("Ending value %s%n", end);

        long time = 0;
        int count = 0;
        int delta = end.subtract(start).intValue();

        time = -System.nanoTime();
        BigInteger[] factoring = getFactoringValues(end.subtract(BigInteger.ONE));
        time += System.nanoTime();
        System.out.format("Preparation in %.2f sec (%d composite values)\n",
                time / 1000000000f, factoring.length);

        time = -System.nanoTime();
        for (; !start.equals(end); start = start.add(BigInteger.ONE)) {
            if (isPrime(start, factoring)) {
                if (show) {
                    time += System.nanoTime();
                    System.out.println(start);
                    time -= System.nanoTime();
                }
                ++count;
            }
        }
        time += System.nanoTime();
        System.out.format("Tested %d numbers in %.2f sec (%.2f per sec)\n",
                delta, time / 1000000000f, 1000000000f * delta / time);
        System.out.format("Found %d prime numbers\n",
                count);
    }
}
