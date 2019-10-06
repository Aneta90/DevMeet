package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;

@AllArgsConstructor
class PlaceCrudCreator implements CrudEntityCreator<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver saver;
    private PlaceCrudFinder finder;

    public PlaceCrudCreator(PlaceCrudRepository repository) {
        this.saver = new PlaceCrudSaver(repository);
        this.finder = new PlaceCrudFinder(repository);

    }

    @Override
    public PlaceEntity createEntity(PlaceDto dto) {
        PlaceEntity place = null;
        boolean placeActivity;

        try {
            place = finder.findEntity(dto);
            placeActivity = place.isActive();

            if (!placeActivity && place.getModificationTime() != null)
                return setDefaultValuesWhenPlaceExists(saver.saveEntity(place));


        } catch (IllegalArgumentException e) {
            return setDefaultValuesWhenPlaceNotExists(place);
        }

        return null;
    }


    private PlaceEntity setDefaultValuesWhenPlaceNotExists(PlaceEntity place) {
        place.setCreationTime(DateTime.now());
        place.setActive(true);
        return place;
    }

    private PlaceEntity setDefaultValuesWhenPlaceExists(PlaceEntity entity) {
        entity.setModificationTime(DateTime.now());
        entity.setActive(true);
        return entity;
    }
}

