package pl.com.devmeet.devmeet.ui.normal_users.messenger_view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.com.devmeet.devmeet.ui.normal_users.DevMeetUserRouteClass;
import pl.com.devmeet.devmeet.ui.normal_users.MainViewNormalUser;

@Route(value = "messengerview", layout = MainViewNormalUser.class)
@PageTitle("Messenger")
@CssImport("styles/views/normal/messenger/messenger-view.css")
public class MessengerView  extends Div implements DevMeetUserRouteClass, AfterNavigationObserver {

    private final String divId = "messenger-view";

    public MessengerView() {
        setId(divId);

        Text text = new Text("Messenger view");
        add(text);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

    }
}
