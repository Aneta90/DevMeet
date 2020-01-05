package pl.com.devmeet.devmeetcore.test_utils;

import pl.com.devmeet.devmeetcore.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeetcore.user.domain.UserDto;
import pl.com.devmeet.devmeetcore.user.domain.UserRepository;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyActiveException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyExistsException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

class TestFirstUserInitiator implements TestObjectInitiator<UserRepository, UserCrudFacade, UserDto> {

    private UserRepository repository;
    private UserDto testUserDto;

    public TestFirstUserInitiator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserCrudFacade initFacade() {
        return new UserCrudFacade(repository);
    }

    @Override
    public UserDto initAndSaveTestObject() throws UserAlreadyExistsException, UserNotFoundException, UserAlreadyActiveException {
        UserCrudFacade userCrudFacade = initFacade();
        userCrudFacade.add(testUserDto);
        return userCrudFacade.activation(testUserDto);
    }
}
