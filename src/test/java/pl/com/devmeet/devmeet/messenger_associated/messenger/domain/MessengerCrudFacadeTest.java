package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessengerCrudFacadeTest {

    @Autowired
    MessengerRepository messengerRepository;
    private MessengerDto messengerDto;
    private MessengerDto createdMessengerDto;
    private MessengerCrudFacade messengerCrudFacade;

  /*  @Autowired
    GroupCrudRepository groupCrudRepository;
    GroupCrudFacade groupCrudFacade;
    private GroupDto groupDto;
    GroupDto createdGroupDto;*/

    @Before
    public void setUp() throws EntityAlreadyExistsException {

    /*    groupCrudFacade = new GroupCrudFacade(groupCrudRepository);
        groupDto = new GroupDto().builder()
                .groupName("testGroup")
                .description("testGroup")
                .messenger(messengerDto)
                .build();*/

        messengerCrudFacade = new MessengerCrudFacade(messengerRepository);
        messengerDto = new MessengerDto().builder()
                .creationTime(DateTime.now())
                .isActive(true)
                .group(null)
                .messengerName("testName")
                .messages(null)
                .member(null)
                .build();

        createdMessengerDto = messengerCrudFacade.create(messengerDto);
        //  createdGroupDto = groupCrudFacade.create(groupDto);

    }

    @Test
    public void WHEN_try_to_create_non_existing_messenger_then_create_new_messenger() {
        assertThat(createdMessengerDto).isNotNull();
        assertThat(createdMessengerDto.getMessengerName()).isEqualTo("testName");
        //   assertThat(createdMessengerDto.getGroup().getGroupName()).isEqualTo("testGroup");
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void WHEN_try_to_create_existing_messenger_then_return_EntityAlreadyExistException() throws EntityAlreadyExistsException {
        MessengerDto existingMessenger = new MessengerDto();
        existingMessenger.setMessengerName("testName");
        messengerCrudFacade.create(existingMessenger);
    }

    @Test
    public void WHEN_find_existing_messenger_then_return_messengerDto() throws EntityNotFoundException {
        MessengerDto foundMessengerDto = messengerCrudFacade.read(createdMessengerDto);
        assertThat(foundMessengerDto).isNotNull();
        assertThat(foundMessengerDto.getMessengerName()).isEqualTo("testName");
    }

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_find_messenger_which_does_not_exist_THEN_return_MessengerNotFoundException() throws EntityNotFoundException {
        MessengerDto notFoundMessengerDto = new MessengerDto();
        notFoundMessengerDto.setMessengerName("nonExistingMessenger");
        messengerCrudFacade.read(notFoundMessengerDto);
    }

    @Test
    public void WHEN_messenger_exists_then_return_true() {
        boolean messengerExists = messengerCrudFacade.isExist(messengerDto);
        assertThat(messengerExists).isTrue();
    }

    @Test
    public void WHEN_messenger_does_not_exist_then_return_false() {
        MessengerDto messengerNotExisted = new MessengerDto();
        messengerNotExisted.setMessengerName("test");
        boolean memberDoesNotExist = messengerCrudFacade.isExist(messengerNotExisted);
        assertThat(memberDoesNotExist).isFalse();
    }

    @Test
    public void WHEN_try_to_delete_existing_messenger_then_delete_messenger() throws EntityNotFoundException {
        boolean isMessengerDeleted = messengerCrudFacade.delete(createdMessengerDto);
        assertThat(isMessengerDeleted).isTrue();
    }

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_delete_non_existing_member_then_throw_EntityNotFoundException() throws EntityNotFoundException {
        MessengerDto nonExistingMessenger = new MessengerDto();
        nonExistingMessenger.setMessengerName("aaa");
        boolean isMessengerDeleted = messengerCrudFacade.delete(nonExistingMessenger);
        assertThat(isMessengerDeleted).isFalse();
    }
}