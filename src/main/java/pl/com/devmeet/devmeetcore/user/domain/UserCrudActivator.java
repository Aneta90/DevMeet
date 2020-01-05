package pl.com.devmeet.devmeetcore.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyActiveException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserCrudStatusEnum;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class UserCrudActivator {

    private UserCrudSaver userSaver;
    private UserCrudFinder userFinder;

    public UserEntity activate(UserDto dto) throws UserNotFoundException, UserAlreadyActiveException {
        UserEntity found = userFinder.find(dto);

        if (!found.isActive()) {
            found.setActive(true);
            found.setModificationTime(DateTime.now());

            return saveUserEntity(found);

        } else
            throw new UserAlreadyActiveException(UserCrudStatusEnum.USER_ALREADY_ACTIVE.toString());
    }

    private UserEntity saveUserEntity(UserEntity entity) {
        return userSaver.saveEntity(entity);
    }
}
