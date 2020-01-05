package pl.com.devmeet.devmeetcore.user.domain;

import java.util.List;

public interface UserCrudInterface {

    static UserDto map(UserEntity entity) {
        return UserMapper.toDto(entity);
    }

    static UserEntity map(UserDto dto) {
        return UserMapper.toEntity(dto);
    }
}
