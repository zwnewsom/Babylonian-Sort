// Assignment 2: Babylonian Sort
// Name: Zachary Newsom
// NID: 4404139

import java.util.ArrayList;
import java.util.LinkedList; 
import java.util.List;
import java.util.Queue;
import java.math.BigInteger;

public class BabylonianSort
{
    static String str = null;
    static char[] digits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX".toCharArray();
    
    // returns index of a given sexagesimal digit within digits[]
    public static int findIndex(char c)
    {
        int i;
        
        for (i = 0; i < 60; i++)
            if (c == digits[i])
                return i;
        
        return -1;
    }
    
    public static void babylonianSort(String[] numbers) throws NumberFormatException
    {
        List<Queue<String>> buckets;
        int i, k, x, LSD, max, bucket, n = numbers.length;
        
        // ensure string(s) is/are valid sexagesimal number(s)
        for (String number : numbers)
            if (!isValidSexagesimalNumber(number))
                throw new NumberFormatException("Not a valid sexagesimal number");
        
        // create "buckets" to be used for sorting
        buckets = new ArrayList<>(60);
        for (i = 0; i < 60; i++)
            buckets.add(new LinkedList<>());
        
        // find max-length string
        max = numbers[0].length();
        for (i = 1; i < n; i++)
        {
            k = numbers[i].length();
            if (k > max)
                max = k;
        }
        
        // loop for each position in the max-length string
        for (LSD = 0; LSD < max; LSD++)
        {
            // place into buckets by LSD
            for (i = 0; i < n; i++)
            {
                k = numbers[i].length();
                x = k - 1 - LSD;
                
                // accounts for variable-length strings
                if (x < 0)
                    bucket = 0;
                else
                    bucket = findIndex(numbers[i].charAt(x));
                buckets.get(bucket).add(numbers[i]);
            }
            
            // remove from buckets and place back into the original array
            for (bucket = i = 0; bucket < 60; bucket++)
                while (!buckets.get(bucket).isEmpty())
                    numbers[i++] = buckets.get(bucket).remove();
        }
    }
    
    // helper method called by primary to implement recursion
    public static void decToSexHelper(long number)
    {
        if (number == 0)
            return;
        
        // recursively reduces 'number' by a factor of 60
        decToSexHelper(number / 60);
        
        // updates str by appending characters from digits[] which are indexed by
        // mod-ing the increasingly reduced 'number' by the target number base: 60
        str += Character.toString(digits[(int)(number % 60)]);
    }
    
    // primary method instantiates str, calls helper method and returns str
    public static String decimalToSexagesimal(long number)
    {
        str = new String();
    
        if (number == 0)
            str = Long.toString(number);
        else
            decToSexHelper(number);
        
        return str;
    }
    
    public static long sexagesimalToDecimal(String number) throws NumberFormatException, ArithmeticException
    {
        int i, index, n = number.length();
        BigInteger bigNumber = new BigInteger("0");
        
        // ensure given string is valid sexagesimal number
        if (!isValidSexagesimalNumber(number))
            throw new NumberFormatException("Not a valid sexagesimal number");
        
        // loop through each position in 'number', continually sum the product of the
        // index of each character's position in digits[] with increasing powers of 60
        for (i = 0; i < n; i++)
        {
            index = findIndex(number.charAt(n - 1 - i));
            bigNumber = bigNumber.add(BigInteger.valueOf(index * (long)Math.pow(60, i)));
        }
        
        // bitshift 'bigNumber' to the right by the max bit-length of a long int(64 bits)
        // if the resulting value is not equal to zero then the BigInteger is too big 
        // to be represented by a long and we throw an ArithmeticException
        if (bigNumber.shiftRight(64).intValue() != 0)
            throw new ArithmeticException("Number too large for long data type");
                
        return bigNumber.longValueExact();
    }
    
    public static boolean isValidSexagesimalNumber(String Number)
    {
        int i, n = Number.length();
        char[] newNum = Number.toCharArray();
        
        // ensure each digit in Number is within ASCII value ranges
        for (i = 0; i < n; i++)
        {
            if ((newNum[i] >= 48 && newNum[i] <= 57) || 
                (newNum[i] >= 65 && newNum[i] <= 88) ||
                (newNum[i] >= 97 && newNum[i] <= 122))
                continue;
            return false;
        }
        return true;
    }
}

