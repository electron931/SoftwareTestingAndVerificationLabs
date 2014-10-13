package com.satansoft.softtesting.lab5;

import java.math.BigInteger;
import java.util.Random;


//Asmuth–Bloom secret sharing
public class AsmuthBloomScheme {

    private static final int MAX_RANDOM_INT = 20;           //keep it small for debugging

    private BigInteger M;           //secret
    private BigInteger p;           //prime number (p > M)
    private BigInteger M2;          //M'
    private BigInteger[] d;         //mutually prime numbers
    private BigInteger[] k;         //remainders

    private int m;                  //necessary number of users to restore secret
    private int n;                  //number of users



    public AsmuthBloomScheme(BigInteger secret, int m, int n) {
        M = secret;
        this.m = m;
        this.n = n;
        shareSecret();
    }


    //method restores secret using Chinese Remainder Theorem
    public BigInteger restoreSecret() {
        BigInteger M0 = d[0];
        for (int i = 1; i < this.m; i++) {
            M0 = M0.multiply(d[i]);
        }

        BigInteger[] Mi = new BigInteger[this.m];
        for (int i = 0; i < this.m; i++) {
            Mi[i] = M0.divide(d[i]);
        }

        BigInteger[] y = new BigInteger[this.m];
        for (int i = 0; i < this.m; i++) {
            BigInteger gcd = Mi[i].gcd(k[i]);
            BigInteger tempMi = Mi[i].divide(gcd);
            BigInteger tempK = k[i].divide(gcd);

            y[i] = BigInteger.ONE;
            BigInteger temp = tempMi.multiply(y[i]).mod(d[i]);
            while( temp.compareTo(tempK) != 0 ) {
                y[i] = y[i].add(BigInteger.ONE);
                temp = tempMi.multiply(y[i]).mod(d[i]);
            }
        }

        System.out.println("Y:");
        outputArray(y);

        for (int i = 0; i < this.m; i++) {
            Mi[i] = M0.divide(d[i]);
        }

        BigInteger x = Mi[0].multiply(y[0]);
        for (int i = 1; i < this.m; i++) {
            x = x.add(Mi[i].multiply(y[i]));
        }
        x = x.mod(M0);

        System.out.println("M' = " + x);

        return x.mod(this.p);
    }


    //method splits secret among n users (each user has {p, d[i], k[i]})
    private void shareSecret() {
        p = getPrimeNumber();
        System.out.println("P: " + p);

        d = getDivisors();
        System.out.println("Divisors:");
        outputArray(d);

        int r = getRandomNumber();
        System.out.println("r: " + r);

        M2 = M.add(p.multiply(
                new BigInteger(Integer.toString(r)))
        );
        System.out.println("M': " + M2);

        k = getRemainders();
        System.out.println("Remainders:");
        outputArray(k);
    }



    private BigInteger getPrimeNumber() {
        return M.nextProbablePrime();
    }



    private BigInteger[] getDivisors() {
        BigInteger[] divisors = new BigInteger[this.n];

        divisors = getCheckedDivisors(divisors, this.p, 0);

        return divisors;
    }



    private BigInteger[] getCheckedDivisors(BigInteger[] divisors, BigInteger first, int index) {
        divisors[0] = first.nextProbablePrime();

        for (int i = 1; i < n; i++) {
            divisors[i] = divisors[i - 1].nextProbablePrime();
            if (!isMutuallyPrime(divisors, i)) {
                divisors[i] = divisors[i].nextProbablePrime();
            }
        }

        BigInteger left = divisors[0];
        for (int i = 1; i < m; i++) {
            left = left.multiply(divisors[i]);
        }

        BigInteger right = divisors[this.n - this.m + 1].multiply(this.p);
        for (int i = this.n - this.m + 2; i < n; i++) {
            right = right.multiply(divisors[i]);
        }

        if (left.compareTo(right) != 1) {                       //left <= right
            divisors = getCheckedDivisors(divisors, divisors[index], ++index);
        }

        return divisors;
    }



    private BigInteger[] getRemainders() {
        BigInteger[] remainders = new BigInteger[this.n];

        for (int i = 0; i < this.n; i++) {
            remainders[i] = M2.mod(d[i]);
        }

        return remainders;
    }



    private boolean isMutuallyPrime(BigInteger[] divisors, int i) {
        BigInteger result = divisors[0];            //gcd
        for (int j = 1; j <= i; j++) {
            result = result.gcd(divisors[j]);
        }

        return result.compareTo(BigInteger.ONE) == 0;
    }



    private int getRandomNumber() {
        return new Random().nextInt(MAX_RANDOM_INT);
    }



    private void outputArray(BigInteger[] array) {
        for (BigInteger item : array) {
            System.out.println(item.toString());
        }
    }

}
