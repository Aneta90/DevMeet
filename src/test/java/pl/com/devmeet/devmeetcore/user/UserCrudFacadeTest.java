package pl.com.devmeet.devmeetcore.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeetcore.user.domain.UserDto;
import pl.com.devmeet.devmeetcore.user.domain.UserRepository;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserCrudFacadeTest {

    @Autowired
    private UserRepository userRepository;

    private UserCrudFacade userFacade;
    private UserDto testDto;

    @Before
    public void setUp() {
        testDto = UserDto.builder()
                .email("test@test.pl")
                .password("testPass")
                .isActive(true)
                .build();
    }

    private UserCrudFacade initFacade() {
        return new UserCrudFacade(userRepository);
    }

    private UserDto createTestUser() throws UserAlreadyExistsException {
        return initFacade().add(testDto);
    }

    @Test
    public void WHEN_create_new_not_existing_user_THEN_return_UserDto() throws UserAlreadyExistsException {
        UserDto created = userFacade.add(testDto);

        assertThat(created).isNotNull();
        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.getModificationTime()).isNull();
        assertThat(created.isActive()).isFalse();
    }
//
//    @Test
//    public void WHEN_try_to_create_new_user_with_no_defined_login_THEN_return_IllegalArgumentException() {
//        try {
//            userFacade.add(testDto);
//            Assert.fail();
//        } catch ( CrudException e) {
//            assertThat(e)
//                    .isInstanceOf(UserNotFoundException.class)
//                    .hasMessage(User);
//        }
//    }

    @Test
    public void WHEN_try_to_create_user_that_already_exist_THEN_return_UserAlreadyExistsException() throws UserAlreadyExistsException {
        createTestUser();

        try {
            createTestUser();
            Assert.fail();
        } catch (UserAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(UserAlreadyExistsException.class)
                    .hasMessage(UserCrudStatusEnum.USER_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_find_existing_user_THEN_return_UserDto() throws UserAlreadyExistsException, UserNotFoundException {
        createTestUser();
        UserDto found = userFacade.find(testDto);
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_user_who_does_not_exist_THEN_return_UserNotFoundException() {
        try {
            userFacade.find(testDto);
            Assert.fail();
        } catch (CrudException e) {
            assertThat(e)
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(UserCrudStatusEnum.USER_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_user_exists_THEN_return_true() throws UserAlreadyExistsException {
        createTestUser();
        assertThat(userFacade.isExist(testDto)).isTrue();
    }

    @Test
    public void WHEN_user_not_exists_THEN_return_false() {
        assertThat(userFacade.isExist(testDto)).isFalse();
    }

    @Test
    public void WHEN_activate_user_RETURN_UserDto() throws UserAlreadyExistsException, UserNotFoundException, UserAlreadyActiveException {
        UserDto created = createTestUser();
        UserDto activated = userFacade.activation(testDto);

        assertThat(created).isNotNull();

        assertThat(activated.isActive()).isTrue();
        assertThat(activated.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_activate_activated_user_THEN_return_UserAlreadyActiveException() throws UserAlreadyExistsException, UserNotFoundException, UserAlreadyActiveException {
        createTestUser();
        userFacade.activation(testDto);

        try {
            userFacade.activation(testDto);
            Assert.fail();
        } catch (CrudException e) {
            assertThat(e)
                    .isInstanceOf(UserAlreadyActiveException.class)
                    .hasMessage(UserCrudStatusEnum.USER_ALREADY_ACTIVE.toString());
        }
    }

    @Test
    public void WHEN_try_to_activate_not_existing_user_THEN_return_UserNotFoundException() {
        try {
            userFacade.activation(testDto);
            Assert.fail();
        } catch (CrudException e) {
            assertThat(e)
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(UserCrudStatusEnum.USER_NOT_FOUND.toString());
        }
    }

    private UserDto userToUpdate() {
        testDto.setEmail("cos@testcos.com");
        testDto.setPassword("134679");
        return testDto;
    }

    @Test
    public void WHEN_try_to_update_activated_existing_user_THEN_return_UserDto() throws UserAlreadyExistsException, UserNotFoundException, UserAlreadyActiveException, UserFoundButNotActive {
        UserDto created = createTestUser();
        UserDto activated = userFacade.activation(testDto);
        UserDto updated = userFacade.update(userToUpdate(), created);

        assertThat(activated.getCreationTime().equals(updated.getCreationTime())).isTrue();
        assertThat(updated.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_update_not_activated_user_THEN_return_UserFoundButNotActiveException() throws UserAlreadyExistsException, UserNotFoundException {
        UserDto created = createTestUser();
        try {
            userFacade.update(userToUpdate(), created);
            Assert.fail();
        } catch (CrudException e) {
            assertThat(e)
                    .isInstanceOf(UserFoundButNotActive.class)
                    .hasMessage(UserCrudStatusEnum.USER_FOUND_BUT_NOT_ACTIVE.toString());
        }
    }

    @Test
    public void WHEN_try_to_update_not_existing_user_THEN_return_IllegalArgumentException() {
        try {
            userFacade.update(userToUpdate(), null);
            Assert.fail();
        } catch (CrudException e) {
            assertThat(e)
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(UserCrudStatusEnum.USER_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_and_activated_user_THEN_return_UserDto() throws UserAlreadyExistsException, UserNotFoundException, UserAlreadyActiveException, UserFoundButNotActive {
        createTestUser();
        userFacade.activation(testDto);
        UserDto deleted = userFacade.delete(testDto);

        assertThat(deleted.isActive()).isTrue();
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_delete_existing_but_not_activated_user_THEN_return_UserFoundButNotActive() throws UserAlreadyExistsException, UserNotFoundException {
        createTestUser();
        try {
            userFacade.delete(testDto);
            Assert.fail();
        } catch (UserFoundButNotActive e) {
            assertThat(e)
                    .isInstanceOf(UserFoundButNotActive.class)
                    .hasMessage(UserCrudStatusEnum.USER_FOUND_BUT_NOT_ACTIVE.toString());
        }
    }

    @Test
    public void WHEN_try_to_delete_not_existing_user_THEN_return_UserNotFoundException() throws UserFoundButNotActive {
        try {
            userFacade.delete(testDto);
        } catch (CrudException e) {
            assertThat(e)
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(UserCrudStatusEnum.USER_NOT_FOUND.toString());
        }

    }
}