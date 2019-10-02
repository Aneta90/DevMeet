package pl.com.devmeet.devmeet.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

@AllArgsConstructor
class UserCrudSaver implements CrudEntitySaver <UserDto, UserEntity> {

    private UserRepository repository;

    @Override
    public UserDto saveEntity(UserEntity entity) {
        return UserCrudInterface.map(repository.save(entity));
    }
}
