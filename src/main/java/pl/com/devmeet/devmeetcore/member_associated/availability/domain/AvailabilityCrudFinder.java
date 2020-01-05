package pl.com.devmeet.devmeetcore.member_associated.availability.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityCrudFinder implements CrudEntityFinder<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudRepository availabilityRepository;
    private AvailabilityMemberFinder memberFinder;

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) throws AvailabilityNotFoundException, MemberNotFoundException, UserNotFoundException {
        Optional<AvailabilityEntity> availability = findAvailability(dto);
        if (availability.isPresent())
            return availability.get();
        else
            throw new AvailabilityNotFoundException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
    }

    private MemberEntity findMemberEntity(MemberDto member) throws MemberNotFoundException, UserNotFoundException {
        return memberFinder.findMember(member);
    }

    private Optional<AvailabilityEntity> findAvailability(AvailabilityDto dto) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity member = findMemberEntity(dto.getMember());

        return availabilityRepository.findByMember(member);
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) throws AvailabilityNotFoundException, MemberNotFoundException, UserNotFoundException {
        Optional<List<AvailabilityEntity>> availabilityEntities = findAvailabilities(dto);

        if (availabilityEntities.isPresent())
            return availabilityEntities.get();
        else
            throw new AvailabilityNotFoundException(AvailabilityCrudInfoStatusEnum.AVAILABILITIES_NOT_FOUND.toString());
    }

    private Optional<List<AvailabilityEntity>> findAvailabilities(AvailabilityDto dto) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity member = findMemberEntity(dto.getMember());

        return availabilityRepository.findAllByMember(member);
    }


    @Override
    public boolean isExist(AvailabilityDto dto) {
        try {
            return findAvailability(dto).isPresent();
        } catch (MemberNotFoundException | UserNotFoundException e) {
            return false;
        }
    }
}
