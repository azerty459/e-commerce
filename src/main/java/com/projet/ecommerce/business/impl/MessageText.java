package com.projet.ecommerce.business.impl;

public class MessageText {

	private static String message;

	public static String getMessage() {
		return message;
	}

	public static void setMessage(String prenom) {

		message = "Bonjour " + prenom + ",\n" +
				"\n" +
				"Votre compte à bien été créé.\n" +
				"\n" +
				"Vous pouvez accéder a votre compte en vous connectant sur le site XXX.\n" +
				"\n" +
				"Bonne journée,\n" +
				"L'équipe e-commerce";
	}

}
