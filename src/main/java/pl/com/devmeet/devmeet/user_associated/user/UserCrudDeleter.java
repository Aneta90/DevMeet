package pl.com.devmeet.devmeet.user_associated.user;

import org.joda.time.DateTime;

class UserCrudDeleter {

    private UserRepository repository;
    private UserCrudFinder userFinder;

    private String userNotFoundMessage = "User not found";

    public UserCrudDeleter(UserRepository repository) {
        this.repository = repository;
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
        return UserCrudInterface.map(repository.save(entity));
    }
}
