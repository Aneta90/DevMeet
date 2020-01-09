package pl.com.devmeet.devmeetcore.ui.admins;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceDto;

import java.util.ArrayList;
import java.util.List;

@Route("admin/places")
class PlacesGui extends VerticalLayout {

    private PlaceCrudFacade place;
    private List<PlaceDto> placeList;

    // vaadin components
    private H1 header;
    private Grid<PlaceDto> placeGrid;

    public PlacesGui(PlaceCrudFacade place) {
        this.place = place;
        placeList = new ArrayList<>();
        placeList = place.findAll();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        Notification.show("Places", 2000, Notification.Position.MIDDLE);

        header = new H1("devmeet app - places");

        placeGrid = new Grid<>(PlaceDto.class);
        placeGrid.removeColumnByKey("creationTime");
        placeGrid.removeColumnByKey("modificationTime");
        placeGrid.removeColumnByKey("active");
        //todo - wyrugować z PlaceDto typy złożone inaczej będzie ciężko cokolwiek pokazać.
        placeGrid.removeColumnByKey("memberId");

        refreshGrid(placeList);

        add(header, placeGrid);

    }

    private void refreshGrid(List<PlaceDto> placeList) {
        placeGrid.setItems(placeList);
        placeGrid.getDataProvider().refreshAll();
    }
}
