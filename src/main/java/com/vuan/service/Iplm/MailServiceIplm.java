package com.vuan.service.Iplm;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.vuan.model.MailDTO;
import com.vuan.service.MailService;

@Service
public class MailServiceIplm implements MailService{
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendEmail(MailDTO mailDTO) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			 
            mimeMessageHelper.setSubject(mailDTO.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mailDTO.getMailFrom(), "Vu An"));
            mimeMessageHelper.setTo(mailDTO.getMailTo());
            mimeMessageHelper.setText(mailDTO.getMailContent() , true);
            
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            
		} catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		
	}

}
