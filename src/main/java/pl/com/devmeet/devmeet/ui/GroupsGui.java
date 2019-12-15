package pl.com.devmeet.devmeet.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;

import java.util.ArrayList;
import java.util.List;

@Route("groups")
class GroupsGui extends VerticalLayout {

    private GroupCrudFacade group;
    private List<GroupDto> groupList;

    // vaadin components
    private H1 header;
    private Grid<GroupDto> groupGrid;

    public GroupsGui(GroupCrudFacade group) {
        this.group = group;
        groupList = new ArrayList<>();
        groupList = group.findAll();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        Notification.show("Groups", 2000, Notification.Position.MIDDLE);

        header = new H1("devmeet app - groups");

        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems("active", "not active", "all");
        radioButtonGroup.setValue("all");

        groupGrid = new Grid<>(GroupDto.class);
        groupGrid.removeColumnByKey("creationTime");
        groupGrid.removeColumnByKey("website");
        groupGrid.removeColumnByKey("description");
        groupGrid.removeColumnByKey("modificationTime");
        groupGrid.removeColumnByKey("active");

        refreshGrid(groupList);

        add(header, radioButtonGroup, groupGrid);

    }

    private void refreshGrid(List<GroupDto> groupList) {
        groupGrid.setItems(groupList);
        groupGrid.getDataProvider().refreshAll();
    }
}
