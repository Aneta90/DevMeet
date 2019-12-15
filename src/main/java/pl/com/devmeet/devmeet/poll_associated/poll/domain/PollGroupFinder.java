package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PollGroupFinder {

    private GroupCrudFacade groupCrudFacade;

    public GroupEntity findGroup(GroupDto dto) throws GroupNotFoundException {
        return groupCrudFacade.findEntityByGroup(dto);
    }
}
