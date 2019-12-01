package pl.com.devmeet.devmeet.test_utils;

import pl.com.devmeet.devmeet.user.domain.DefaultUserLoginTypeEnum;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.registration.UserRegistration;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 30.11.2019
 * Time: 12:46
 */
class TestSecondUserInitiator implements TestObjectInitiator<UserRepository, UserCrudFacade, UserDto> {

    private UserRepository repository;
    private UserDto testUserDto;

    public TestSecondUserInitiator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserCrudFacade initFacade() {
        return new UserCrudFacade(repository);
    }

    @Override
    public UserDto initAndSaveTestObject() {
        UserCrudFacade userCrudFacade = initFacade();
        userCrudFacade.create(testUserDto, DefaultUserLoginTypeEnum.EMAIL);
        return userCrudFacade.activation(testUserDto);
    }
}
