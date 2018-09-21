package me.samfletcher;

import java.util.ArrayList;

/**
 * The Main class is the main driver for this application.
 * It is a simple calculator that takes a user's input and 
 * computes basic math.
 * 
 * @author Sam Fletcher
 * @version 2018-08-29
 */
public class Calculator
{
    /** Chars that are allowed in input */
    public final static char[] allowedChars = {32,42,43,45,46,47,48,49,50,51,52,53,54,55,56,57};
    /** Allowed operators */
    public final static char[] operators = {'+', '-', '*', '/'};
    /** Allowed numbers */
    public final static char[] nums = {' ','.','1','2','3','4','5','6','7','8','9','0'};
    /** Error message to be displayed */
    public final static String ERROR_MESSAGE = "= Syntax error";
    
    /**
     * Does the main calculations of the program
     * @param input The input to be manipulated
     */
    public static String calculate(String input) {
        // If result is not edited, then it will return with an error
        String result = ERROR_MESSAGE;
        // Checking format and making sure there were no errors when grouping
        if(checkFormat(input) && !grouping(input).contains("ERROR")) {
            // Setting up variables to be used
            ArrayList<String> grouped = grouping(input);
            double sum = 0;
            // Checking for one number input
            if(grouped.size() == 1)
                sum = Double.parseDouble(grouped.get(0));
            /*
             * Works with "PEMDAS"
             * (except without the "PE")
             */
            // Summing all multiplication
            while(grouped.contains("*"))
            {
                for(int i = 1; i < grouped.size(); i+=2)
                {
                    if(grouped.get(i).equals("*"))
                    {
                        sum = Double.parseDouble(grouped.get(i-1)) * Double.parseDouble(grouped.get(i+1));
                        grouped.set(i, ""+sum);
                        grouped.remove(i-1);
                        grouped.remove(i);
                        break;
                    }
                }
            }
            // Summing all division
            while(grouped.contains("/"))
            {
                for(int i = 1; i < grouped.size(); i+=2)
                {
                    if(grouped.get(i).equals("/"))
                    {
                        sum = Double.parseDouble(grouped.get(i-1)) / Double.parseDouble(grouped.get(i+1));
                        grouped.set(i, ""+sum);
                        grouped.remove(i-1);
                        grouped.remove(i);
                        break;
                    }
                }
            }
            // Summing all addition
            while(grouped.contains("+"))
            {
                for(int i = 1; i < grouped.size(); i+=2)
                {
                    if(grouped.get(i).equals("+"))
                    {
                        System.out.println(grouped.get(i-1) + " + " + grouped.get(i+1));
                        sum = Double.parseDouble(grouped.get(i-1)) + Double.parseDouble(grouped.get(i+1));
                        grouped.set(i, ""+sum);
                        grouped.remove(i-1);
                        grouped.remove(i);
                        break;
                    }
                }
            }
            // Summing all subtraction
            while(grouped.contains("-"))
            {
                for(int i = 1; i < grouped.size(); i+=2)
                {
                    if(grouped.get(i).equals("-"))
                    {
                        sum = Double.parseDouble(grouped.get(i-1)) - Double.parseDouble(grouped.get(i+1));
                        grouped.set(i, ""+sum);
                        grouped.remove(i-1);
                        grouped.remove(i);
                        break;
                    }
                }
            }
            result = "= "+sum;
        }
        return result;
    }
    
    /**
     * Takes input and separates the entities into groups. 
     * Ex: 
     *  "34+5" is passed,
     *  {"34", "+", "5"} is returned.
     *  
     * NOTE: Last index of returned array will read "ERROR" if there is one.
     * 
     * @param input the string to be separated
     * @return the array that grouped the strings
     */
    public static ArrayList<String> grouping(String input) {
        // The ArrayList to hold all the individual groups
        ArrayList<String> result = new ArrayList<String>();
        // The single string to hold a group to eventually add to the result
        String group = "";
        char current;
        char next = 'a';
        /* Used to check the first char of every token so that it's not invalid
         * Ex: "*3+1" isn't valid
         *     "3+*1" is not valid
         */
        boolean first = false;
        // Loops through input
        for(int i = 0; i < input.length(); i++) {
            // Making sure it isn't the last index
            if(i != input.length()-1) {
                // If not, gets the next char
                next = input.charAt(i+1);
            }
            // Gets the current char
            current = input.charAt(i);
            // Making sure the loop knows it's the first index
            if(i == 0) {
                first = true;
            }
            
            // Created to check if first value is valid
            if(first) {
                if(contains(nums, current)) {
                    group += current;
                    first = false;
                }
                /*
                 *  had to add this line to avoid inputs like "--5"
                 *  where it reads the first "-" as a number and the
                 *  second "-" as an operator
                 */
                else if(current=='-' && contains(nums,next)) {
                    group += current;
                    first = false;
                }
                // ERROR: first index of token is not valid or first two tokens are "--"
                else {
                    result.add("ERROR");
                    break;
                }
            }
            else {
                // Checks if next index is a number
                if(contains(nums,current)) {
                    group += current;
                }
                // Checks if next index is an operator
                else if(contains(operators,current)){
                    result.add(group);
                    result.add(""+current);
                    first = true;
                    group = "";
                    
                }
            }
        }
        result.add(group);
        // Removes all unnecessary spaces
        for(int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i).replaceAll("\\s+", ""));
            if(result.get(i).equals("."))
                result.add("ERROR");
        }
        // Fixes the error when user inputs '5-5-'
        if(result.get(result.size()-1) == "") {
            result.add("ERROR");
        }
        return result;
    }
    
    /**
     * Checks the format of a passed string
     * @param input the string that is being checked
     * @return whether or not it is the correct format
     */
    public static boolean checkFormat(String input) {
        for(int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            if(!contains(allowedChars, current)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if a given character is in an array of characters.
     * This is used with the checkFormat method.
     * @param data the array that is being looked through
     * @param target the target that is trying to be found 
     * within the given array
     * @return whether the target has been found or not
     */
    public static boolean contains(char[] data, char target) {
        for(int i = 0; i < data.length; i++) {
            if(data[i] == target) {
                return true;
            }
        }
        return false;
    }

}
