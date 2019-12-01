package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.*;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 01.12.2019
 * Time: 13:49
 */

@RequiredArgsConstructor
class MessengerMemberOrGroupConnector {

    @NonNull
    private MessengerCrudFinder messengerCrudFinder;

    public MessengerEntity connectWithMessenger(MessengerEntity messengerEntity, MessengerDto messengerDto) throws MessengerArgumentNotSpecified, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        MemberDto memberDto = checkMemberIsNotNull(messengerDto);
        GroupDto groupDto = checkGroupIsNotNull(messengerDto);

        if (memberDto != null && groupDto == null) {
            messengerEntity.setMember(
                    findMember(memberDto));

        } else if (groupDto != null && memberDto == null) {
            messengerEntity.setGroup(
                    findGroup(groupDto));

        } else
            throw new MessengerArgumentNotSpecified(MessengerInfoStatusEnum.NOT_SPECIFIED_MEMBER_OR_GROUP.toString());

        return messengerEntity;
    }

    private MemberDto checkMemberIsNotNull(MessengerDto messengerDto) {
        return messengerCrudFinder.checkMemberIsNotNull(messengerDto);
    }

    private GroupDto checkGroupIsNotNull(MessengerDto messengerDto) {
        return messengerCrudFinder.checkGroupIsNotNull(messengerDto);
    }

    private MemberEntity findMember(MemberDto member) throws MemberNotFoundException, UserNotFoundException {
        return messengerCrudFinder.findMember(member);
    }

    private GroupEntity findGroup(GroupDto group) throws GroupNotFoundException {
        return messengerCrudFinder.findGroup(group);
    }
}
