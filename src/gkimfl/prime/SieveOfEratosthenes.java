package gkimfl.prime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.math.BigInteger;

public class SieveOfEratosthenes implements Iterator<BigInteger>, Iterable<BigInteger> {
    /**
     * This is how many numbers to sieve in each batch.
     */
    public static final int DEFAULT_SEIVE_LEN = 1 << 20;

    static class PrimeOfEratosthenes {
        BigInteger remain;
        BigInteger prime;
    }

    ArrayList<PrimeOfEratosthenes> primes;

    BigInteger sieveBase;
    BigInteger sieveBound;
    BigInteger sieveLength;

    boolean sieve[];
    int sievePos;

    /**
     * Prime the sieve (pun intended).
     * 
     * Sieve starts at zero, with zero and one marked not prime.
     */
    public SieveOfEratosthenes(int seiveLen) {
        if (seiveLen < 2) {
            throw new IllegalArgumentException();
        }

        primes = new ArrayList<SieveOfEratosthenes.PrimeOfEratosthenes>();

        sieveBase = BigInteger.ZERO;
        sieveBound = BigInteger.valueOf(seiveLen);
        sieveLength = sieveBound;

        sieve = new boolean[seiveLen];
        sievePos = 0;

        Arrays.fill(sieve, true);
        sieve[0] = false;
        sieve[1] = false;
    }

    public SieveOfEratosthenes() {
        this(DEFAULT_SEIVE_LEN);
    }

    @Override
    public Iterator<BigInteger> iterator() {
        return this;
    }

    /**
     * Get the next prime number.
     */
    @Override
    public BigInteger next() {
        while (true) {
            while (++sievePos < sieve.length) {
                if (sieve[sievePos]) {
                    return foundPrime();
                }
            }
            advanceSieve();
            sievePos = -1;
        }
    }

    /**
     * The number represented by the current sieve position is prime.
     * 
     * Add the prime number to the list of primes and return the value.
     */
    private BigInteger foundPrime() {
        PrimeOfEratosthenes prime = new PrimeOfEratosthenes();
        prime.remain = BigInteger.valueOf(sievePos);
        prime.prime = sieveBase.add(prime.remain);
        prime.remain = prime.remain.add(prime.prime);
        advancePrime(prime);
        primes.add(prime);
        return prime.prime;
    }

    /**
     * Advance the sieve to the next batch of numbers.
     * 
     * Previously found prime numbers are used to mark off any composites in the
     * new range.
     */
    private void advanceSieve() {
        Arrays.fill(sieve, true);
        sieveBase = sieveBound;
        sieveBound = sieveBound.add(sieveLength);
        for (PrimeOfEratosthenes prime : primes) {
            prime.remain = prime.remain.subtract(sieveLength);
            advancePrime(prime);
        }
    }

    /**
     * Advance the prime remainder past the end of the current batch.
     * 
     * Mark composites of this prime in the current batch as such.
     */
    private void advancePrime(PrimeOfEratosthenes prime) {
        assert (prime.remain.signum() >= 0);
        while (prime.remain.compareTo(sieveLength) < 0) {
            sieve[prime.remain.intValue()] = false;
            prime.remain = prime.remain.add(prime.prime);
        }
    }

    /**
     * There are (to the best of my knowledge) an infinite number of primes.
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * So you say two is no longer prime? Yeah right!
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Count a few prime numbers and print some stats.
     */
    public static void main(String[] argv) {
        // number of primes to find before quitting
        int tofind = 1000000;

        long time = -System.nanoTime();

        SieveOfEratosthenes erato = new SieveOfEratosthenes();
        BigInteger last = erato.next();
        for (int found = 1; found < tofind; ++found) {
            last = erato.next();
        }

        time += System.nanoTime();

        System.out.format("Found %d primes in %.3f sec (%.1f per sec)%n",
                tofind, time / 1000000000f, 1000000000f * tofind / time);
        System.out.format("Last prime generated is %s%n", last);
    }

}
