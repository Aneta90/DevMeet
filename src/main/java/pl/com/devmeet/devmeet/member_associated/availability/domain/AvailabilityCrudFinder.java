package pl.com.devmeet.devmeet.member_associated.availability.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;

import java.util.List;
import java.util.Optional;

class AvailabilityCrudFinder implements CrudEntityFinder<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudRepository repository;

    public AvailabilityCrudFinder(AvailabilityCrudRepository repository) {
        this.availabilityCrudSaver = new AvailabilityCrudSaver(repository);
        this.repository = repository;
    }

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) {
        Optional<AvailabilityEntity> availability = findAvailability(dto);
        if (availability.isPresent())
            return availability.get();
        else
            throw new IllegalArgumentException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
    }

    private Optional<AvailabilityEntity> findAvailability(AvailabilityDto dto) {
        MemberDto member = dto.getMember();
        String nick = member.getNick();
        if (checkMember(member))
            return repository.findByMemberNick(nick);
        return Optional.empty();
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) {
        Optional<List<AvailabilityEntity>> availabilities;
        MemberDto member = dto.getMember();
        String nick = member.getNick();
        if (checkMember(member)){
            availabilities= repository.findAllByMemberNick(nick);
            if (availabilities.isPresent())
                return availabilities.get();
        }
        throw new IllegalArgumentException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
    }

    @Override
    public boolean isExist(AvailabilityDto dto) {
        return findAvailability(dto).isPresent();
    }

    private boolean checkMember(MemberDto member) {
        return member != null && !member.equals("");
    }
}
