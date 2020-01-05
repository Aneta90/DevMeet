package pl.com.devmeet.devmeetcore.user.domain;

import lombok.AllArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;

@AllArgsConstructor
class UserCrudSaver implements CrudEntitySaver <UserDto, UserEntity> {

    private UserRepository repository;

    @Override
    public UserDto saveEntity(UserEntity entity) {
        return UserCrudInterface.map(repository.save(entity));
    }
}
