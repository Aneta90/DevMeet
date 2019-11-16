package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class PlaceCrudSaver implements CrudEntitySaver<PlaceEntity, PlaceEntity> {

    private PlaceCrudRepository placeCrudRepository;
    private PlaceMemberFinder memberFinder;

    @Override
    public PlaceEntity saveEntity(PlaceEntity entity) throws MemberNotFoundException, UserNotFoundException {
        return placeCrudRepository
                .save(connectPlaceWithMember(entity));
    }

    private PlaceEntity connectPlaceWithMember(PlaceEntity placeEntity) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity memberEntity = placeEntity.getMember();
        if (memberEntity.getId() == null)
            memberEntity= memberFinder.findMember(MemberCrudFacade.map(placeEntity.getMember()));
        placeEntity.setMember(memberEntity);
        return placeEntity;
    }

}