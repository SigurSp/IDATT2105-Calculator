package com.example.calculator.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CalculationResult {

  private double result;

  public CalculationResult(double result) {
      this.result = result;
  }

  public double getResult() {
    return result;
  }

  public void setResult(double result) {
    this.result = result;
  }
}
