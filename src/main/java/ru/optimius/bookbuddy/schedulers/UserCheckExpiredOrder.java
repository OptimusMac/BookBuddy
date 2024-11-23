package ru.optimius.bookbuddy.schedulers;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.OrderEntity;
import ru.optimius.bookbuddy.entities.UserEntity;
import ru.optimius.bookbuddy.repositories.UserRepository;

@Component
@AllArgsConstructor
public class UserCheckExpiredOrder {

  private UserRepository userRepository;

  private JavaMailSender mailSender;

  @SneakyThrows
  @Scheduled(cron = "0 0 9 * * ?")
  public void checkUsers() {
    for (UserEntity userEntity : userRepository.findAll()) {
      Collection<OrderEntity> bookExpired = findExpiredBook(userEntity);
      if (bookExpired.isEmpty()) {
        continue;
      }
      sendHtmlEmail(userEntity.getEmail(), "Уведомление: Истёк срок возврата книги",
          buildHtmlEmailBody(userEntity, bookExpired));
    }
  }

  private Collection<OrderEntity> findExpiredBook(UserEntity user) {
    Instant instant = Instant.now();
    return user.getOrderBooks().stream().filter(bookEntity -> {
      Instant instantBook = bookEntity.getInstant().minus(Duration.of(3, ChronoUnit.HOURS));
      return instant.isAfter(instantBook);
    }).toList();
  }

  public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(htmlBody, true);
    helper.setFrom("jormo2@mail.ru");

    mailSender.send(mimeMessage);


  }

  private String buildHtmlEmailBody(UserEntity user, Collection<OrderEntity> expiredOrders) {
    StringBuilder bookRows = new StringBuilder();

    for (OrderEntity order : expiredOrders) {
      BookEntity book = order.getBookEntity();
      bookRows.append("<tr>")
          .append("<td>").append(book.getName()).append("</td>")
          .append("<td>").append(book.getAuthor()).append("</td>")
          .append("<td>").append(book.getPrice()).append(" руб.</td>")
          .append("<td>").append(order.getInstant()).append("</td>")
          .append("<td><img src=\"http://localhost:8080/photo/upload/")
          .append(book.getId()) // Предположим, что у каждой книги есть уникальный ID
          .append("\" alt=\"Cover\" width=\"50\"></td>")
          .append("</tr>");
    }
    return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f9f9f9; }
                .container { width: 80%; margin: 20px auto; background-color: #ffffff; border: 1px solid #dddddd; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); padding: 20px; }
                .header { text-align: center; padding: 10px 0; }
                .header img { max-width: 100px; }
                .header h1 { color: #333333; margin: 0; }
                .content { margin: 20px 0; }
                .content p { font-size: 16px; color: #555555; }
                .table-container { margin-top: 20px; }
                table { width: 100%; border-collapse: collapse; margin: 10px 0; }
                table th, table td { padding: 10px; text-align: left; }
                table th { background-color: #4CAF50; color: #ffffff; }
                table tr:nth-child(even) { background-color: #f2f2f2; }
                table tr:hover { background-color: #f1f1f1; }
                .footer { text-align: center; margin-top: 20px; font-size: 14px; color: #777777; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <!-- Динамическая вставка логотипа -->
                    <img src="http://localhost:8080/photo/upload/logo" alt="Library Logo">
                    <h1>Уведомление о возврате</h1>
                </div>
                <div class="content">
                    <p>Уважаемый(ая) <strong>""" + user.getFirstName() + " " + user.getSecondName() + """
        </strong>,</p>
        <p>Напоминаем, что срок возврата следующих книг истёк:</p>
                    <div class="table-container">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Название книги</th>
                                    <th>Автор</th>
                                    <th>Цена</th>
                                    <th>Срок возврата</th>
                                    <th>Обложка</th> <!-- Столбец для обложки -->
                                </tr>
                            </thead>
                            <tbody>
        """ + bookRows + """
        </tbody>
                        </table>
                    </div>
                    <p>Пожалуйста, верните книги как можно скорее, чтобы избежать штрафов.</p>
                    <p>Спасибо за использование нашей библиотеки!</p>
                </div>
                <div class="footer">
        С уважением,<br>
        Ваша библиотека
        </div>
            </div>
        </body>
        </html>
        """;
}

}
