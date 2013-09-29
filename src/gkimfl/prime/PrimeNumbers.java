package gkimfl.prime;

import java.math.BigInteger;
import java.util.Iterator;

public class PrimeNumbers implements Iterable<BigInteger> {
    int len;

    public PrimeNumbers(int seiveLen) {
        len = seiveLen;
    }

    public PrimeNumbers() {
        len = SieveOfEratosthenes.DEFAULT_SEIVE_LEN;
    }

    @Override
    public Iterator<BigInteger> iterator() {
        return new SieveOfEratosthenes(len);
    }
}
