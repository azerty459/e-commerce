package com.projet.ecommerce.utilitaire;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmailUtilitaire {

	@Autowired
	private JavaMailSender emailSender;

	public void sendWelcomeEmail(String destinataire, String prenom) {
		if (StringUtils.isBlank(prenom)) {
			prenom = "Anonyme";
		} else {
			prenom = prenom.toLowerCase();
			char[] temp = prenom.toCharArray();
			temp[0] = Character.toUpperCase(temp[0]);
			prenom = new String(temp);
		}

		String message = "Bonjour " + prenom + ",\n" +
				"\n" +
				"Votre compte a bien été créé.\n" +
				"\n" +
				"Vous pouvez accéder à votre compte en vous connectant sur le site XXX.\n" +
				"\n" +
				"Bonne journée,\n" +
				"L'équipe e-commerce";

		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(destinataire);
		email.setSubject("Création de compte");
		email.setText(message);

		this.emailSender.send(email);
	}

}
