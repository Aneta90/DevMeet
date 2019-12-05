package pl.com.devmeet.devmeet.user.domain;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("users")
class UserGui extends VerticalLayout {

    private UserService service;
    private List<UserDto> userList;

    // vaadin components
    private H1 header1;
    private Grid<UserDto> userGrid;
    private TextField textFieldEmail;
    private ComboBox<String> comboBoxIsActive;

    public UserGui(UserService service) {
        this.service = service;
        userList = new ArrayList<>();
        userList = service.findAll();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        Notification.show("User", 2000, Notification.Position.MIDDLE);

        FormLayout filtersLayout = new FormLayout();
        header1 = new H1("devmeet app - user");
        comboBoxIsActive = new ComboBox<>("Activated");
        comboBoxIsActive.setItems("yes", "no");
        comboBoxIsActive.addValueChangeListener(e -> filterUserList());
        textFieldEmail = new TextField("Email");
        filtersLayout.add(textFieldEmail, comboBoxIsActive);

        userGrid = new Grid<>(UserDto.class);
        userGrid.removeColumnByKey("id");
        userGrid.removeColumnByKey("password");
        userGrid.removeColumnByKey("active");
        userGrid.removeColumnByKey("creationTime");
        userGrid.removeColumnByKey("loginTime");
        userGrid.removeColumnByKey("modificationTime");

        userGrid.addThemeVariants(
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES);
        refreshGrid(userList);

        add(header1, filtersLayout, userGrid);
    }

    private void filterUserList() {
        if (comboBoxIsActive.getValue() != null) {
            if (comboBoxIsActive.getValue().equals("yes"))
                userList = service.findAllByIsActive(true);
            if (comboBoxIsActive.getValue().equals("no"))
                userList = service.findAllByIsActive(false);
        } else userList = service.findAll();
        refreshGrid(userList);
    }

    private void refreshGrid(List<UserDto> userList) {
        userGrid.setItems(userList);
        userGrid.getDataProvider().refreshAll();
    }
}
