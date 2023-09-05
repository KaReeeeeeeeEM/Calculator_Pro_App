package com.example.calculatorpro;

import java.math.BigDecimal;
import java.util.Stack;

public class EvalJavaScript {
    public static BigDecimal calculateResult(String expression) {
        // Separate numbers and operators using the updated regular expression
        String[] tokens = expression.split("(?<=\\d)(?=[+\\-*/%])|(?<=[+\\-*/%])(?=\\d)|(?<=\\d\\.\\d)(?=[+\\-*/%])|(?<=[+\\-*/%])(?=\\d\\.\\d)|(?<=\\d)(?=%)|(?=%)(?=\\d)");

        // Initialize stacks for numbers and operators
        Stack<BigDecimal> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        try {
            for (String token : tokens) {
                token = token.trim(); // Remove any leading/trailing whitespace

                if (token.isEmpty()) {
                    // You can add code here to handle empty tokens if necessary
                    // For example, you might want to log a warning or throw an exception
                    continue; // Skip empty tokens
                } else if (isNumeric(token)) {
                    // If it's a number, push it onto the numbers stack
                    numbers.push(new BigDecimal(token));
                } else if (isOperator(token)) {
                    // If it's an operator, handle operator precedence
                    while (!operators.isEmpty() && hasHigherPrecedence(operators.peek(), token)) {
                        BigDecimal operand2 = numbers.pop();
                        BigDecimal operand1 = numbers.pop();
                        String operator = operators.pop();
                        BigDecimal result = applyOperator(operand1, operand2, operator);
                        numbers.push(result);
                    }
                    operators.push(token);
                }
            }

            // Process any remaining operators with error handling
            while (!operators.isEmpty()) {
                BigDecimal operand2 = numbers.pop();
                BigDecimal operand1 = numbers.pop();
                String operator = operators.pop();
                BigDecimal result = applyOperator(operand1, operand2, operator);
                numbers.push(result);
            }
        } catch (ArithmeticException e) {
            // Handle math errors (e.g., division by zero)
            throw new ArithmeticException("Math error: " + e.getMessage());
        }

        // The final result is on top of the 'numbers' stack
        return numbers.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static BigDecimal calculateModulus(BigDecimal operand1, BigDecimal operand2) {
        if (operand2.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Modulus by zero");
        }
        return operand1.remainder(operand2);
    }

    private static boolean isOperator(String str) {
        return str.matches("[+\\-*/%]");
    }

    private static boolean hasHigherPrecedence(String operator1, String operator2) {
        return (operator2.equals("*") || operator2.equals("/") || operator2.equals("%")) &&
                (operator1.equals("+") || operator1.equals("-"));
    }

    private static BigDecimal applyOperator(BigDecimal operand1, BigDecimal operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1.add(operand2);
            case "-":
                return operand1.subtract(operand2);
            case "*":
                return operand1.multiply(operand2);
            case "/":
                if (operand2.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1.divide(operand2, 10, BigDecimal.ROUND_HALF_UP); // You can adjust the scale as needed
            case "%":
                return calculateModulus(operand1, operand2);
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}
