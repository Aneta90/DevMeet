package pl.com.devmeet.devmeetcore.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class UserCrudSaver implements CrudEntitySaver <UserEntity, UserEntity> {

    private UserRepository repository;

    @Override
    public UserEntity saveEntity(UserEntity entity) {
        return repository.save(entity);
    }
}
