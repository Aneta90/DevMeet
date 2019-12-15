package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 21:01
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessengerFinder {

    private MessengerRepository messengerRepository;
    private UserRepository userRepository;
    private MemberRepository memberRepository;
    private GroupCrudRepository groupCrudRepository;

    public MessengerEntity findMessenger(MessengerDto messengerDto) throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException {
        return initFacade().findEntity(messengerDto);
    }

    private MessengerCrudFacade initFacade() {
        return new MessengerCrudFacade(messengerRepository, userRepository, memberRepository, groupCrudRepository);
    }
}
