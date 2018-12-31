package isa.projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UrlPathHelper;

import isa.projekat.model.User;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment env;

	@Async
	public void sendActivationEmail(User user) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Aktivacioni email");
		String body = "Postovani  " + user.getUsername()
				+ ",\nDa biste aktivirali svoj nalog, potrebno je da kliknete na sledeci link: "
				+"http://" +env.getProperty("hostname") + "/activate/" + user.getUsername();
		mail.setText(body);
		javaMailSender.send(mail);
	}
}
