package ru.optimius.bookbuddy.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module.Open;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.optimius.bookbuddy.configs.ImageConfig;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.service.BookService;

@RestController
@RequestMapping("/photo")
@AllArgsConstructor
public class PhotoController {

  private final ImageConfig imageConfig;
  private BookService bookService;


  @PostMapping("/upload")
  public ResponseEntity<?> uploadPhoto(@RequestParam String url, @RequestParam Long bookID){
    BookEntity bookEntity = bookService.findById(bookID)
        .orElse(null);

    if(bookEntity == null){
      return ResponseEntity.notFound().build();
    }

    String uuid = bookEntity.getUuid().toString();
    try {
      String fileName = uuid + ".jpg";
      Path destinationPath = Paths.get(imageConfig.getUploadDir(), fileName);

      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);

      if (response.getStatusCode().is2xxSuccessful()) {
        Files.createDirectories(destinationPath.getParent());
        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationPath.toFile())) {
          StreamUtils.copy(response.getBody(), fileOutputStream);
        }
        return ResponseEntity.ok("Фотография успешно загружена.");
      } else {
        return ResponseEntity.status(response.getStatusCode()).body("Не удалось загрузить файл.");
      }
    } catch (IOException e) {
      return ResponseEntity.status(500).body("Произошла ошибка при сохранении файла: " + e.getMessage());
    }
  }

  @GetMapping("/upload/{bookID}")
  public ResponseEntity<Resource> getPhoto(@PathVariable Long bookID) throws IOException {
    // Находим книгу по ID
    BookEntity bookEntity = bookService.findById(bookID).orElse(null);

    if (bookEntity == null) {
      return ResponseEntity.notFound().build();
    }

    String uuid = bookEntity.getUuid().toString();
    String fileName = uuid + ".jpg";

    Path filePath = Paths.get(imageConfig.getUploadDir()).resolve(fileName);

    Resource resource = new UrlResource(filePath.toUri());

    if (resource.exists() && resource.isReadable()) {
      return ResponseEntity.ok()
          .header(
              HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
          .body(resource);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
