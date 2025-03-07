package com.example.calculator.service;

import com.example.calculator.dao.CalculationRepository;
import com.example.calculator.dao.UserRepository;
import com.example.calculator.model.Calculation;
import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResult;
import com.example.calculator.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CalculationRepository calculationRepository;

  public User authenticateUser(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null || !user.getPassword().equals(password)) {
      throw new IllegalArgumentException("Invalid username or password");
    }
    return user;
  }

  public CalculationResult performCalculation(CalculationRequest calculationRequest, Long userid) {
    double result = calculate(calculationRequest.getExpression());
    User user = userRepository.findById(userid).orElseThrow(() ->
        new IllegalArgumentException("User not found"));
    Calculation calc = new Calculation(
        calculationRequest.getExpression(), String.valueOf(result), user);
    calculationRepository.save(calc);
    return new CalculationResult(result);
  }

  public List<Calculation> getRecentCalculations(Long userId) {
    return calculationRepository.findTop10ByUserIdOrderByIdDesc(userId);
  }

  private double calculate(String expression) {
    expression = expression.replaceAll("\\s", "");
    double result = 0;
    StringBuilder currentNumber = new StringBuilder();
    char currentOperator = '+';

    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);

      if (Character.isDigit(c) || c == '.') {
        currentNumber.append(c);
      }
      if (isOperator(c) || i == expression.length() - 1) {
        double operand = Double.parseDouble(currentNumber.toString());
        switch (currentOperator) {
          case '+': result += operand; break;
          case '-': result -= operand; break;
          case '*': result *= operand; break;
          case '/':
            if (operand == 0) {
              throw new IllegalArgumentException("Cannot divide by zero");
            } else {
              result /= operand; break;
            }
          default:
            throw new IllegalArgumentException("Invalid operator: " + currentOperator);
        }
        currentOperator = c;
        currentNumber = new StringBuilder();
      }
    }

      return new CalculationResult(result).getResult();

  }
  private boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/';
  }
}

