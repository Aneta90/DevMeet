package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class MemberCrudDeactivator {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;
    private MemberMessengerDeactivator memberMessengerDeactivator;

    public MemberEntity delete(MemberDto memberDto) throws MemberFoundButNotActiveException, MemberNotFoundException, UserNotFoundException, MessengerAlreadyExistsException, MessengerNotFoundException, GroupNotFoundException {
        MemberEntity memberEntity = findMember(memberDto);

        if (memberEntity.isActive())
            return saveMember(
                    setDefaultValuesWhenMemberIsDelete(memberEntity));

        throw new MemberFoundButNotActiveException(MemberCrudStatusEnum.MEMBER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private MemberEntity setDefaultValuesWhenMemberIsDelete(MemberEntity memberEntity) throws UserNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException, MessengerNotFoundException, GroupNotFoundException {
        memberEntity.setActive(false);
        memberEntity.setModificationTime(DateTime.now());

        deactivateMessenger(memberEntity);

        return memberEntity;
    }

    private MemberEntity findMember(MemberDto dto) throws MemberNotFoundException, UserNotFoundException {
        return memberCrudFinder.findEntity(dto);
    }

    private MessengerDto deactivateMessenger(MemberEntity memberEntity) throws UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, GroupNotFoundException, MessengerAlreadyExistsException {
        return memberMessengerDeactivator.deactivateMessenger(memberEntity);
    }

    private MemberEntity saveMember(MemberEntity entity) {
        return memberCrudSaver.saveEntity(entity);
    }
}