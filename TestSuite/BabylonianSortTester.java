// Tester for BabylonianSort, written by Tiger Sachse.

// To use, compile and run your program with these commands:
//      javac BabylonianSort.java BabylonianSortTester.java
//      java BabylonianSortTester

import java.io.*;
import java.util.*;
import java.util.Map.*;

// Class that provides tests for BabylonianSort.
public class BabylonianSortTester {

    private static int passed = 0;
    private static int failed = 0;
    private static final int INPUTS = 6;

    // Print a failure message and increment the failed counter.
    private static void fail(String message) {
        System.out.print("FAILED: ");
        System.out.println(message);
        failed++;
    }

    // Print a success message and increment the passed counter.
    private static void pass(String message) {
        System.out.print("PASSED: ");
        System.out.println(message);
        passed++;
    }

    // Print the final report.
    private static void printFinalReport() {
        System.out.printf("\nTests passed: %d\n", passed);
        System.out.printf("Tests failed: %d\n", failed);
        System.out.println("==========================================================\n");

        if (failed == 0) {
            System.out.println("Congratulations! You are passing all test cases.");
            System.out.println("It is highly recommended that you write some tests");
            System.out.println("for yourself, as these tests are not comprehensive.");
        }
        else {
            System.out.println("You appear to be failing some tests.");
            System.out.println("Keep working hard and don't give up!");
        }
    }

    // Test conversion of decimal longs to sexagesimal strings.
    private static void testDecimalToSexagesimal() {
        HashMap<Integer, String> tests = new HashMap<>();
        tests.put(0, "0");
        tests.put(7, "7");
        tests.put(59, "X");
        tests.put(1337, "mh");
        tests.put(100000, "rKE");

        for (Entry<Integer, String> test : tests.entrySet()) {
            String result = BabylonianSort.decimalToSexagesimal(test.getKey());
            String message = String.format("decimalToSexagesimal(%d)", test.getKey());
            
            try {
                if (result.equals(test.getValue())) {
                    pass(message);
                }
                else {
                    fail(message + " **output mismatch**");
                }
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }
        }
    }

    // Test conversion of sexagesimal strings to decimal longs.
    private static void testSexagesimalToDecimal() {
        HashMap<String, Integer> tests = new HashMap<>();
        tests.put("0", 0);
        tests.put("7", 7);
        tests.put("X", 59);
        tests.put("mh", 1337);
        tests.put("rKE", 100000);

        for (Entry<String, Integer> test : tests.entrySet()) {
            long result = BabylonianSort.sexagesimalToDecimal(test.getKey());
            String message = String.format("sexagesimalToDecimal(\"%s\")", test.getKey());

            try {
                if (result == test.getValue()) {
                    pass(message);
                }
                else {
                    fail(message + " **output mismatch**");
                }
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }
        }
    }
  
    // Test that the proper error is thrown when given an invalid sexagesimal string.
    private static void testInvalidSexagesimalToDecimal() {
        String message = String.format("sexagesimalToDecimal(\"12aZ\")");

        try {
            BabylonianSort.sexagesimalToDecimal("12aZ");
            fail(message + " **exception expected**");
        }
        
        // This will catch the expected NumberFormatException and pass the case.
        catch (NumberFormatException e) {
            pass(message);
        }
       
        catch (Exception e) {
            fail(message + " **wrong exception**");
        }
    }
    
    private static void testInvalidSexagesimalToDecimalBig() {
        String message = String.format("sexagesimalToDecimalBig(\"X0000000000\")");

        try {
            BabylonianSort.sexagesimalToDecimal("X0000000000");
            fail(message + " **exception expected**");
        }
        
        // This will catch the expected ArithmeticException and pass the case.
        catch (ArithmeticException e) {
            pass(message);
        }
        
        catch (NumberFormatException e) {
            fail(message + " **wrong exception**");
        }
        
        catch (Exception e) {
            fail(message + " **wrong exception**");
        }
    }

    
    // Test that the submission recognizes valid sexagesimal strings.
    private static void testValidSexagesimalNumbers() {
        String[] validNumbers = {
            "17bbxX",
            "0000",
            "8ayT",
            "2",
        };

        for (String number : validNumbers) {
            String message = String.format("isValidSexagesimalNumber(\"%s\")", number);
            try {
                if (BabylonianSort.isValidSexagesimalNumber(number)) {
                    pass(message);
                }
                else {
                    fail(message + " **output mismatch**");
                }
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }
        }
    }

    // Test that the submission recognizes invalid sexagesimal strings.
    private static void testInvalidSexagesimalNumbers() {
        String[] invalidNumbers = {
            "-1",
            "AAZ",
            "**&%@#???",
        };
   
        for (String number : invalidNumbers) {
            String message = String.format("isValidSexagesimalNumber(\"%s\")", number);
            try {
                if (!BabylonianSort.isValidSexagesimalNumber(number)) {
                    pass(message);
                }
                else {
                    fail(message + " **output mismatch**");
                }
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }
        }
    }

    // Test that a number of inputs can be sorted properly.
    private static void testBabylonianSort() throws IOException {
        for (int input = 1; input <= INPUTS; input++) {
            Scanner scanner = new Scanner(new File(String.format("Inputs/Input%d.txt", input)));

            // The first integer of each input file is the number of strings
            // in the file.
            String[] numbers = new String[scanner.nextInt()];

            scanner.nextLine();
            for (int index = 0; index < numbers.length; index++) {
                numbers[index] = scanner.nextLine();
            }

            scanner.close();

            String message = String.format("babylonianSort(Inputs/Input%d.txt)", input);

            // Attempt to sort the array of numbers.
            try {
                BabylonianSort.babylonianSort(numbers);
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }

            boolean successful = true;
            scanner = new Scanner(new File(String.format("Outputs/Output%d.txt", input)));

            // Compare the numbers array to the expected output file. The output
            // file will list the numbers in order.
            for (int index = 0; index < numbers.length; index++) {
                if (!numbers[index].equals(scanner.nextLine())) {
                    fail(message + " **output mismatch**");
                    successful = false;
                    break;
                } 
            }

            // If all numbers matched, then pass the case.
            if (successful) {
                pass(message);
            }

            scanner.close();
        }
    }

    // Main entry point of the test class.
    public static void main(String[] args) throws IOException {
        System.out.println("Results:");
        System.out.println("==========================================================");

        testDecimalToSexagesimal();
        testSexagesimalToDecimal();
        testInvalidSexagesimalToDecimal();
        testInvalidSexagesimalToDecimalBig();
        testValidSexagesimalNumbers();
        testInvalidSexagesimalNumbers();
        testBabylonianSort();

        printFinalReport();
    }
}
