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
class UserCrudDeleter {

    private UserCrudSaver userSaver;
    private UserCrudFinder userFinder;

    public UserEntity delete(UserDto dto) throws UserNotFoundException, UserFoundButNotActive {
        UserEntity found = userFinder.findEntityByEmail(dto);

        if (found.isActive()) {
            found.setActive(false);
            found.setModificationTime(DateTime.now());
            return saveUserEntity(found);
        }
        throw new UserFoundButNotActive(UserCrudStatusEnum.USER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private UserEntity saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
