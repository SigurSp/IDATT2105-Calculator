package com.example.calculator.dao;

import com.example.calculator.model.Calculation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {
  List<Calculation> findTop10ByUserIdOrderByIdDesc(Long userid);
}
