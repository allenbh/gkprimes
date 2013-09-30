package gkimfl.prime;

import java.math.BigInteger;

public class Math {
    /**
     * Calculate integer square root by binary search.
     */
    public static BigInteger getSquareRoot(BigInteger n) {
        BigInteger high = n.shiftRight(n.bitLength() >> 1);
        BigInteger low = n.divide(high).add(BigInteger.ONE);

        if (high.compareTo(low) < 0) {
            BigInteger tmp = low;
            low = high;
            high = tmp;
        }

        while (high.compareTo(low) >= 0) {
            BigInteger mid = low.add(high).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                high = mid.subtract(BigInteger.ONE);
            }
            else {
                low = mid.add(BigInteger.ONE);
            }
        }
        return low.subtract(BigInteger.ONE);
    }
}
