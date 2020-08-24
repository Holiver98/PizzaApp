package com.github.holiver98.ui;

import com.github.holiver98.model.User;
import com.github.holiver98.service.IUserService;
import com.github.holiver98.service.IncorrectPasswordException;
import com.github.holiver98.service.NotFoundException;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "login")
public class Login extends VerticalLayout implements View {
    @Autowired
    private IUserService userService;

    public Login(){
        TextField emailTF = new TextField("Email");
        PasswordField passwordPF = new PasswordField("Password");
        Button loginBtn = new Button("Login");

        addComponent(emailTF);
        addComponent(passwordPF);
        addComponent(loginBtn);

        Binder<User> binder = new Binder<>();
        binder.forField(emailTF)
                .asRequired("Required field")
                .withValidator(new EmailValidator("wrong email"))
                .bind(User::getEmailAddress, User::setEmailAddress);
        binder.forField(passwordPF)
                .asRequired("Required field")
                .bind(User::getPassword, User::setPassword);

        User user = new User();
        binder.setBean(user);

        loginBtn.addClickListener(clickEvent -> {
            BinderValidationStatus<User> result = binder.validate(); if(result.isOk() && !result.hasErrors()){login(user);}});
    }

    private void login(User user){
        try {
            userService.login(user.getEmailAddress(), user.getPassword());
        } catch (IncorrectPasswordException e) {
            //Invalid password
            Notification.show("Invalid email or password.", Notification.Type.WARNING_MESSAGE);
        } catch (NotFoundException e) {
            //Invalid email
            Notification.show("Invalid email or password.", Notification.Type.WARNING_MESSAGE);
        }
    }
}
