package com.springboot.data.jpa.springbootdatajpa.controller;

import com.springboot.data.jpa.springbootdatajpa.entity.User;
import com.springboot.data.jpa.springbootdatajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

  @Autowired private UserRepository userRepository;

  @GetMapping("/user/{id}")
  public User getUserById(@PathVariable("id") Integer id) {
    Optional<User> user = userRepository.findById(id);
    return user.get();
  }
}
