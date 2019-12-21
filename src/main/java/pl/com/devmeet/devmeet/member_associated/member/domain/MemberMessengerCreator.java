package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 21.12.2019
 * Time: 21:46
 */

@RequiredArgsConstructor
class MemberMessengerCreator {

    @NonNull
    private MessengerCrudFacade messengerCrudFacade;

    public MessengerDto createMessenger(MemberDto memberDto) throws UserNotFoundException, MessengerArgumentNotSpecified, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        return messengerCrudFacade.add(MessengerDto.builder()
                .member(memberDto)
                .build());
    }
}