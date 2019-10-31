package pl.com.devmeet.devmeet.user.domain;

import java.util.List;

public interface UserCrudInterface {

    UserDto create(UserDto dto, DefaultUserLoginTypeEnum defaultLoginType);

    UserEntity findEntity(UserDto dto);

    UserDto read(UserDto dto);

    List<UserDto> readAll();

    boolean isExist(UserDto dto);

    UserDto activation(UserDto dto);

    UserDto update(UserDto newDto, UserDto oldDto);

    boolean delete(UserDto dto);

    static UserDto map(UserEntity entity) {
        return UserMapper.toDto(entity);
    }

    static UserEntity map(UserDto dto) {
        return UserMapper.toEntity(dto);
    }
}
