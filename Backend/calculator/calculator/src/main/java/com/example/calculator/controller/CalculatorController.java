package com.example.calculator.controller;

import com.example.calculator.model.Calculation;
import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResult;
import com.example.calculator.model.User;
import com.example.calculator.service.CalculatorService;

import com.example.calculator.util.JwtUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "http://localhost:8080")
public class CalculatorController {
  private final static Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    private final CalculatorService calculatorService;
    private Long currentUserId;

  public CalculatorController(CalculatorService calculatorService) {
    this.calculatorService = calculatorService;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
    logger.debug("Login attempt for username: {}", username);
    User user = calculatorService.authenticateUser(username, password);
    if (user != null) {
      currentUserId = user.getId();
      String token = JwtUtil.generateToken(username); // Generating jwt token
      logger.debug("Login successful for user: {}, token: {}", username, token);
      return ResponseEntity.ok(token); // Returning JWT token
    }
    logger.warn("Login failed for user: {}", username);
    return ResponseEntity.status(401).body(null);
  }

  @PostMapping("/calculate")
    public ResponseEntity<CalculationResult> calculate(
        @RequestBody CalculationRequest calculationRequest) {
      if (currentUserId == null) {
        logger.warn("Calculation requested without logging in");
        return ResponseEntity.status(401).body(null);
      }
      logger.debug("Received calculation request: {}", calculationRequest);
      CalculationResult result = calculatorService.performCalculation(calculationRequest, currentUserId);
        logger.debug("Returning calculation result: {}", result);
      return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
  public ResponseEntity<List<Calculation>> getHistory() {
    if (currentUserId == null) {
      logger.warn("History requested without logging in");
      return ResponseEntity.status(401).body(null);
    }
    logger.debug("Fetching history for user: {}", currentUserId);
    List<Calculation> history = calculatorService.getRecentCalculations(currentUserId);
    logger.debug("Returning history: {}", history);
    return ResponseEntity.ok(history);
  }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        currentUserId = null;
        return ResponseEntity.ok("Logout successful");
    }
}
