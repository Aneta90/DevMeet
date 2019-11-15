package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;


@Builder
@AllArgsConstructor
@NoArgsConstructor
class AvailabilityCrudSaver implements CrudEntitySaver<AvailabilityEntity, AvailabilityEntity> {

    private AvailabilityCrudRepository availabilityCrudRepository;
    private AvailabilityMemberFinder memberFinder;

    @Override
    public AvailabilityEntity saveEntity(AvailabilityEntity entity) throws EntityNotFoundException {
        return availabilityCrudRepository
                .save(connectAvailabilityWithMember(entity));
    }

    private AvailabilityEntity connectAvailabilityWithMember(AvailabilityEntity availabilityEntity) throws EntityNotFoundException {
        MemberEntity memberEntity = availabilityEntity.getMember();
        if (memberEntity.getId() == null)
            memberEntity= memberFinder.findMember(MemberCrudFacade.map(availabilityEntity.getMember()));
        availabilityEntity.setMember(memberEntity);
        return availabilityEntity;
    }

}

