package pl.com.devmeet.devmeetcore.ui.normal_users.messenger_view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import pl.com.devmeet.devmeetcore.ui.normal_users.MainViewNormalUser;

@Route(value = "messengerview", layout = MainViewNormalUser.class)
//@RouteAlias(value = "normal", layout = MainViewNormalUser.class)
@PageTitle("Messenger")
//@CssImport("styles/views/normal/messenger/messenger-view.css")
public class MessengerView extends Div implements AfterNavigationObserver {

    private final String divId = "messenger-view";

    private Text text;

    public MessengerView() {
        setId(divId);
        text = new Text("Messenger view constructor");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        text = new Text("Messenger view after select tab");
        add(text);
    }
}
