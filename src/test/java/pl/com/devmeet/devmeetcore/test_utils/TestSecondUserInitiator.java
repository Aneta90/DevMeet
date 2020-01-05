package pl.com.devmeet.devmeetcore.test_utils;

import pl.com.devmeet.devmeetcore.user.domain.DefaultUserLoginTypeEnum;
import pl.com.devmeet.devmeetcore.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeetcore.user.domain.UserDto;
import pl.com.devmeet.devmeetcore.user.domain.UserRepository;

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
