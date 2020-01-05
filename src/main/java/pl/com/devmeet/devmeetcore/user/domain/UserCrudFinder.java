package pl.com.devmeet.devmeetcore.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserCrudStatusEnum;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class UserCrudFinder {

    private UserRepository repository;

    public UserEntity findByEmail(String email) throws UserNotFoundException {
        Optional<UserEntity> foundUser = Optional.empty();
        if (!email.isEmpty())
            foundUser = repository.findByEmail(email);

        if (foundUser.isPresent())
            return foundUser.get();

        throw new UserNotFoundException(UserCrudStatusEnum.USER_NOT_FOUND.toString());
    }

    public UserEntity find(UserDto dto) throws UserNotFoundException {
        String email = "";

        if (dto != null)
            email = dto.getEmail();

        return findByEmail(email);
    }

    public List<UserEntity> findAllEntities() {
        return repository.findAll();
    }

    @Deprecated
    public boolean isExist(UserDto dto) {
        try {
            return find(dto) != null;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}
