package com.github.holiver98.ui;

import com.github.holiver98.model.Role;
import com.github.holiver98.model.User;
import com.github.holiver98.service.IUserService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@SpringView(name = "profile")
public class Profile extends VerticalLayout implements View {
    @Autowired
    private IUserService userService;

    private Label usernameLabel;
    private Label emailLabel;
    private Label roleLabel;
    private Button editPizzasBtn;
    private Button editIngredientsBtn;

    public Profile(){
        Label usernameCaptionLabel = new Label("Username:");
        Label emailCaptionLabel = new Label("Email address:");
        Label roleCaptionLabel = new Label("Role:");
        usernameLabel = new Label();
        emailLabel = new Label();
        roleLabel = new Label();

        editPizzasBtn = new Button("Edit pizzas");
        editPizzasBtn.addClickListener(clickEvent -> getUI().getNavigator().navigateTo("edit_pizzas"));
        editIngredientsBtn = new Button("Edit ingredients");

        addComponents(usernameCaptionLabel, usernameLabel,
                emailCaptionLabel, emailLabel,
                roleCaptionLabel, roleLabel);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null) {
            String encodedEmail = event.getParameters();
            String email = decode(encodedEmail);

            Optional<User> loggedInUser = userService.getLoggedInUser();
            loggedInUser.ifPresent(user -> setupUi(user));
        }
    }

    private void setupUi(User user){
        updateUiFields(user);
        addEditButtonsIfIsAdmin(user);
    }

    private void updateUiFields(User user) {
        usernameLabel.setValue(user.getUsername());
        emailLabel.setValue(user.getEmailAddress());
        roleLabel.setValue(user.getRole().name());
    }

    private void addEditButtonsIfIsAdmin(User user) {
        if(user.getRole().equals(Role.CHEF)){
            addComponent(editPizzasBtn);
            addComponent(editIngredientsBtn);
        }
    }

    private String decode(String encodedString){
        try {
            return URLDecoder.decode(encodedString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
