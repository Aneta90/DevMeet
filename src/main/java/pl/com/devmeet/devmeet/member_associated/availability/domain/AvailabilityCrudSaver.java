package pl.com.devmeet.devmeet.member_associated.availability.domain;

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
class AvailabilityCrudSaver implements CrudEntitySaver<AvailabilityEntity, AvailabilityEntity> {

    private AvailabilityCrudRepository availabilityCrudRepository;
    private AvailabilityMemberFinder memberFinder;

    @Override
    public AvailabilityEntity saveEntity(AvailabilityEntity entity) throws MemberNotFoundException, UserNotFoundException {
        return availabilityCrudRepository
                .save(connectAvailabilityWithMember(entity));
    }

    private AvailabilityEntity connectAvailabilityWithMember(AvailabilityEntity availabilityEntity) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity memberEntity = availabilityEntity.getMember();
        if (memberEntity.getId() == null)
            memberEntity= memberFinder.findMember(MemberCrudFacade.map(availabilityEntity.getMember()));
        availabilityEntity.setMember(memberEntity);
        return availabilityEntity;
    }

}

