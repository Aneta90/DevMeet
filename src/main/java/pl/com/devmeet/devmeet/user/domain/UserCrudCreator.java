package pl.com.devmeet.devmeet.user.domain;

import org.joda.time.DateTime;

class UserCrudCreator {

    private UserRepository repository;
    private UserCrudFinder userFinder;

    private String defaultLoginTypeErrMessage = "User default login type not defined";

    public UserCrudCreator(UserRepository repository) {
        this.repository = repository;
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
        throw new IllegalArgumentException(defaultLoginTypeErrMessage);
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
        return UserCrudInterface.map(repository.save(entity));
    }
}
