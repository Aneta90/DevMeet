package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 29.11.2019
 * Time: 23:02
 */

@RequiredArgsConstructor
class MessengerGroupFinder {

    @NonNull
    private GroupCrudFacade groupCrudFacade;

    public GroupEntity findGroup (GroupDto groupDto) throws GroupNotFoundException {
        return groupCrudFacade.findEntity(groupDto);
    }
}
