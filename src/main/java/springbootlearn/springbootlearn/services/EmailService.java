package springbootlearn.springbootlearn.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    
   public String sendEmail(String to,String subject,String body){
      try {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);
        javaMailSender.send(mail);
        return "sended";
      } catch (Exception e) {
        log.error("Error occured while sending email", e.getMessage());
        throw new RuntimeException(e.getMessage());
      }
   }
}
