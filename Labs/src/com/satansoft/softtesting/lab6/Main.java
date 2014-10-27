package com.satansoft.softtesting.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/*
* Реализовать генератор больших простых чисел на основе FIPS 186
* */


public class Main {

    private final static int NUM_TIMES = 20;

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please, enter the number of the generated prime numbers:");

        String nString = bufferedReader.readLine();

        int n = 0;

        try {
            n = Integer.parseInt(nString);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid number!");
            return;
        }

        if (n > 0) {
            System.out.println("Prime numbers:");
            for (int i = 0; i < n; i++) {
                BigInteger prime = generateBigPrimeNumber();
                System.out.println(prime);
            }

        }

        else {
            System.out.println("n must be > 0");
        }

    }

    public static BigInteger generateBigPrimeNumber() {
        Helper helper = new Helper();

        BigInteger SEED;
        BigInteger U;
        BigInteger q;

        boolean isPrime = false;

        do {
            //Шаг 1. Выбираем произвольную последовательность из как минимум 160 бит
            // и называем ее SEED. Пусть g – длина SEED в битах.
            SEED = helper.getSEED();

            //Шаг 2. Вычисляем U = SHA[SEED] XOR SHA[(SEED+1) mod 2g ].
            U = helper.getU(SEED);

            //Шаг 3. Создаем q из U устанавливая младший и старший бит равным 1:
            //Заметим, что 2^159 < q < 2^160.
            q = helper.getQ(U);

            //Шаг 4. Проверяем q на простоту.
            isPrime = helper.isPrime(q, NUM_TIMES);
        }
        while (!isPrime);

        return q;
    }

}
