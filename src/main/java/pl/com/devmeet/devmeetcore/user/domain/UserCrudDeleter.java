package pl.com.devmeet.devmeetcore.user.domain;

import org.joda.time.DateTime;

class UserCrudDeleter {

    private UserCrudSaver userSaver;
    private UserCrudFinder userFinder;

    private String userNotFoundMessage = "User not found";

    public UserCrudDeleter(UserRepository repository) {
        this.userSaver = new UserCrudSaver(repository);
        this.userFinder = new UserCrudFinder(repository);
    }

    public boolean delete(UserDto dto) {
        UserEntity found;

        try {
            found = userFinder.findEntity(dto);

            if (found.isActive()) {
                found.setActive(false);
                found.setModificationTime(DateTime.now());

                return saveUserEntity(found) != null;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    private UserDto saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
