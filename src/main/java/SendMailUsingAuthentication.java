import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUsingAuthentication {
	private static final String SMTP_HOST_NAME = "smtp.gmail.com"; // or simply "localhost"
	private static final String SMTP_AUTH_USER = "wallace.espindola@gmail.com";
	private static final String SMTP_AUTH_PWD = "xxxxxxxxx";
	private static final String emailSubjectTxt = "Assunto e-mail de teste - usando TLS";
	private static final String emailMsgTxt = "Corpo do e-mail de teste \n\n Usando TLS";
	private static final String emailFromAddress = "wallace.espindola@gmail.com";
	private static final String SMTP_PORT_SSL = "465"; // default 25
	private static final String SMTP_PORT_TLS = "587"; // default 25

	// Add List of Email address to who email needs to be sent to
	private static final String[] emailList = { "wejproducoes@gmail.com" };

	public static void main(String args[]) throws Exception {
		SendMailUsingAuthentication smtpMailSender = new SendMailUsingAuthentication();
		smtpMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
		System.out.println("Sucessfully Sent mail to All Users");
	}

	public void postMail(String recipients[], String subject, String message, String from) 
			throws MessagingException, AuthenticationFailedException {
		
		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		

		// enviando email via SSL
//		props.put("mail.smtp.port", SMTP_PORT_SSL);
//		props.put("mail.smtp.socketFactory.port", SMTP_PORT_SSL);
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		// enviando email via TLS
		props.put("mail.smtp.port", SMTP_PORT_TLS);
		props.put("mail.smtp.starttls.enable", "true");
		
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		
		System.out.println("Enviando e-mail...");
		Transport.send(msg);
		System.out.println("E-mail enviado!!!");
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}
