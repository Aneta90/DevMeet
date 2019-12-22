package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.*;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
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

    @Override
    public MessengerEntity findEntity(MessengerDto dto) throws MessengerNotFoundException, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        MemberEntity foundMember;
        GroupEntity foundGroup;
        MemberDto memberDto = checkMemberIsNotNull(dto);
        GroupDto groupDto = checkGroupIsNotNull(dto);

        if (memberDto != null && groupDto == null) {
                foundMember = findMember(memberDto);
                return findMemberMessenger(foundMember);


        } else if (groupDto != null && memberDto == null) {
                foundGroup = findGroup(groupDto);
                return findGroupMessenger(foundGroup);

        }

        throw new MessengerNotFoundException(MessengerInfoStatusEnum.NOT_SPECIFIED_MEMBER_OR_GROUP.toString());
    }

    @Override
    public List<MessengerEntity> findEntities(MessengerDto dto) throws CrudException {
        return null;
    }

    public MemberDto checkMemberIsNotNull(MessengerDto messengerDto) {
        try {
            return messengerDto.getMember();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public GroupDto checkGroupIsNotNull(MessengerDto messengerDto) {
        try {
            return messengerDto.getGroup();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public MemberEntity findMember(MemberDto dto) throws MemberNotFoundException, UserNotFoundException {
        return memberFinder.findMember(dto);
    }

    public GroupEntity findGroup(GroupDto dto) throws GroupNotFoundException {
        return groupFinder.findGroup(dto);
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
    public boolean isExist(MessengerDto dto) {
        try {
            return findEntity(dto) != null;
        } catch (MessengerNotFoundException | MemberNotFoundException | UserNotFoundException | GroupNotFoundException e) {
            return false;
        }
    }
}