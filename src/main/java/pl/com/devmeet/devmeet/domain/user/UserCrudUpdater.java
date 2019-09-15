package pl.com.devmeet.devmeet.domain.user;

import org.joda.time.DateTime;

class UserCrudUpdater {

    private UserRepository repository;
    private UserCrudFinder userFinder;

    private String userNotFoundMessage = "User not found";

    public UserCrudUpdater(UserRepository repository) {
        this.repository = repository;
        this.userFinder = new UserCrudFinder(repository);
    }

    public UserDto update(UserDto newDto, UserDto oldDto) {
        UserEntity found;

        if (oldDto != null && newDto != null) {
            try {
                found = userFinder.findEntity(oldDto);

                if (found.isActive())
                    return saveUserEntity(updateUser(newDto, found));

            } catch (IllegalArgumentException e) {
                userNotFound();
            }
        } else
            userNotFound();

        return null;
    }

    private UserEntity updateUser(UserDto updatedUser, UserEntity user) {
        DefaultUserLoginTypeEnum login = updatedUser.getLogin();
        String phone = updatedUser.getPhone();
        String email = updatedUser.getEmail();
        String password = updatedUser.getPassword();
        boolean modification = false;

        if (login != null) {
            user.setLogin(updatedUser.getLogin());
            modification = true;
        }
        if (phone != null && !phone.equals("")) {
            user.setPhone(updatedUser.getPhone());
            modification = true;
        }
        if (email != null && !email.equals("")) {
            user.setEmail(updatedUser.getEmail());
            modification = true;
        }
        if (password != null && !password.equals("")) {
            user.setPassword(updatedUser.getPassword());
            modification = true;
        }

        if (modification)
            user.setModificationTime(DateTime.now());

        return user;
    }

    private void userNotFound() {
        throw new IllegalArgumentException(userNotFoundMessage);
    }

    private UserDto saveUserEntity(UserEntity entity) {
        return UserCrudInterface.map(repository.save(entity));
    }
}
