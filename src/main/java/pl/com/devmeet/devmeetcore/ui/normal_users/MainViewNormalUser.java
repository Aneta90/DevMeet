package pl.com.devmeet.devmeetcore.ui.normal_users;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import pl.com.devmeet.devmeetcore.ui.normal_users.group_view.GroupsView;
import pl.com.devmeet.devmeetcore.ui.normal_users.messenger_view.MessengerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route("normal")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainViewNormalUser extends AppLayout {

    private final Tabs menu;

    public MainViewNormalUser() {
        menu = createMenuTabs();
        addToNavbar(alignCenterNavbar(menu));
    }

    private static FlexLayout alignCenterNavbar(Tabs menu) {
        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setSizeFull();
        flexLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        flexLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        flexLayout.add(menu);
        return flexLayout;
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());

        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Groups", GroupsView.class));
        tabs.add(createTab("Messenger", MessengerView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab(getSelectedTab());
    }

    private String getSelectedTab() {
        return RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
    }

    private void selectTab(String target) {
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink
                    && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> {
            menu.setSelectedTab((Tab) tab);
        });
    }
}
