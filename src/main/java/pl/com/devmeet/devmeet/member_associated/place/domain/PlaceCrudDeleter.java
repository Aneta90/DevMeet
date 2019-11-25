package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceFoundButNotActiveException;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PlaceCrudDeleter implements CrudEntityDeleter<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver placeCrudSaver;
    private PlaceCrudFinder placeCrudFinder;

    @Override
    public PlaceEntity deleteEntity(PlaceDto dto) throws PlaceFoundButNotActiveException, MemberNotFoundException, PlaceNotFoundException, UserNotFoundException {
        PlaceEntity place = placeCrudFinder.findEntity(dto);
        boolean placeActivity = place.isActive();

        if (placeActivity) {
            place.setActive(false);
            place.setModificationTime(DateTime.now());

            return placeCrudSaver.saveEntity(place);
        }
        throw new PlaceFoundButNotActiveException(PlaceCrudStatusEnum.PLACE_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
