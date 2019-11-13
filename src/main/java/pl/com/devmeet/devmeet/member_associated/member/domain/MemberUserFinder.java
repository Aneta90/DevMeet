package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.UserEntity;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 12.11.2019
 * Time: 21:30
 */

@RequiredArgsConstructor
class MemberUserFinder {

    @NonNull
    private UserCrudFacade userCrudFacade;

    public UserEntity findUserEntity(UserDto dto) throws EntityNotFoundException {
        UserEntity userEntity;

        try {
            userEntity = userCrudFacade.findEntity(dto);
            if(userEntity != null)
                return userEntity;
        }catch (IllegalArgumentException e){
            throw new EntityNotFoundException(MemberCrudStatusEnum.MEMBER_USER_NOT_FOUND.toString());
        }

        throw new EntityNotFoundException(MemberCrudStatusEnum.MEMBER_USER_NOT_FOUND.toString());
    }
}
