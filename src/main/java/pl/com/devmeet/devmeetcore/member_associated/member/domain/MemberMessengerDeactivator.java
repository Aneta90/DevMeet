package pl.com.devmeet.devmeetcore.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 22.12.2019
 * Time: 22:05
 */

@RequiredArgsConstructor
class MemberMessengerDeactivator {

    @NonNull
    private MessengerCrudFacade messengerCrudFacade;

    public MessengerDto deactivateMessenger(MemberEntity memberEntity) throws UserNotFoundException, GroupNotFoundException, MessengerAlreadyExistsException, MessengerNotFoundException, MemberNotFoundException {
        return messengerCrudFacade.deactivateMembersMessenger(MemberCrudFacade.map(memberEntity));
    }
}
