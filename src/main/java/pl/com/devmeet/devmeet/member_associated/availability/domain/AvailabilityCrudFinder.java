package pl.com.devmeet.devmeet.member_associated.availability.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.group_associated.group.domain.status.GroupCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

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
                throw new IllegalArgumentException(GroupCrudInfoStatusEnum.GROUP_NOT_FOUND.toString());
        }

    private Optional<AvailabilityEntity> findAvailability(AvailabilityDto dto) {
        MemberDto member = dto.getMember();
        String nick = member.getNick();
        if (checkMemberEntity (member))
            return repository.findByMember(nick);
        return Optional.empty();
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) {
        Optional<List<AvailabilityEntity>> availabilities;
        MemberDto member= dto.getMember();
        if (checkMemberEntity(member)) {
            availabilities= repository.findAllByMember(member);

            if (availabilities.isPresent())
                return availabilities.get();
        }
    }

    @Override
    public boolean isExist(AvailabilityDto dto) {
        return false;
    }

    private boolean checkMemberEntity (MemberDto member){
        return member !=null && member.equals("");
    }
}
