package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.*;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Builder
class MessengerCrudFinder implements CrudEntityFinder<MessengerDto, MessengerEntity> {

    private MessengerRepository messengerRepository;
    private MessengerGroupFinder groupFinder;
    private MessengerMemberFinder memberFinder;


    private MemberEntity findMember(MessengerDto dto) throws MemberNotFoundException, UserNotFoundException {
        return memberFinder.findMember(dto.getMember());
    }

    private GroupEntity findGroup(MessengerDto dto) throws GroupNotFoundException {
        return groupFinder.findGroup(dto.getGroup());
    }

    @Override
    public MessengerEntity findEntity(MessengerDto dto) throws MessengerNotFoundException {
        MemberEntity foundMember;
        GroupEntity foundGroup;

        try {
            foundMember = findMember(dto);
            return findMemberMessenger(foundMember);

        } catch (MemberNotFoundException | UserNotFoundException e) {
            try {
                foundGroup = findGroup(dto);
                return findGroupMessenger(foundGroup);

            } catch (GroupNotFoundException ex) {
                throw new MessengerNotFoundException(MessengerInfoStatusEnum.MESSENGER_NOT_FOUND.toString());
            }
        }
    }

    private MessengerEntity findMemberMessenger(MemberEntity memberEntity) throws MessengerNotFoundException {
        Optional<MessengerEntity> foundMessenger = messengerRepository.findByMember(memberEntity);

        if (foundMessenger.isPresent())
            return foundMessenger.get();

        throw new MessengerNotFoundException(MessengerInfoStatusEnum.MESSENGER_NOT_FOUND_BY_MEMBER.toString());
    }

    private MessengerEntity findGroupMessenger(GroupEntity groupEntity) throws MessengerNotFoundException {
        Optional<MessengerEntity> foundMessenger = messengerRepository.findByGroup(groupEntity);

        if (foundMessenger.isPresent())
            return foundMessenger.get();

        throw new MessengerNotFoundException(MessengerInfoStatusEnum.MESSENGER_NOT_FOUND_BY_GROUP.toString());
    }

    @Override
    public List<MessengerEntity> findEntities(MessengerDto dto) throws MessengerNotFoundException {
        GroupEntity foundGroup;

        try {
            foundGroup = findGroup(dto);
            return findAllGroupMembersMessengers(foundGroup);

        } catch (GroupNotFoundException e) {
            throw new MessengerNotFoundException(MessengerInfoStatusEnum.MESSENGERS_OF_MEMBERS_NOT_FOUND.toString());
        }
    }

    private List<MessengerEntity> findAllGroupMembersMessengers(GroupEntity groupEntity) throws MessengerNotFoundException {
        Optional<List<MessengerEntity>> foundMessengers = messengerRepository.findAllByGroup(groupEntity);

        if (foundMessengers.isPresent())
            return foundMessengers.get();

        throw new MessengerNotFoundException(MessengerInfoStatusEnum.MESSENGERS_OF_MEMBERS_NOT_FOUND.toString());
    }

    @Override
    public boolean isExist(MessengerDto dto) {
        try {
            return findEntity(dto) != null;
        } catch (MessengerNotFoundException e) {
            return false;
        }
    }
}