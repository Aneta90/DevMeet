package pl.com.devmeet.devmeetcore.user.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserStatusEnum;

class UserCrudCreator {

    private UserCrudSaver userSaver;
    private UserCrudFinder userFinder;

    private String defaultLoginTypeErrMessage = "User default login type not defined";

    public UserCrudCreator(UserRepository repository) {
        this.userSaver = new UserCrudSaver(repository);
        this.userFinder = new UserCrudFinder(repository);
    }

    public UserDto create(UserDto dto, DefaultUserLoginTypeEnum defaultLoginType) {
        UserEntity user;
        boolean userActive;

        try {
            user = userFinder.findEntity(dto);

            userActive = user.isActive();

            if (!userActive && user.getModificationTime() != null) {
                user.setModificationTime(DateTime.now());
                user.setActive(true);

                return saveUserEntity(user);
            }

        } catch (IllegalArgumentException e) {
            user = createEntityWithUserLogin(dto, defaultLoginType);

            return saveUserEntity(user);
        }
        return null;
    }

    private UserEntity createEntityWithUserLogin(UserDto dto, DefaultUserLoginTypeEnum defaultLoginType) {
        UserEntity entity = UserCrudInterface.map(dto);
        String phoneLogin = entity.getPhone();
        String emailLogin = entity.getEmail();

        if (defaultLoginType != null) {

            if (phoneLogin != null && !phoneLogin.equals("") && emailLogin != null && !emailLogin.equals("")) {
                entity.setLogin(defaultLoginType);
                return setDefaultValuesToNewUserEntity(entity);
            }
        }
        throw new IllegalArgumentException(UserStatusEnum.USER_DEFAULT_LOGIN_TYPE_NOT_DEFINED.toString());
    }

    private UserEntity setDefaultValuesToNewUserEntity(UserEntity user) {
        user.setCreationTime(DateTime.now());
        user.setModificationTime(null);
        user.setActive(false);
        user.setLoggedIn(false);
        user.setLoginTime(null);

        return user;
    }

    private UserDto saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
