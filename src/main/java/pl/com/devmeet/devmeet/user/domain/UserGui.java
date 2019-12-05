package pl.com.devmeet.devmeet.user.domain;

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

    public UserGui(UserService service) {
        this.service = service;
        userList = new ArrayList<>();
        userList = service.findAll();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        Notification.show("User", 2000, Notification.Position.MIDDLE);

        FormLayout filtersLayout = new FormLayout();
        textFieldEmail = new TextField("Email");
        header1 = new H1("devmeet app - user");
        filtersLayout.add(header1, textFieldEmail);

        userGrid = new Grid<>(UserDto.class);
        userGrid.removeColumnByKey("id");
        userGrid.removeColumnByKey("password");
        userGrid.removeColumnByKey("creationTime");
        userGrid.removeColumnByKey("loginTime");
        userGrid.removeColumnByKey("modificationTime");

        userGrid.addThemeVariants(
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES);
        refreshGrid(userList);

        add(filtersLayout, userGrid);
    }

    private void refreshGrid(List<UserDto> userList) {
        userGrid.setItems(userList);
        userGrid.getDataProvider().refreshAll();
    }
}
