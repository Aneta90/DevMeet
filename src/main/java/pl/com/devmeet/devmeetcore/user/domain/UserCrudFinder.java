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

    public UserEntity findEntityByEmail(UserDto dto) throws UserNotFoundException {
        Optional<UserEntity> foundUser = Optional.empty();
        if (dto != null) {
            String email = dto.getEmail();

            if (!email.isEmpty())
                foundUser = repository.findByEmailAndPassword(email, dto.getPassword());

            if (foundUser.isPresent())
                return foundUser.get();
        }
        throw new UserNotFoundException(UserCrudStatusEnum.USER_NOT_FOUND.toString());
    }

    public List<UserEntity> findAllEntities(){
        return repository.findAll();
    }

    @Deprecated
    public boolean isExist(UserDto dto) {
        try {
            return findEntityByEmail(dto) != null;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}
