package gkimfl.prime;

import static gkimfl.prime.Math.getSquareRoot;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;

public class SieveOfEratosthenesSegment implements Iterator<BigInteger>, Iterable<BigInteger> {

    BigInteger sieveBase;
    BigInteger sieveBound;
    BigInteger sieveLength;

    boolean sieve[];
    int sievePos;

    public SieveOfEratosthenesSegment(BigInteger around, BigInteger howmany) {

        BigInteger root = getSquareRoot(around);

        sieveBase = root.pow(2);
        sieveBound = root.add(BigInteger.ONE).pow(2);
        sieveLength = sieveBound.subtract(sieveBase);

        if (howmany != null && sieveLength.compareTo(howmany) > 0) {
            if (sieveBound.subtract(howmany).compareTo(around) > 0) {
                sieveBase = around;
            }
            sieveBound = sieveBase.add(howmany);
            sieveLength = howmany;
        }

        sieve = new boolean[sieveLength.intValue()];
        Arrays.fill(sieve, true);
        for (BigInteger prime : new SieveOfEratosthenes(1<<22)) {
            if (root.compareTo(prime) < 0) {
                break;
            }
            BigInteger remain = sieveBase.remainder(prime);
            if(!remain.equals(BigInteger.ZERO)) {
                remain = prime.subtract(remain);
            }
            while (remain.compareTo(sieveLength) < 0) {
                sieve[remain.intValue()] = false;
                remain = remain.add(prime);
            }
        }

        sievePos = 0;
        while (sievePos < sieve.length && !sieve[sievePos]) {
            ++sievePos;
        }
    }

    public SieveOfEratosthenesSegment(BigInteger around) {
        this(around, null);
    }

    @Override
    public Iterator<BigInteger> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return sievePos < sieve.length;
    }

    @Override
    public BigInteger next() {
        BigInteger prime = sieveBase.add(BigInteger.valueOf(sievePos));

        ++sievePos;
        while (sievePos < sieve.length && !sieve[sievePos]) {
            ++sievePos;
        }

        return prime;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] argv) {
        // look for primes around this value
        BigInteger around = new BigInteger("1000000000000");
        BigInteger howmany = new BigInteger("10000");

        // print prime numbers that are found
        boolean show = false;

        long time = 0;

        time = -System.nanoTime();
        SieveOfEratosthenesSegment range = new SieveOfEratosthenesSegment(around, howmany);
        time += System.nanoTime();

        System.out.format("Starting value %s%n", range.sieveBase);
        System.out.format("Ending value %s%n", range.sieveBound);
        System.out.format("Preparation in %.2f sec\n", time / 1000000000f);

        int delta = range.sieveBound.subtract(range.sieveBase).intValue();
        int count = 0;

        time = -System.nanoTime();
        for (BigInteger prime : range) {
            if (show) {
                time += System.nanoTime();
                System.out.println(prime);
                time -= System.nanoTime();
            }
            ++count;
        }
        time += System.nanoTime();
        System.out.format("Tested %d numbers in %.2f sec (%.2f per sec)\n",
                delta, time / 1000000000f, 1000000000f * delta / time);
        System.out.format("Found %d prime numbers\n",
                count);
    }
}
