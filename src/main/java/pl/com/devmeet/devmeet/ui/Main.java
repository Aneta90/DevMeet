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

        Tab tabHome = new Tab(imageLogo);
        Tab tabH2 = new Tab("Tab two");
        Tab tabUser = new Tab(new Icon(VaadinIcon.USER));

        Div page1 = new Div();
        page1.add("Home");
        Div page2 = new Div();
        page2.setText("Page#2");
        page2.setVisible(false);
        Div page3 = new Div();
        page3.setText("user");
        page3.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();

        tabsToPages.put(tabHome, page1);
        tabsToPages.put(tabH2, page2);
        tabsToPages.put(tabUser, page3);
        Tabs tabsHorizontal = new Tabs(tabHome, tabH2, tabUser);
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
