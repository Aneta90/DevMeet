package pl.com.devmeet.devmeet.member_associated.member;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 11.12.2019
 * Time: 22:50
 */

@ParentLayout(MemberView.class)
public class SideMenuBar extends Div implements RouterLayout {

    public SideMenuBar() {
        addMenuElement(GroupView.class, "Groups");
        addMenuElement(MemberView.class, "Members");
    }

    private void addMenuElement(
            Class<? extends Component> navigationTarget,
            String name) {
    }

}

@Route("groups")
class GroupView extends Div {

}

@Route("other-members")
class MembersView extends Div {

}