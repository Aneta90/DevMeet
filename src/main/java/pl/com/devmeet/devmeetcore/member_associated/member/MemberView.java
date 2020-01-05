package pl.com.devmeet.devmeetcore.member_associated.member;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@Route(value = "member")
public class MemberView extends VerticalLayout implements RouterLayout {

    public MemberView() {
        add("hello member");


    }
}
