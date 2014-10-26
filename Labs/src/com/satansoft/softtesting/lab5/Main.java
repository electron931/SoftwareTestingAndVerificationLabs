package com.satansoft.softtesting.lab5;

/*
 Написать программу, реализующую пороговую схему (t,n) на основе китайской теоремы об остатках
 Условия:
     секрет a >= 256 bit
     n — количество долей, на которые был разделён секрет, 3 < n < 20
     m - количество долей, которые нужны для восстановления секрет, 2 < m < 19
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please, enter n, m and a secret:");

        String nString = bufferedReader.readLine();
        String mString = bufferedReader.readLine();
        String secretString = bufferedReader.readLine();

        int n = 0;
        int m = 0;
        BigInteger secret;

        try {
            n = Integer.parseInt(nString);
            m = Integer.parseInt(mString);
            secret = new BigInteger(secretString);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid number(s)!");
            return;
        }

        if ( (3 < n && n < 20) && (2 < m && m < 19) ) {
            AsmuthBloomScheme asmuthBloomScheme =
                    new AsmuthBloomScheme(secret, m, n);
            BigInteger restoredSecret = asmuthBloomScheme.restoreSecret();
            System.out.println("Secret: " + restoredSecret);
        }
        else {
            System.out.println("3 < n < 20; 2 < m < 19");
        }

    }

}
