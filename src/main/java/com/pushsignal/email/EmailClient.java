package com.pushsignal.email;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailClient {
	private static final Logger LOG = LoggerFactory.getLogger(EmailClient.class);

	private final Properties configProperties = new Properties();

	public EmailClient() {
		fetchConfig();
	}

	/**
	 * Send a single email.
	 */
	public void sendEmail(final String toEmailAddr,
			final String subject, final String body) {
		// Here, no Authenticator argument is used (it is null).
		// Authenticators are used to prompt the user for user
		// name and password.
		final Session session = Session.getDefaultInstance(configProperties, null);
		final MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("gdenning@pushsignal.com", "PushSignal"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddr));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (final MessagingException ex) {
			LOG.error("Error sending email.", ex);
		} catch (UnsupportedEncodingException ex) {
			LOG.error("Error sending email.", ex);
		}
	}

	/**
	 * Allows the config to be refreshed at runtime, instead of requiring a
	 * restart.
	 */
	public void refreshConfig() {
		configProperties.clear();
		fetchConfig();
	}

	/**
	 * Open a specific text file containing mail server parameters, and populate
	 * a corresponding Properties object.
	 */
	private void fetchConfig() {
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream("email.properties");
			if (input == null) {
				throw new IOException("Unable to locate email.properties on the classpath.");
			}
			configProperties.load(input);
		} catch (final IOException ex) {
			LOG.error("Cannot open and load mail server properties file.", ex);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (final IOException ex) {
				LOG.error("Cannot close mail server properties file.", ex);
			}
		}
	}
}
