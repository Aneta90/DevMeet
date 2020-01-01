package pl.com.devmeet.devmeet.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route
@Theme(value = Lumo.class, variant = Lumo.DARK)
class MainView extends VerticalLayout {

    private Button normalUserButton = new Button("Normal User");
//    private Button adminButton = new Button("admin");

    public MainView() {

//        normalUserButtonConfig();
//        adminButtonActionConfig();

//        add(normalUserButton, adminButton);
        add(normalUserButton);
    }
//
//    void normalUserButtonConfig() {
//        this.normalUserButton.setIcon(new Icon(VaadinIcon.BUTTON));
//
//        this.normalUserButton.addClickListener(e ->
//                normalUserButton.getUI().ifPresent(ui -> ui.navigate("normal"))
//        );
//    }

//    void adminButtonActionConfig() {
//        this.normalUserButton.setIcon(new Icon(VaadinIcon.BUTTON));
//
//        this.adminButton.addClickListener(e ->
//                adminButton.getUI().ifPresent(ui -> ui.navigate("admin")));
//    }
}
