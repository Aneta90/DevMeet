package pl.com.devmeet.devmeet.member_associated.place.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;

class PlaceCrudDeleter implements CrudEntityDeleter<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver placeCrudSaver;


    //czemu SAVER?

    public PlaceCrudDeleter(PlaceCrudRepository repository) {
        this.placeCrudSaver = new PlaceCrudSaver(repository);
    }

    public PlaceEntity deleteEntity(PlaceDto dto) {
        return null;
    }
}
