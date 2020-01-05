package pl.com.devmeet.devmeetcore.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.status_and_exceptions.PlaceAlreadyExistsException;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.status_and_exceptions.PlaceNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PlaceCrudCreator implements CrudEntityCreator<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver placeCrudSaver;
    private PlaceCrudFinder placeCrudFinder;
    private MemberCrudFacade memberCrudFacade;

    @Override
    public PlaceEntity createEntity(PlaceDto dto) throws MemberNotFoundException, UserNotFoundException, PlaceAlreadyExistsException {
        PlaceEntity place;
        try {
            place = placeCrudFinder.findEntity(dto);

            if (!place.isActive() && place.getModificationTime() != null)
                return placeCrudSaver.saveEntity(setDefaultValuesWhenPlaceExists(place));

        } catch (PlaceNotFoundException e) {
            place = setDefaultValuesWhenPlaceNotExists(PlaceCrudFacade.map(dto));
            return placeCrudSaver.saveEntity(place);
        }

        throw new PlaceAlreadyExistsException(PlaceCrudStatusEnum.PLACE_ALREADY_EXISTS.toString());
    }

    private PlaceEntity setDefaultValuesWhenPlaceNotExists(PlaceEntity place) {
        place.setCreationTime(DateTime.now());
        place.setActive(true);
        return place;
    }

    private PlaceEntity setDefaultValuesWhenPlaceExists(PlaceEntity place) {
        place.setModificationTime(DateTime.now());
        place.setActive(true);
        return place;

    }
}
