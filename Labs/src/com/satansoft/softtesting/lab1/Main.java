package com.satansoft.softtesting.lab1;

/*
* The program reads three integer values from a console.
* The three values are interpreted as representing the lengths
* of the sides of a triangle. The program prints a message that
* states whether the triangle is scalene (неравносторонний),
* isosceles (равнобедренный), or equilateral (равносторонний)
* */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    private static int firstLength;
    private static int secondLength;
    private static int thirdLength;



    public static void main(String[] args) throws IOException {

        String resultMessage;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please, enter three integer values" +
                " (lengths of the sides of a triangle)");

        String firstLengthString = bufferedReader.readLine();
        String secondLengthString = bufferedReader.readLine();
        String thirdLengthString = bufferedReader.readLine();

        if ( isCorrectStrings(firstLengthString, secondLengthString, thirdLengthString) ) {

            try {
                convertStringsToIntegers(firstLengthString,
                                         secondLengthString,
                                         thirdLengthString);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid number(s)!");
                return;
            }

            if (!isPositive(firstLength, secondLength, thirdLength)) {
                System.out.println("Numbers must be positive!");
                return;
            }

            if (canTriangleBeBuilt(firstLength, secondLength, thirdLength)) {

                if (isEquilateral(firstLength, secondLength, thirdLength)) {
                    resultMessage = "The triangle is equilateral";
                }
                else if (isIsosceles(firstLength, secondLength, thirdLength)) {
                    resultMessage = "The triangle is isosceles";
                }
                else {
                    resultMessage = "The triangle is scalene";
                }



            }
            else {
                resultMessage = "The triangle cannot be built with these lengths!";
            }

        }
        else {
            resultMessage = "You must enter three integer values!";
        }

        System.out.println(resultMessage);

    }



    private static boolean isCorrectStrings(String firstLengthString,
                                            String secondLengthString,
                                            String thirdLengthString) {
        return (
                 !firstLengthString.equals("")  &&
                 !secondLengthString.equals("") &&
                 !thirdLengthString.equals("")
               );
    }



    private static void convertStringsToIntegers(String firstLengthString,
                                                 String secondLengthString,
                                                 String thirdLengthString)
            throws NumberFormatException {
        firstLength = Integer.parseInt(firstLengthString);
        secondLength = Integer.parseInt(secondLengthString);
        thirdLength = Integer.parseInt(thirdLengthString);
    }



    private static boolean isPositive(int firstLength, int secondLength, int thirdLength) {
        return (
                 firstLength  > 0  &&
                 secondLength > 0  &&
                 thirdLength  > 0
               );
    }



    private static boolean canTriangleBeBuilt(int firstLength, int secondLength, int thirdLength) {
        return (
                 (firstLength < secondLength + thirdLength) &&
                 (secondLength < thirdLength + firstLength) &&
                 (thirdLength < secondLength + firstLength)
               );
    }



    private static boolean isEquilateral(int firstLength, int secondLength, int thirdLength) {
        return (
                 firstLength == secondLength &&
                 secondLength == thirdLength
               );
    }



    private static boolean isIsosceles(int firstLength, int secondLength, int thirdLength) {
        return (
                 (firstLength == secondLength &&
                 secondLength != thirdLength)
                    ||
                 (firstLength != secondLength &&
                 secondLength == thirdLength)
                    ||
                 (firstLength != secondLength &&
                 firstLength == thirdLength)
               );
    }


}
