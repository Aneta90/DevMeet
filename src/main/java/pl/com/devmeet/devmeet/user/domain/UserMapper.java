package pl.com.devmeet.devmeet.user.domain;

class UserMapper {

    static UserDto map(UserEntity entity) {
        return new UserDto().builder()
                .login(entity.getLogin())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .creationTime(entity.getCreationTime())
                .modificationTime(entity.getModificationTime())
                .isActive(entity.isActive())
                .loggedIn(entity.isLoggedIn())
                .loginTime(entity.getLoginTime())
                .build();
    }

    static UserEntity map(UserDto dto) {
        return new UserEntity().builder()
                .login(dto.getLogin())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .loggedIn(dto.isLoggedIn())
                .loginTime(dto.getLoginTime())
                .build();
    }
}
