import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

	public static void main(String[] args) {

		final String username = "wallace.espindola@gmail.com";
		final String password = "xxxxxxxxxxxx";

		final String emailFrom = "teste@gmail.com";
		final String emailTo = "wejproducoes@gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		
		// Configurando para TLS
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));

			message.setSubject("Assunto e-mail de teste TLS");
			message.setText("Testando,\n\n Corpo do e-mail TLS de teste!");

			System.out.println("Enviando E-mail...");

			Transport.send(message);

			System.out.println("E-mail enviado!!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
