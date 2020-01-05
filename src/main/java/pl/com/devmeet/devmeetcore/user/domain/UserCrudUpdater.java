package pl.com.devmeet.devmeetcore.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserCrudStatusEnum;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserFoundButNotActive;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class UserCrudUpdater {

    private UserCrudSaver userSaver;
    private UserCrudFinder userFinder;

    public UserEntity updateEmail(UserDto oldDto, String email) throws UserNotFoundException {
        UserEntity found = userFinder.find(oldDto);

        if (!email.isEmpty()) {
            found.setEmail(email);
            found.setModificationTime(DateTime.now());
        }
        return saveUserEntity(found);
    }

    public UserEntity updatePassword(UserDto oldDto, String password) throws UserNotFoundException {
        UserEntity found = userFinder.find(oldDto);

        if (!password.isEmpty()) {
            found.setPassword(password);
            found.setModificationTime(DateTime.now());
        }
        return saveUserEntity(found);
    }

    public UserEntity update(UserDto newDto, UserDto oldDto) throws UserNotFoundException, UserFoundButNotActive {
        UserEntity found = userFinder.find(oldDto);

        if (found.isActive())
            return saveUserEntity(updateAllowedParameters(newDto, found));

        throw new UserFoundButNotActive(UserCrudStatusEnum.USER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private UserEntity updateAllowedParameters(UserDto updatedUser, UserEntity user) {
        String email = updatedUser.getEmail();
        String password = updatedUser.getPassword();
        boolean modification = false;

        if (email.isEmpty()) {
            user.setEmail(updatedUser.getEmail());
            modification = true;
        }
        if (!password.isEmpty()) {
            user.setPassword(updatedUser.getPassword());
            modification = true;
        }

        if (modification)
            user.setModificationTime(DateTime.now());

        return user;
    }

    private UserEntity saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
