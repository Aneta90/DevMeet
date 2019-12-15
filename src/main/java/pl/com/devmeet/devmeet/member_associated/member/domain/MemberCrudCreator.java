package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserEntity;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@RequiredArgsConstructor
class MemberCrudCreator implements CrudEntityCreator<MemberDto, MemberEntity> {

    @NonNull
    private MemberCrudFinder memberFinder;
    @NonNull
    private MemberCrudSaver saver;

    @Override
    public MemberEntity createEntity(MemberDto dto) throws MemberAlreadyExistsException, UserNotFoundException {
        MemberEntity memberEntity;

        try {
            memberEntity = memberFinder.findEntity(dto);

            if (!memberEntity.isActive())
                return saver.saveEntity(
                        setDefaultValuesIfMemberExistButNotActive(
                        mapToEntity(dto)));

        } catch (MemberNotFoundException e) {
            return saver.saveEntity(
                    setDefaultValuesIfMemberNotExist(
                    connectMemberWithUser(dto)));
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

    private MemberEntity setDefaultValuesIfMemberExistButNotActive(MemberEntity entity){
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
        return memberFinder.getUserFinder()
                .findUserEntity(memberDto.getUser());
    }
}