package pl.com.devmeet.devmeet.ui.normal_users.group_view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.com.devmeet.devmeet.ui.normal_users.DevMeetUserRouteClass;
import pl.com.devmeet.devmeet.ui.normal_users.MainViewNormalUser;

@Route(value = "groupsview", layout = MainViewNormalUser.class)
@PageTitle("GroupsView")
@CssImport("styles/views/normal/groups/groups-view.css")
public class GroupsView extends Div implements DevMeetUserRouteClass, AfterNavigationObserver {

    private final String divId = "groups-view";

    public GroupsView() {
        setId(divId);

        Text text = new Text("Group view");
        add(text);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

    }
}
