package es.jdlopez.sqlcmd;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    private Authenticator auth;
    private Properties sessionProps;
    private String from;

    public SendMail(String host, String port, String from) {
        this(host, port, from, false, false, null, null);
    }
    public SendMail(String host, String port, String from, boolean auth, boolean tls, String user, String pass) {
        this.from = from;
        this.sessionProps = new Properties();
        sessionProps.setProperty("mail.smtp.host", host);
        sessionProps.setProperty("mail.smtp.port", port);
        if (auth) {
            sessionProps.setProperty("mail.smtp.auth", "true");
            // just user pass authenticator
            this.auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            };
        }
        if (tls)
            sessionProps.setProperty("mail.smtp.starttls.enable", "true");

    }

    public void sendText(String subject, String body, String to) throws MessagingException {
        Session session = Session.getInstance(sessionProps, auth);
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(from));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

        msg.setSubject(subject);

        msg.setText(body);
        // Let's tranport sets default value
        //msg.setHeader("X-Mailer", mailer);
        //msg.setSentDate(new Date());

	    Transport.send(msg);
    }

    public void sendMime(String mime, String subject, String body, String to) throws MessagingException {
        Session session = Session.getInstance(sessionProps, auth);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject);
        msg.setContent(body, mime);
        Transport.send(msg);
    }
}
