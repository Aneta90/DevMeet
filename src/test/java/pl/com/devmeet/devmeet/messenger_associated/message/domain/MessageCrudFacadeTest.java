package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessageCrudFacadeTest {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private GroupCrudRepository groupCrudRepository;
    @Autowired
    private MemberRepository memberRepository;

    private MessageCrudFacade facade;
    private MessageDto messageDto;
    private MessageDto createdMessageDto;

    private GroupCrudFacade groupCrudFacade;
    private GroupDto testGroup;
    private GroupDto createdGroupDto;

    private MemberCrudFacade memberCrudFacade;
    private MemberDto memberDto;
    private MemberDto memberDto1;
    private MemberDto createdMemberDto;
    private MemberDto createdMemberDto1;

    @Before
    public void setUp() throws EntityAlreadyExistsException {
        facade = new MessageCrudFacade(messageRepository);
        groupCrudFacade = new GroupCrudFacade(groupCrudRepository);
        memberCrudFacade = new MemberCrudFacade(memberRepository);

        testGroup = new GroupDto().builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
                .messenger(null)
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .creationTime(null)
                .modificationTime(null)
                .isActive(false)
                .build();

       // createdGroupDto = groupCrudFacade.create(testGroup);

        memberDto = new MemberDto();
        memberDto.setUser(null); //???
        memberDto.setNick("testMember");
        memberDto.setActive(true);
        memberDto.setModificationTime(DateTime.now());
        memberDto.setCreationTime(DateTime.now());

        createdMemberDto = memberCrudFacade.create(memberDto);

        memberDto1 = new MemberDto();
        memberDto1.setNick("testMember2");
        memberDto1.setActive(true);
        memberDto1.setModificationTime(DateTime.now());
        memberDto1.setCreationTime(DateTime.now());

        createdMemberDto1 = memberCrudFacade.create(memberDto1);

        messageDto = new MessageDto().builder()
                .creationTime(DateTime.now())
                .fromMember(createdMemberDto)
                .toMember(createdMemberDto1)
                .toGroup(null)
                .message("testMessage")
                .build();
        createdMessageDto = facade.create(messageDto);
    }


    @Test
    public void create() {

        assertThat(createdMessageDto).isNotNull();
        assertThat(createdMessageDto.getMessage()).isEqualTo("testMessage");
        assertThat(createdMessageDto.getFromMember().getNick()).isEqualTo("testMember");
       // assertThat(createdMessageDto.getToGroup().getGroupName()).isEqualTo("Java test group");
    }

    @Test
    public void findEntityFromMember() {
    }

    @Test
    public void findEntityToMember() {
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteMessagesSentToMember() {
    }

    @Test
    public void deleteMessagesSentFromMember() {
    }

    @Test
    public void map() {
    }

    @Test
    public void map1() {
    }
}