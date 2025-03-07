package com.example.calculator.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonManagedReference
    private List<Calculation> calculations;

  public User() {}

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }


}
