package util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Mail {

    public static void main(String[] args) {
        Mail.send("christian.d.ruf@gmail.com", "test", "Computations ended");
    }

    public static void send(String to, String subject, String message) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.mail.de");
            email.setSmtpPort(465);
            email.setAuthentication("kalle.dammkar@mail.de", "!F4nd7Fuck!");
            email.setSSL(true);
            email.setTLS(true);
            email.setFrom("kalle.dammkar@mail.de", "Kalle");
            email.setSubject(subject);
            email.addTo(to);
            email.setMsg(message);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
