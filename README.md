# GkIMFL's fun with primes

I like primes :)  They make cryptography hard.

## PrimeNumbersRange

This is a toy program to test an idea about using the greatest common
divisor method to quickly check for primes.  The idea is to check for
a common divisor between a candidate prime and a carefully crafted
number.  The crafted number is a product of all primes from two to the
square root of the candidate.  If the greatest common divisor is one,
then the candidate is prime.

From my laptop, starting at one hundred trillion:

  Starting value 100000000000000
  Ending value 100000000010000
  Preparation in 2.19 sec (6172 composite values)
  Tested 10000 numbers in 7.25 sec (1379.97 per sec)
  Found 304 prime numbers

From my laptop, starting at one quadrillion:

  Starting value 1000000000000000
  Ending value 1000000000010000
  Preparation in 16.87 sec (18221 composite values)
  Tested 10000 numbers in 18.49 sec (540.78 per sec)
  Found 263 prime numbers

## SieveOfEratosthenes

This is perhaps the most important algorithm to know if you go for an
interview at one of the big west coast software companies.  Nuff said.

