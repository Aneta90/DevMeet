package pl.com.devmeet.devmeetcore.ui.admins;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Route("groups")
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

        radioButtonGroup.addValueChangeListener(e -> {
            if (e.getValue().equals("active"))
                groupList = group.findAll().stream()
                        .filter(GroupDto::isActive)
                        .collect(Collectors.toList());
            else if (e.getValue().equals("not active"))
                groupList = group.findAll().stream()
                        .filter(groupDto -> !groupDto.isActive())
                        .collect(Collectors.toList());
            else groupList = group.findAll();
            refreshGrid(groupList);
        });

        add(header, radioButtonGroup, groupGrid);

    }

    private void refreshGrid(List<GroupDto> groupList) {
        groupGrid.setItems(groupList);
        groupGrid.getDataProvider().refreshAll();
    }
}
