package service;

public interface IMailService {

    /**
     * Sends an email to the specified emailAddress with the given content.
     *
     * @param emailAddress The address the email will be sent to.
     * @param content The text content of the email.
     */
    void sendMailTo(String emailAddress, String content);
}
