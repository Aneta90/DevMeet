package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

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
    public AvailabilityEntity findEntity(AvailabilityDto dto) throws EntityNotFoundException {
        Optional<AvailabilityEntity> availability = findAvailability(dto);
        if (availability.isPresent())
            return availability.get();
        else
            throw new EntityNotFoundException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
    }

    private MemberEntity findMemberEntity(MemberDto member) throws EntityNotFoundException {
        return memberFinder.findMember(member);
    }

    private Optional<AvailabilityEntity> findAvailability(AvailabilityDto dto) throws EntityNotFoundException {
        MemberEntity member;
        try {
            member = findMemberEntity(dto.getMember());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_MEMBER_NOT_FOUND.toString());
        }
        return availabilityRepository.findByMember(member);
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) throws EntityNotFoundException {
        Optional<List<AvailabilityEntity>> availabilityEntities = findAvailabilities(dto);

        if(availabilityEntities.isPresent())
            return availabilityEntities.get();
        else
            throw new EntityNotFoundException(AvailabilityCrudInfoStatusEnum.AVAILABILITIES_NOT_FOUND.toString());
    }

    private Optional<List<AvailabilityEntity>> findAvailabilities(AvailabilityDto dto) throws EntityNotFoundException {
        MemberEntity member;
        try {
            member = findMemberEntity(dto.getMember());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_MEMBER_NOT_FOUND.toString());
        }
        return availabilityRepository.findAllByMember(member);
    }


    @Override
    public boolean isExist(AvailabilityDto dto) {
        try {
            return findAvailability(dto).isPresent();
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

}
