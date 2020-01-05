package pl.com.devmeet.devmeetcore.ui.normal_users.group_view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.com.devmeet.devmeetcore.ui.normal_users.MainViewNormalUser;

@Route(value = "groupsview", layout = MainViewNormalUser.class)
@PageTitle("GroupsView")
//@CssImport("styles/views/normal/groups/groups-view.css")
public class GroupsView extends Div implements AfterNavigationObserver {

    private final String divId = "groups-view";

    Text text;

    public GroupsView() {
        setId(divId);
        text = new Text("Group view constructor");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        text = new Text("Group view after select tab");
        add(text);
    }
}
