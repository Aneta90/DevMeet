package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.messenger_associated.message.domain.MessageDto;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessengerCrudFacadeTest {

    @Autowired
    MessengerRepository messengerRepository;

    @Autowired
    GroupCrudRepository groupCrudRepository;

    private MessengerCrudFacade messengerCrudFacade;

    GroupCrudFacade groupCrudFacade;
    MemberCrudFacade memberCrudFacade;

    private GroupDto groupDto;
    private MessengerDto messengerDto;
    private MemberDto memberDto;
    private MemberDto memberDto1;
    private MessageDto messageDto;
    private List<MessageDto> messageDtoList;

    @Before
    public void setUp() throws EntityNotFoundException, EntityAlreadyExistsException {

        //init??

        messengerCrudFacade = new MessengerCrudFacade(messengerRepository);
        groupDto = new GroupDto().builder()
                .groupName("testGroup")
                .creationTime(DateTime.now())
                .description("testGroup")
                .isActive(true)
                .messenger(messengerDto)
                .build();
        groupCrudFacade.create(groupDto);

        memberDto = new MemberDto().builder()
                .nick("testMember")
                .messenger(messengerDto)
                .creationTime(DateTime.now())
                .build();

        memberDto1 = new MemberDto().builder()
                .nick("testMember1")
                .messenger(messengerDto)
                .creationTime(DateTime.now())
                .build();

        memberCrudFacade.create(memberDto);
        memberCrudFacade.create(memberDto1);

    /*    messageDto = new MessageDto().builder()
                .creationTime(DateTime.now())
                .message("messageTest")
                .fromMember(memberDto)
                .toMember(memberDto1)
                .build();

        messageDtoList = new LinkedList<>();
        messageDtoList.add(messageDto);*/

        messengerDto = new MessengerDto().builder()
                .creationTime(DateTime.now())
                .isActive(true)
                .group(groupDto)
               // .messages(messageDtoList)
                .member(memberDto)
                .build();
        messengerCrudFacade.create(messengerDto);

    }

    @Test
    public void WHEN_try_to_create_non_existing_messenger_then_create_new_messenger() {
        assertThat(messengerDto).isNotNull();
        assertThat(messengerDto.getGroup()).isEqualTo(groupDto);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void WHEN_try_to_create_existing_messenger_then_return_EntityAlreadyExistException() throws EntityAlreadyExistsException, EntityNotFoundException {
        MessengerDto existingMessenger = new MessengerDto();
        existingMessenger.setGroup(groupDto);
        messengerCrudFacade.create(messengerDto);
    }
}