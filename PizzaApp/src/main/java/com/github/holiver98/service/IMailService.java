package com.github.holiver98.service;

import com.github.holiver98.model.Order;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

public interface IMailService {

    /**
     * Sends an email to the specified emailAddress with the given content.
     *
     * @param emailAddress The address the email will be sent to.
     * @param content The text content of the email.
     */
    void sendMailTo(String emailAddress, String content) throws MessagingException;

    /**
     * Sends an order confirmation email to the email address, that is found in the order.
     *
     * @param order Contains the order information, such as the user's email address and the total price.
     */
    void sendOrderConfirmationEmail(Order order) throws MessagingException;
}
