package pl.com.devmeet.devmeet.member_associated.place.domain;

public class PlaceCrudUpdater {

    private PlaceCrudRepository repository;

    public PlaceCrudUpdater(PlaceCrudRepository repository) {
        this.repository=repository;
    }


    public PlaceEntity updateEntity(PlaceDto oldDto, PlaceDto newDto) {
        return null;
    }
}
