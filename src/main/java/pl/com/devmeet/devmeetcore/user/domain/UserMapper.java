package pl.com.devmeet.devmeetcore.user.domain;

class UserMapper {

    static UserDto toDto(UserEntity entity) {
        return new UserDto().builder()
                .id(entity.getId())
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

    static UserEntity toEntity(UserDto dto) {
        return new UserEntity().builder()
                .id(dto.getId())
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
