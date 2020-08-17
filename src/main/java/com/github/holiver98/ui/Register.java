package com.github.holiver98.ui;

import com.github.holiver98.model.User;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView(name = "register")
public class Register extends VerticalLayout implements View {
    public Register(){
        TextField usernameTF = new TextField("Username");
        TextField emailTF = new TextField("Email");
        PasswordField passwordPF = new PasswordField("Password");
        PasswordField confirmPasswordPF = new PasswordField("Confirm password");
        Button registerBtn = new Button("Register");

        addComponent(usernameTF);
        addComponent(emailTF);
        addComponent(passwordPF);
        addComponent(confirmPasswordPF);
        addComponent(registerBtn);

        Binder<User> binder = new Binder<>();
        binder.forField(emailTF)
                //.asRequired("required field")
                .withValidator(new EmailValidator("wrong email"))
                .bind(User::getEmailAddress, User::setEmailAddress);

        User user = new User();
        binder.setBean(user);

        registerBtn.addClickListener(clickEvent -> {
            BinderValidationStatus<User> result = binder.validate(); if(result.isOk() && !result.hasErrors()){
                Notification.show(user.toString());}});
    }
}
