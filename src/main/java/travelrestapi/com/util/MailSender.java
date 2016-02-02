package travelrestapi.com.util;


import javax.mail.Message;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
import org.springframework.mail.javamail.JavaMailSender;  
import org.springframework.mail.javamail.MimeMessagePreparator;  

import org.springframework.beans.factory.annotation.Autowired;



public class MailSender
{

	private JavaMailSender mailSender;  
   
    public void setMailSender(JavaMailSender mailSender) 
	{  
        this.mailSender = mailSender;  
    }  
	
	public void sendMail(final String fromEmail, final String toEmail, final String subject, final String msg) throws Exception
	{
 		
		try
		{
			MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {  
	              
	                public void prepare(MimeMessage mimeMessage) throws Exception {  
	                   mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
	                   mimeMessage.setFrom(new InternetAddress(fromEmail));  
	                   mimeMessage.setSubject(subject);  
	                   mimeMessage.setText(msg);  
	                }  
	        };  
	        mailSender.send(messagePreparator);  
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
}
