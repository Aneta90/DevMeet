package pl.com.devmeet.devmeet.member_associated.place.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import java.util.List;

class PlaceCrudFinder implements CrudEntityFinder<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver placeCrudSaver;

    public PlaceCrudFinder(PlaceCrudRepository repository) {
        this.placeCrudSaver= new PlaceCrudSaver(repository);
    }

    @Override
    public PlaceEntity findEntity(PlaceDto dto) {
        return null;
    }

    @Override
    public List<PlaceEntity> findEntities (PlaceDto dto) {
        return null;
    }

    @Override
    public boolean isExist(PlaceDto dto) {
        return false;
    }
}
