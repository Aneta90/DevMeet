package pl.com.devmeet.devmeet.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route
class Main extends VerticalLayout {


    public Main() {

        Image imageLogo = new Image("https://dummyimage.com/36x36/000/fff", "logo");
        imageLogo.getStyle().set("borderRadius", "30%");

        Icon groupIcon = new Icon(VaadinIcon.GROUP);
        groupIcon.setSize("45px");
        Icon userIcon = new Icon(VaadinIcon.USER);
        userIcon.setSize("45px");
        Tab tabHome = new Tab(imageLogo);
        Tab tabGroup = new Tab(groupIcon);
        Tab tabUser = new Tab(userIcon);

        Div page1 = new Div();
        page1.add("Home");
        Div page2 = new Div();
        page2.setText("groups");
        page2.setVisible(false);
        Div page3 = new Div();
        page3.setText("users");
        page3.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();

        tabsToPages.put(tabHome, page1);
        tabsToPages.put(tabGroup, page2);
        tabsToPages.put(tabUser, page3);
        Tabs tabsHorizontal = new Tabs(tabHome, tabGroup, tabUser);
        Div pages = new Div(page1, page2, page3);
        Set<Component> pagesShown = Stream.of(page1)
                .collect(Collectors.toSet());

        tabsHorizontal.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabsHorizontal.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        add(tabsHorizontal, pages);

    }
}
