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

    public UserEntity update(UserDto newDto, UserDto oldDto) throws UserNotFoundException, UserFoundButNotActive {
        UserEntity found = userFinder.findEntityByEmail(oldDto);

        if (found.isActive())
            return saveUserEntity(updateUser(newDto, found));

        throw new UserFoundButNotActive(UserCrudStatusEnum.USER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private UserEntity updateUser(UserDto updatedUser, UserEntity user) {
        String email = updatedUser.getEmail();
        String password = updatedUser.getPassword();
        boolean modification = false;

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

    private UserEntity saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
