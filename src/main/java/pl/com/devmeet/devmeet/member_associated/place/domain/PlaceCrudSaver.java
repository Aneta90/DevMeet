package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class PlaceCrudSaver implements CrudEntitySaver<PlaceEntity, PlaceEntity> {

    private PlaceCrudRepository placeCrudRepository;
    private PlaceMemberFinder memberFinder;

    @Override
    public PlaceEntity saveEntity(PlaceEntity entity) throws EntityNotFoundException {
        return placeCrudRepository
                .save(connectPlaceWithMember(entity));
    }

    private PlaceEntity connectPlaceWithMember(PlaceEntity placeEntity) throws EntityNotFoundException {
        MemberEntity memberEntity = placeEntity.getMember();
        if (memberEntity.getId() == null)
            memberEntity= memberFinder.findMember(MemberCrudFacade.map(placeEntity.getMember()));
        placeEntity.setMember(memberEntity);
        return placeEntity;
    }

}