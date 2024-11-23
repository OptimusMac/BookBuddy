package ru.optimius.bookbuddy.configs;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@ConfigurationProperties(prefix = "image")
@Component
public class ImageConfig {

  private String uploadDir;

}