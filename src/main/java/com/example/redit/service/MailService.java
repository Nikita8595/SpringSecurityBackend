package com.example.redit.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.redit.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
	private final JavaMailSender javaMailSender;
	
	private final MailBuilder mailContentBuilder;
	
	void sendMail(NotificationEmail notificationEmail) throws Exception {
		MimeMessagePreparator messagePrepator =mimeMessage->{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("katarianikita0708@gmail.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			//messageHelper.setText(notificationEmail.getBody());
			messageHelper.setText(notificationEmail.getBody());
		};
		try {
			javaMailSender.send(messagePrepator);
			log.info("Activation Email Sent");
			
		}
		catch(Exception e) {
			throw new Exception("Exception occurred when sending mail to" +notificationEmail.getRecipient());
		}
	}
	

}
