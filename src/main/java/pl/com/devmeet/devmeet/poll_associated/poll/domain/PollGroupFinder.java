package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status.PollCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PollGroupFinder {

    private GroupCrudFacade groupCrudFacade;

    public GroupEntity findGroup(GroupDto dto) throws EntityNotFoundException {
        try {
            return groupCrudFacade.findEntity(dto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(PollCrudStatusEnum.POLL_GROUP_NOT_FOUND.toString());
        }
    }
}
