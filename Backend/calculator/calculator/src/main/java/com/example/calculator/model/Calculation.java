package com.example.calculator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "calculations")
public class Calculation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The expression to be saved
   */
  @Setter
  @Column(nullable = false)
    private String expression;

  @Setter
  @Column(nullable = false)
    private String result;

  @Setter
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference
    private User user;

  public Calculation() {}

  public Calculation(String expression, String result, User user) {
    this.expression = expression;
    this.result = result;
    this.user = user;
  }

}
