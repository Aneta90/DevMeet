package pl.com.devmeet.devmeet.user.domain;

public interface UserCrudInterface {

    UserDto create(UserDto dto, DefaultUserLoginTypeEnum defaultLoginType);

    UserEntity findEntity(UserDto dto);

    UserDto read(UserDto dto);

    boolean isExist(UserDto dto);

    UserDto activation(UserDto dto);

    UserDto update(UserDto newDto, UserDto oldDto);

    boolean delete(UserDto dto);

    static UserDto map(UserEntity entity) {
        return UserMapper.map(entity);
    }

    static UserEntity map(UserDto dto) {
        return UserMapper.map(dto);
    }
}
