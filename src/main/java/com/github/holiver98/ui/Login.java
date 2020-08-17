package com.github.holiver98.ui;

import com.github.holiver98.model.User;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView(name = "login")
public class Login extends VerticalLayout implements View {
    public Login(){
        TextField emailTF = new TextField("Email");
        PasswordField passwordPF = new PasswordField("Password");
        Button loginBtn = new Button("Login");

        addComponent(emailTF);
        addComponent(passwordPF);
        addComponent(loginBtn);

        Binder<User> binder = new Binder<>();
        binder.forField(emailTF)
                //.asRequired("required field")
                .withValidator(new EmailValidator("wrong email"))
                .bind(User::getEmailAddress, User::setEmailAddress);

        User user = new User();
        binder.setBean(user);

        loginBtn.addClickListener(clickEvent -> {
            BinderValidationStatus<User> result = binder.validate(); if(result.isOk() && !result.hasErrors()){Notification.show(user.toString());}});
    }
}
