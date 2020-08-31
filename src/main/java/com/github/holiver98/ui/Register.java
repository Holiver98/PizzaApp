package com.github.holiver98.ui;

import com.github.holiver98.model.User;
import com.github.holiver98.service.AlreadyExistsException;
import com.github.holiver98.service.IUserService;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "register")
public class Register extends VerticalLayout implements View {
    @Autowired
    private IUserService userService;
    private TextField usernameTF;
    private TextField emailTF;
    private PasswordField passwordPF;
    private PasswordField confirmPasswordPF;

    private static final String requiredFieldText = "Required field.";
    private static final int usernameMinLength = 3;
    private static final int usernameMaxLength = 20;
    private static final int passwordMinLength = 5;
    private static final int passwordMaxLength = 30;

    public Register(){
        usernameTF = new TextField("Username");
        emailTF = new TextField("Email");
        passwordPF = new PasswordField("Password");
        confirmPasswordPF = new PasswordField("Confirm password");
        Button registerBtn = new Button("Register");

        addComponent(usernameTF);
        addComponent(emailTF);
        addComponent(passwordPF);
        addComponent(confirmPasswordPF);
        addComponent(registerBtn);

        User user = new User();
        Binder<User> binder = new Binder<>();
        BindUserToForm(user, binder);

        registerBtn.addClickListener(clickEvent -> register(user, binder));
    }

    private void register(User user, Binder<User> binder) {
        BinderValidationStatus<User> result = binder.validate();
        if(result.isOk() && !result.hasErrors()){
            try {
                binder.writeBean(user);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            try {
                userService.register(user);
            } catch (AlreadyExistsException e) {
                Notification.show("Email already registered.");
            }
        }
    }

    private void BindUserToForm(User user, Binder<User> binder){
        binder.forField(usernameTF)
                .asRequired(requiredFieldText)
                .withValidator(new StringLengthValidator(
                        "Username length has to be between " + usernameMinLength + " and " + usernameMaxLength,
                        usernameMinLength, usernameMaxLength))
                .bind(User::getUsername, User::setUsername);

        binder.forField(emailTF)
                .asRequired(requiredFieldText)
                .withValidator(new EmailValidator("Invalid email."))
                .bind(User::getEmailAddress, User::setEmailAddress);

        SerializablePredicate<String> arePasswordFieldsMatching = s -> s.equals(confirmPasswordPF.getValue());

        binder.forField(passwordPF)
                .asRequired(requiredFieldText)
                .withValidator(new StringLengthValidator(
                        "Password length has to be between " + passwordMinLength + " and " + passwordMaxLength,
                        passwordMinLength, passwordMaxLength))
                .withValidator(arePasswordFieldsMatching, "Passwords don't match.")
                .bind(User::getPassword, User::setPassword);

        confirmPasswordPF.setRequiredIndicatorVisible(true);

        binder.setBean(user);
    }
}
