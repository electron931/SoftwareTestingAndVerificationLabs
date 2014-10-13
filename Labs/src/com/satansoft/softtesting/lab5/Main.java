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

        System.out.println("Please, enter n and m:");

        String nString = bufferedReader.readLine();
        String mString = bufferedReader.readLine();

        int n = 0;
        int m = 0;

        try {
            n = Integer.parseInt(nString);
            m = Integer.parseInt(mString);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid number(s)!");
            return;
        }

        if ( (3 < n && n < 20) && (2 < m && m < 19) ) {
            String secret = "451";
            AsmuthBloomScheme asmuthBloomScheme =
                    new AsmuthBloomScheme(new BigInteger(secret), m, n);
            BigInteger restoredSecret = asmuthBloomScheme.restoreSecret();
            System.out.println("Secret: " + restoredSecret);
        }
        else {
            System.out.println("3 < n < 20; 2 < m < 19");
        }

    }

}
