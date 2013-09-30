# GkIMFL's fun with primes

I like primes :)  They make cryptography hard.

## SieveOfEratosthenes

This is perhaps the most important algorithm to know if you go for an
interview at one of the big west coast software companies.  Nuff said.

From my laptop, enumerate the first million primes:

    Found 1000000 primes in 4.510 sec (221705.6 per sec)
    Last prime generated is 15485863

## SieveOfEratosthenesSegment

This searches for primes in a segment of candidate values.  The
segment is bounded by successive square numbers, so all the values in
the segment have the same integer square root.  The sieve for the
segment is marked off using primes up to the square root.  That is
sufficient to mark off all the composites.  If a composite number has
a prime factor greater than the square root, then it must also have a
prime factor that is less than the square root.

I wrote this after the following, PrimeNumbersRange.  The segmented
sieve algorithm is much faster, it turns out.

From my laptop, test ten thousand numbers around one quadrillion:

    Starting value 1000000000000000
    Ending value 1000000000010000
    Preparation in 10.00 sec
    Tested 10000 numbers in 0.00 sec (37224952.00 per sec)
    Found 263 prime numbers

## PrimeNumbersRange

This is a toy program to test an idea about using the greatest common
divisor method to check for primes.  The idea is to check for a common
divisor between a candidate prime and a carefully crafted number.  The
crafted number is a product of all primes from two to the square root
of the candidate.  If the greatest common divisor is one, then the
candidate is prime.

The range of numbers to test is based on an arbitrary number so search
around.  A valid range is computed such that the chosen number is
within the range.  Prime numbers below the range may be a factor of
the value used for testing.  Testing numbers below the range has the
effect of misidentifying some prime numbers as composite.  Composite
numbers above the range may have a prime factor greater than the
largest factor of the number used for testing.  Testing numbers above
the range has the effect of misidentifying some composite numbers as
prime.

From my laptop, test for primes around one billion:

    Starting value 999950884
    Ending value 1000014129
    Preparation in 0.17 sec (31 composite values)
    Tested 63245 numbers in 0.24 sec (265120.44 per sec)
    Found 3057 prime numbers

From my laptop, test for primes around one trillion:

    Starting value 1000000000000
    Ending value 1000002000001
    Preparation in 0.21 sec (720 composite values)
    Tested 2000001 numbers in 153.97 sec (12989.42 per sec)
    Found 72413 prime numbers

This is starting to be a rather large range to search, so you can
specify the maximum size of the range with howmany.

From my laptop, test ten thousand numbers around one trillion:

    Starting value 1000000000000
    Ending value 1000000010000
    Preparation in 0.20 sec (720 composite values)
    Tested 10000 numbers in 0.84 sec (11885.77 per sec)
    Found 335 prime numbers

From my laptop, test ten thousand numbers around one quadrillion:

    Starting value 1000000000000000
    Ending value 1000000000010000
    Preparation in 17.19 sec (18221 composite values)
    Tested 10000 numbers in 18.62 sec (537.03 per sec)
    Found 263 prime numbers


