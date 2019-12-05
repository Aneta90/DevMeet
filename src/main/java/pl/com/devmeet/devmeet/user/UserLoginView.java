package pl.com.devmeet.devmeet.user;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 19.11.2019
 * Time: 18:20
 */


//@Route(value = UserLoginView.ROUTE)
//@PageTitle("Login")
//@HtmlImport("frontend://bower_components/iron-form/iron-form.html")
@UIScope
public class UserLoginView extends UI {

    public static final String ROUTE = "login";

    FormLayout formLayout;

    @Autowired
    UserLoginLayout layout;

    public UserLoginView() {
        initForm();
        initActionForForm();
    }

    private void initForm() {
        TextField loginTextField = new TextField();
        loginTextField.getElement().setAttribute("name", "type your login");
        PasswordField passwordField = new PasswordField();
        passwordField.getElement().setAttribute("name", "pass");
        Button submitButton = new Button("Log in");
        submitButton.setId("submitbutton");

//        UI.getCurrent().getPage()
//                .executeJs("document.getElementById('submitbutton')" +
//                        ".addEventListener('click', () => document.getElementById('ironform')" +
//                        ".submit());");

        FormLayout formLayout = new FormLayout();
        formLayout.add(loginTextField, passwordField, submitButton);
    }

    private void initActionForForm() {
        Element formElement = new Element("form");
        formElement.setAttribute("method", "post");
        formElement.setAttribute("action", "login");
        formElement.appendChild(formLayout.getElement());

        Element ironForm = new Element("iron-form");
        ironForm.setAttribute("id", "ironform");
        ironForm.setAttribute("allow-redirect", true);
        ironForm.appendChild(formElement);

        getElement().appendChild(ironForm);
        layout.setClassName("login-view");
    }
}
