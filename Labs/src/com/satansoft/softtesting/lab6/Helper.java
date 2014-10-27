package com.satansoft.softtesting.lab6;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Helper {

    private int g = 160;                //длина SEED в битах

    public BigInteger getSEED() {
        return new BigInteger(g, new Random());
    }


    public BigInteger getU(BigInteger SEED) {
        //SHA[SEED]
        BigInteger SHA_SEED = new BigInteger(toSHA1(SEED.toByteArray()), 16);

        //(SEED+1) mod 2^g
        BigInteger SEED_PLUS_ONE_MOD
                = SEED.add(BigInteger.ONE).mod(BigInteger.valueOf((long) Math.pow(2, g)));

        //SHA[(SEED+1) mod 2^g ]
        BigInteger SHA_SEED_PLUS_ONE_MOD = new BigInteger(toSHA1(SEED_PLUS_ONE_MOD.toByteArray()), 16);

        //U = SHA[SEED] XOR SHA[(SEED+1) mod 2^g
        return SHA_SEED.xor(SHA_SEED_PLUS_ONE_MOD);
    }


    public BigInteger getQ(BigInteger U) {
        //q = U OR 2^159 OR 1.
        return U.or(BigInteger.valueOf((long) Math.pow(2, 159))).or(BigInteger.ONE);
    }


    public boolean isPrime(BigInteger q, int numTimes) {
        return MillerRabin.isPrime(q, numTimes);
    }




    private static String toSHA1(byte[] convert) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        return byteArrayToHexString(md.digest(convert));
    }


    private static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (byte aB : b) {
            result +=
                    Integer.toString((aB & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }


}
