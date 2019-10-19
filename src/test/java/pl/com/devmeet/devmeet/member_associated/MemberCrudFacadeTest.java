package pl.com.devmeet.devmeet.member_associated;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MemberCrudFacadeTest {

    @Autowired
    MemberRepository memberRepository;

    MemberDto createdMemberDto;

    @Before
    public void setUp(){
        MemberCrudFacade memberCrudFacade = new MemberCrudFacade(memberRepository);
        MemberDto memberDto = new MemberDto();
        memberDto.setNick("testMember");
        memberDto.setActive(true);
        memberDto.setModificationTime(DateTime.now());
        memberDto.setCreationTime(DateTime.now());
        createdMemberDto = memberCrudFacade.create(memberDto);
    }

    @Test
    public void WHEN_creating_non_existing_member_then_create_new_member(){
        assertThat(createdMemberDto).isNotNull();
        assertThat(createdMemberDto.getNick()).isEqualTo("testMember");
        assertThat(createdMemberDto.isActive()).isTrue();
    }
}
