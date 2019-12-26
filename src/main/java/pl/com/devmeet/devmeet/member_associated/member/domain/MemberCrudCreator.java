package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeet.user.domain.UserEntity;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@Builder
@NoArgsConstructor
@AllArgsConstructor
class MemberCrudCreator implements CrudEntityCreator<MemberDto, MemberEntity> {

    private MemberCrudFinder memberFinder;
    private MemberCrudSaver saver;
    private MemberUserFinder memberUserFinder;
    private MemberMessengerCreator memberMessengerCreator;

    @Override
    public MemberEntity createEntity(MemberDto dto) throws MemberAlreadyExistsException, UserNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException, MessengerArgumentNotSpecified {
        MemberEntity memberEntity;

        try {
            memberEntity = memberFinder.findEntity(dto);

            if (!memberEntity.isActive())
                return saver.saveEntity(
                        setDefaultValuesIfMemberExistButNotActive(
                                mapToEntity(dto)));

        } catch (MemberNotFoundException e) {
            memberEntity = saver.saveEntity(
                    setDefaultValuesIfMemberNotExist(
                            connectMemberWithUser(dto))
            );
            createMessengerForMember(dto);

            return memberEntity;
        }

        throw new MemberAlreadyExistsException(MemberCrudStatusEnum.MEMBER_ALREADY_EXIST.toString());
    }

    private MemberEntity mapToEntity(MemberDto dto) {
        return MemberCrudFacade.map(dto);
    }

    private MemberEntity setDefaultValuesIfMemberNotExist(MemberEntity entity) {
        entity.setActive(true);
        entity.setCreationTime(DateTime.now());

        return entity;
    }

    private MemberEntity setDefaultValuesIfMemberExistButNotActive(MemberEntity entity) {
        entity.setActive(true);
        entity.setModificationTime(DateTime.now());

        return entity;
    }

    private MemberEntity connectMemberWithUser(MemberDto memberDto) throws UserNotFoundException {
        UserEntity foundUser = findUser(memberDto);
        MemberEntity memberEntity = mapToEntity(memberDto);

        memberEntity.setUser(foundUser);

        return memberEntity;
    }

    private UserEntity findUser(MemberDto memberDto) throws UserNotFoundException {
        return memberUserFinder.findUserEntity(memberDto.getUser());
    }

    private void createMessengerForMember(MemberDto memberDto) throws UserNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException, GroupNotFoundException, MessengerArgumentNotSpecified {
        memberMessengerCreator.createMessenger(memberDto);
    }
}