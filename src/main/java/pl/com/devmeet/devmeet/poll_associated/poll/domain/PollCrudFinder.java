package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status.PollCrudStatusEnum;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PollCrudFinder implements CrudEntityFinder<PollDto, PollEntity> {

    private PollCrudRepository pollCrudRepository;
    private PollGroupFinder groupFinder;

    @Override
    public PollEntity findEntity(PollDto dto) throws IllegalArgumentException, EntityNotFoundException {
        Optional<PollEntity> poll = findPoll(dto);

        if(poll.isPresent())
            return poll.get();

        throw new EntityNotFoundException(PollCrudStatusEnum.POLL_NOT_FOUND.toString());
    }

    private GroupEntity findGroupEntity(GroupDto group) throws EntityNotFoundException {
        return groupFinder.findGroup(group);
    }

    private Optional<PollEntity> findPoll(PollDto dto) throws EntityNotFoundException {
        GroupEntity group;
        group = findGroupEntity(dto.getGroup());

        return pollCrudRepository.findByGroupAndActive(group, dto.isActive());
    }

    private Optional<List<PollEntity>> findPolls(PollDto dto) throws EntityNotFoundException {
        GroupEntity group;
        group = findGroupEntity(dto.getGroup());

        return pollCrudRepository.findAllByGroup(group);
    }

    @Override
    public List<PollEntity> findEntities(PollDto dto) throws IllegalArgumentException, EntityNotFoundException {
        Optional<List<PollEntity>> polls = findPolls(dto);

        if (polls.isPresent())
            return polls.get();

        throw new EntityNotFoundException(PollCrudStatusEnum.POLLS_NOT_FOUND.toString());
    }

    @Override
    public boolean isExist(PollDto dto) {
        return false;
    }
}
