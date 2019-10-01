package pl.com.devmeet.devmeet.user.domain;

import java.util.Optional;

class UserCrudFinder {

    private UserRepository repository;

    private String userNotFoundMessage = "User not found";

    public UserCrudFinder(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity findEntity(UserDto dto) {
        Optional<UserEntity> foundUser = Optional.empty();
        String phone = dto.getPhone();
        String email = dto.getEmail();

        if (phone != null && !phone.equals(""))
            foundUser = repository.findByPhoneAndPassword(phone, dto.getPassword());
        else if (email != null && !phone.equals(""))
            foundUser = repository.findByEmailAndPassword(email, dto.getPassword());

        if (foundUser.isPresent())
            return foundUser.get();
        else
            throw new IllegalArgumentException(userNotFoundMessage);
    }

    public UserDto read(UserDto dto) {
        return getUserDtoFromEntity(findEntity(dto));
    }

    public boolean isExist(UserDto dto) {
        try {
            return findEntity(dto) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private UserDto getUserDtoFromEntity(UserEntity entity) {
        return UserCrudInterface.map(entity);
    }
}
