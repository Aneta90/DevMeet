package pl.com.devmeet.devmeetcore.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route
class MainView extends VerticalLayout {

    private Button normalUserButton = new Button("Normal User");
    private Button adminButton = new Button("Admin");
    private Button changeTheme = new Button("Light");
    private boolean darkTheme = true;

    private Text normUserInfoText = new Text("Creating a test user for testing views");
    private Text adminUserInfoText = new Text("Administration tools");
    private Text changeThemeInfoText = new Text("You can change theme");

    public MainView() {
        Div normUserLine = new Div();
        Div adminUserLine = new Div();
        Div themeSetting = new Div();

        normUserLine.add(normalUserButton, normUserInfoText);
        adminUserLine.add(adminButton, adminUserInfoText);
        themeSetting.add(changeTheme, changeThemeInfoText);

        normalUserButtonConfig();
        adminButtonActionConfig();
        adminChangeTheme();

        add(
                normUserLine,
                adminUserLine,
                themeSetting
        );
    }

    private void adminChangeTheme() {
        changeTheme.addClickListener(e -> {
            if (!darkTheme) {
                UI.getCurrent().getElement().setAttribute("theme", Lumo.DARK);
                changeTheme.setText("Light");
                darkTheme = true;
            } else {
                UI.getCurrent().getElement().setAttribute("theme", Lumo.LIGHT);
                changeTheme.setText("Dark");
                darkTheme = false;
            }
        });

    }

    //
    void normalUserButtonConfig() {
        normalUserButton.addClickListener(e ->
                normalUserButton.getUI().ifPresent(ui -> ui.navigate("normal"))
        );
    }

    void adminButtonActionConfig() {
        adminButton.addClickListener(e ->
                adminButton.getUI().ifPresent(ui -> ui.navigate("admin")));
    }
}
