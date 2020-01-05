package pl.com.devmeet.devmeetcore.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyExistsException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserCrudStatusEnum;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class UserCrudCreator {

    private UserCrudSaver userSaver;
    private UserCrudFinder userFinder;

    public UserEntity create(UserDto dto) throws UserAlreadyExistsException {
        UserEntity user;
        boolean userActive;

        try {
            user = userFinder.find(dto);

            userActive = user.isActive();

            if (!userActive && user.getModificationTime() != null) {
                user.setModificationTime(DateTime.now());
                user.setActive(true);

                return saveUserEntity(user);
            }

        } catch (UserNotFoundException e) {
            return saveUserEntity(setDefaultValuesToNewUserEntity(UserCrudFacade.map(dto)));
        }
        throw new UserAlreadyExistsException(UserCrudStatusEnum.USER_ALREADY_EXISTS.toString());
    }

//    private UserEntity createEntityWithUserLogin(UserDto dto, DefaultUserLoginTypeEnum defaultLoginType) {
//        UserEntity entity = UserCrudInterface.map(dto);
//        String phoneLogin = entity.getPhone();
//        String emailLogin = entity.getEmail();
//
//        if (defaultLoginType != null) {
//
//            if (phoneLogin != null && !phoneLogin.equals("") && emailLogin != null && !emailLogin.equals("")) {
//                entity.setLogin(defaultLoginType);
//                return setDefaultValuesToNewUserEntity(entity);
//            }
//        }
//        throw new IllegalArgumentException(UserStatusEnum.USER_DEFAULT_LOGIN_TYPE_NOT_DEFINED.toString());
//    }

    private UserEntity setDefaultValuesToNewUserEntity(UserEntity user) {
        user.setCreationTime(DateTime.now());
        user.setModificationTime(null);
        user.setActive(false);
//        user.setLoggedIn(false);
//        user.setLoginTime(null);

        return user;
    }

    private UserEntity saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
