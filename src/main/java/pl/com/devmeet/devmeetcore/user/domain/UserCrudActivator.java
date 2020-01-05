package pl.com.devmeet.devmeetcore.user.domain;

import org.joda.time.DateTime;

class UserCrudActivator {

    private UserCrudSaver userSaver;
    private UserCrudFinder userFinder;

    private String userAlreadyActivatedMessage = "User has been activated";

    public UserCrudActivator(UserRepository repository) {
        this.userSaver = new UserCrudSaver(repository);
        this.userFinder = new UserCrudFinder(repository);
    }

    public UserDto activate(UserDto dto) {
        UserEntity found;

        found = userFinder.findEntity(dto);

        if (!found.isActive()) {
            found.setActive(true);
            found.setModificationTime(DateTime.now());

            return saveUserEntity(found);

        } else
            throw new IllegalArgumentException(userAlreadyActivatedMessage);
    }

    private UserDto saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
