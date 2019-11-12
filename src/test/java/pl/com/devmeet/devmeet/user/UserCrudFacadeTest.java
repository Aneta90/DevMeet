package pl.com.devmeet.devmeet.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.user.domain.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserCrudFacadeTest {

    @Autowired
    private UserRepository userRepository;

    UserCrudInterface userFacade;
    UserDto testDto;

    private String userNotFoundMessage = "User not found";
    private String defaultLoginTypeErrMessage = "User default login type not defined";
    private String userAlreadyActivatedMessage = "User has been activated";

    @Before
    public void setUp() throws Exception {
        userFacade = new UserCrudFacade(userRepository);

        testDto = new UserDto().builder()
                .email("test@test.pl")
                .phone("221234567")
                .password("testPass")
                .isActive(true)
                .loggedIn(true)
                .build();
    }

    @Test
    public void WHEN_create_new_not_existing_user_THEN_return_UserDto() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);

        assertThat(created).isNotNull();

        assertThat(created.getLogin()).isEqualTo(DefaultUserLoginTypeEnum.PHONE);
        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.getModificationTime()).isNull();
        assertThat(created.isActive()).isFalse();
        assertThat(created.isLoggedIn()).isFalse();
        assertThat(created.getLoginTime()).isNull();
    }

    @Test
    public void WHEN_try_to_create_new_user_with_no_defined_login_THEN_return_IllegalArgumentException() {
        try {
            userFacade.create(testDto, null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(defaultLoginTypeErrMessage);
        }
    }

    @Test
    public void WHEN_try_to_create_user_that_already_exist_THEN_return_null() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto copyOfCreated = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);

        assertThat(created).isNotNull();
        assertThat(copyOfCreated).isNull();
    }

    @Test
    public void WHEN_find_existing_user_THEN_return_UserDto() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto found = userFacade.read(testDto);

        assertThat(created).isNotNull();
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_user_who_does_not_exist_THEN_return_IllegalArgumentException() {
        try {
            userFacade.read(testDto);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(userNotFoundMessage);
        }
    }

    @Test
    public void WHEN_user_exists_THEN_return_true() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);

        assertThat(created).isNotNull();
        assertThat(userFacade.isExist(testDto)).isTrue();
    }

    @Test
    public void WHEN_user_not_exists_THEN_return_false() {
        assertThat(userFacade.isExist(testDto)).isFalse();
    }

    @Test
    public void WHEN_activate_user_RETURN_UserDto() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto activated = userFacade.activation(testDto);

        assertThat(created).isNotNull();

        assertThat(activated.isActive()).isTrue();
        assertThat(activated.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_activate_activated_user_THEN_return_IllegalArgumentException() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto activated = userFacade.activation(testDto);

        assertThat(created).isNotNull();
        assertThat(activated).isNotNull();

        try {
            userFacade.activation(testDto);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(userAlreadyActivatedMessage);
        }
    }

    @Test
    public void WHEN_try_to_activate_not_existing_user_THEN_return_IllegalArgumentException() {
        try {
            userFacade.activation(testDto);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(userNotFoundMessage);
        }
    }

    @Test
    public void WHEN_try_to_update_activated_existing_user_THEN_return_UserDto() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto activated = userFacade.activation(testDto);
        UserDto updated = userFacade.update(userToUpdate(), created);

        assertThat(created).isNotNull();
        assertThat(activated).isNotNull();
        assertThat(activated.getCreationTime().equals(updated.getCreationTime())).isTrue();
        assertThat(updated.getLogin().equals(activated.getLogin())).isFalse();
    }

    @Test
    public void WHEN_try_to_update_not_activated_user_THEN_return_null() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto updated = userFacade.update(userToUpdate(), created);

        assertThat(created).isNotNull();
        assertThat(updated).isNull();

    }

    @Test
    public void WHEN_try_to_update_not_existing_user_THEN_return_IllegalArgumentException() {
        try {
            userFacade.update(userToUpdate(), null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(userNotFoundMessage);
        }
    }

    @Test
    public void WHEN_try_to_update_existing_but_not_activated_user_THEN_return_null() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto updated = userFacade.update(userToUpdate(), created);

        assertThat(created).isNotNull();
        assertThat(updated).isNull();
    }

    private UserDto userToUpdate() {
        return new UserDto().builder()
                .login(DefaultUserLoginTypeEnum.EMAIL)
                .phone("331234567")
                .email("cos@testcos.com")
                .password("134679")
                .build();
    }

    @Test
    public void WHEN_delete_existing_and_activated_user_THEN_return_true() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        UserDto activated = userFacade.activation(testDto);
        boolean deleted = userFacade.delete(testDto);

        assertThat(created).isNotNull();
        assertThat(activated).isNotNull();
        assertThat(deleted).isTrue();
    }

    @Test
    public void WHEN_try_to_delete_existing_but_not_activated_user_THEN_return_false() {
        UserDto created = userFacade.create(testDto, DefaultUserLoginTypeEnum.PHONE);
        boolean deleted = userFacade.delete(testDto);

        assertThat(created).isNotNull();
        assertThat(deleted).isFalse();
    }

    @Test
    public void WHEN_try_to_delete_not_existing_user_THEN_return_false() {
        boolean deleted = userFacade.delete(testDto);

        assertThat(deleted).isFalse();
    }
}