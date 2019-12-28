package pl.com.devmeet.devmeet.ui.admins;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("admin")
@Theme(value = Lumo.class, variant = Lumo.DARK)
class MainViewAdmin extends VerticalLayout{


    MainViewAdmin() {

        showTabs();

    }

    private void showTabs() {
        //https://stackoverflow.com/questions/57553973/where-should-i-place-my-vaadin-10-static-files/
        Image imageLogo = new Image("img/logo.png", "logo");
        imageLogo.getStyle().set("borderRadius", "5%");

        Icon groupIcon = new Icon(VaadinIcon.GROUP);
        groupIcon.setSize("45px");
        Icon userIcon = new Icon(VaadinIcon.USER);
        userIcon.setSize("45px");
        Tab tabHome = new Tab(imageLogo);
        Tab tabGroup = new Tab(groupIcon);
        Tab tabUser = new Tab(userIcon);

        Div pageHome = new Div();
        pageHome.add("Home");
        Div pageGroups = new Div();
        pageGroups.setText("groups");
        pageGroups.setVisible(false);
        Div pageUsers = new Div();
        pageUsers.setText("users");
        pageUsers.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();

        tabsToPages.put(tabHome, pageHome);
        tabsToPages.put(tabGroup, pageGroups);
        tabsToPages.put(tabUser, pageUsers);
        Tabs tabsHorizontal = new Tabs(tabHome, tabGroup, tabUser);
        Div pages = new Div(pageHome, pageGroups, pageUsers);
        Set<Component> pagesShown = Stream.of(pageHome)
                .collect(Collectors.toSet());

        tabsHorizontal.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabsHorizontal.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);


            if (selectedPage.equals(pageGroups))
                UI.getCurrent().navigate("groups");

            if (selectedPage.equals(pageUsers))
                UI.getCurrent().navigate("users");


        });

        add(tabsHorizontal, pages);
    }
}
