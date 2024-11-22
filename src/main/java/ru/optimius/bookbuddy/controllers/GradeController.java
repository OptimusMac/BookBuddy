package ru.optimius.bookbuddy.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.optimius.bookbuddy.dto.GradeDTO;
import ru.optimius.bookbuddy.service.GradeService;

@RestController
@RequestMapping("/grade")
@AllArgsConstructor
public class GradeController {


  private GradeService gradeService;

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody GradeDTO gradeDTO){
    return gradeService.create(gradeDTO);
  }

  @GetMapping("/user")
  public GradeDTO gradeUser(
      @RequestParam Long bookID,
      @RequestParam Long userID){
    return gradeService.gradeUser(bookID, userID);
  }



}
