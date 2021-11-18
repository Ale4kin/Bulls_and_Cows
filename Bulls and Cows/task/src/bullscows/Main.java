package bullscows;

import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int turn = 1;


        System.out.println("Please, enter the secret code's length:");

        String string = scanner.nextLine();
        String inputSize = string.replace(">", "");



        try {
            int a = Integer.parseInt(inputSize);
        } catch (NumberFormatException e) {

            System.out.println("Error: " + "\"" + inputSize + "\"" + " isn't a valid number.");
            return;
        }


        if (Integer.parseInt(inputSize) > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        } else if (Integer.parseInt(inputSize) <= 0) {
            System.out.println("Error: the input isn't a valid number.");
            return;
        }else {
            System.out.println("Input the number of possible symbols in the code:");
        }

        int size = Integer.parseInt(inputSize);
        int possibleNumber = scanner.nextInt();
        scanner.nextLine();


        try {
            getRandomSecretCode(size, possibleNumber);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: it's not possible to generate a code with a length of " + size + " with " + possibleNumber + " unique symbols.");
            return;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: it's not possible to generate a code");
            return;
        }


        String resultPig = getRandomSecretCode(size, possibleNumber);


        System.out.println("Okay, let's start a game!");


        while (true) {

            System.out.println("Turn " + turn + ":");

            String input = scanner.nextLine();


            int countBulls = 0;
            int countCows = 0;


            char[] array1 = resultPig.toCharArray();
            char[] array2 = input.toCharArray();

            for (int i = 0; i < array1.length; i++) {
                if (array1[i] == array2[i]) {
                    countBulls += 1;
                }
            }
            for (int i = 0; i < array1.length; i++) {
                for (int j = 0; j < array2.length; j++) {
                    if (array1[i] == array2[j] && i != j) {

                        countCows += 1;

                    }
                }
            }


            if (countCows == 0 && countBulls > 0 && countBulls < size) {
                System.out.println("Grade: " + countBulls + " bull(s).");
                turn = turn + 1;
            } else if (countBulls == 0 && countCows > 0) {
                System.out.println("Grade: " + countCows + " cow(s).");
                turn = turn + 1;
            } else if (countBulls == 0 && countCows == 0) {
                System.out.println("Grade: None.");
                turn = turn + 1;
            } else if (countBulls > 0 && countCows > 0 && countBulls < size) {
                System.out.println("Grade: " + countBulls + " bull(s) and " + countCows + " cow(s).");
                turn = turn + 1;
            } else if (countBulls == size) {
                System.out.println("Grade: " + countBulls + " bull(s).");
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }


        }
    }

    private static String getRandomSecretCode(int size, int possibleNumber) {
        Random random = new Random();
        char[] code = new char[size];


        ArrayList<Character> source = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

        List secretCode = source.subList(0, possibleNumber);


        for (int i = 0; i < size; i++) {
            code[i] = (char) secretCode.remove(random.nextInt(secretCode.size() - 1));

        }

        String resultPig = new String(code);


        for (int i = 0; i < size; i++) {
            code[i] = '*';

        }
        if (possibleNumber <= 10) {
            System.out.println("The secret is prepared: " + new String(code) + " (0-" + secretCode.get(secretCode.size() - 1) + ")");
        } else if (possibleNumber > 10) {
            System.out.println("The secret is prepared: " + new String(code) + " (0-9, a-" + secretCode.get(secretCode.size() - 1) + ")");
        }
        return resultPig;
    }


}

