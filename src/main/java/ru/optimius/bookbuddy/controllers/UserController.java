package ru.optimius.bookbuddy.controllers;

import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.optimius.bookbuddy.dto.UserDTO;
import ru.optimius.bookbuddy.entities.UserEntity;
import ru.optimius.bookbuddy.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {


  private UserService userService;

  @PostMapping("/create")
  public UserDTO create(@RequestBody UserEntity userEntity){
    return userService.create(userEntity);
  }


  @GetMapping("/all")
  public Collection<UserDTO> findAll(){
    return userService.findAll();
  }
}