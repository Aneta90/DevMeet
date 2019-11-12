package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PlaceCrudDeleter implements CrudEntityDeleter<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver placeCrudSaver;
    private PlaceCrudFinder placeCrudFinder;

    @Override
    public PlaceEntity deleteEntity(PlaceDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        PlaceEntity place = placeCrudFinder.findEntity(dto);
        boolean placeActivity = place.isActive();

        if (placeActivity) {
            place.setActive(false);
            place.setModificationTime(DateTime.now());

            return placeCrudSaver.saveEntity(place);
        }
        throw new EntityAlreadyExistsException(PlaceCrudStatusEnum.PLACE_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
