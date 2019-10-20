package pl.com.devmeet.devmeet.member_associated;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MemberCrudFacadeTest {

    @Autowired
    MemberRepository memberRepository;

    MemberDto memberDto;

    private MemberDto createdMemberDto;
    private MemberCrudFacade memberCrudFacade;

    @Before
    public void setUp() throws EntityAlreadyExistsException {
        memberCrudFacade = new MemberCrudFacade(memberRepository);
        memberDto = new MemberDto();
        memberDto.setNick("testMember");
        memberDto.setActive(true);
        memberDto.setModificationTime(DateTime.now());
        memberDto.setCreationTime(DateTime.now());
        createdMemberDto = memberCrudFacade.create(memberDto);
    }

    @Test
    public void WHEN_creating_non_existing_member_then_create_new_member() {
        assertThat(createdMemberDto).isNotNull();
        assertThat(createdMemberDto.getNick()).isEqualTo("testMember");
        assertThat(createdMemberDto.isActive()).isTrue();
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void WHEN_try_to_create_existing_member_then_throw_exception() throws EntityAlreadyExistsException {
        MemberDto existingMemberDto = new MemberDto();
        existingMemberDto.setNick("testMember");
        memberCrudFacade.create(existingMemberDto);
    }

    @Test
    public void WHEN_find_existing_member_then_return_memberDto() throws EntityNotFoundException {
        MemberDto foundMemberDto = memberCrudFacade.read(memberDto);
        assertThat(foundMemberDto).isNotNull();
        assertThat(foundMemberDto.getNick()).isEqualTo("testMember");
    }

    @Test(expected = NullPointerException.class) //do poprawy ma wyrzucać EntityNotFoundException
    public void WHEN_try_to_find_member_who_does_not_exist_THEN_return_MemberNotFoundException() throws EntityNotFoundException {
        MemberDto notFoundMemberDto = new MemberDto();
        notFoundMemberDto.setNick("null");
        memberCrudFacade.read(notFoundMemberDto);
    }
}
