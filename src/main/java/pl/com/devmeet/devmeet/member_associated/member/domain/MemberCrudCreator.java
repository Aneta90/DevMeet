package pl.com.devmeet.devmeet.member_associated.member.domain;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberCrudCreator implements CrudEntityCreator<MemberDto, MemberEntity> {

    @NonNull
    private MemberCrudFinder memberFinder;
    @NonNull
    private MemberCrudSaver saver;

    @Override
    public MemberEntity createEntity(MemberDto dto) throws IllegalArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
//        Optional<MemberEntity>

        return null;
    }

    private UserEntity findUser(UserDto dto) throws EntityNotFoundException {
        return memberFinder.getUserFinder().findUser(dto);
    }

    private MemberEntity connectMemberWithUser (MemberEntity entity) throws EntityNotFoundException {
        return null;
    }
}