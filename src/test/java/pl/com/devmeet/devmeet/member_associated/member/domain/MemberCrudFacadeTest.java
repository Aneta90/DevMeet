package pl.com.devmeet.devmeet.member_associated.member.domain;

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

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_find_member_who_does_not_exist_THEN_return_MemberNotFoundException() throws EntityNotFoundException {
        MemberDto notFoundMemberDto = new MemberDto();
        notFoundMemberDto.setNick("null");
        MemberDto memberNotFound = memberCrudFacade.read(notFoundMemberDto);
        assertThat(memberNotFound).isNull();
    }

    @Test
    public void WHEN_member_exists_then_return_true() {
        boolean memberExists = memberCrudFacade.isExist(memberDto);
        assertThat(memberExists).isTrue();
    }

    @Test
    public void WHEN_member_does_not_exist_then_return_false() {
        MemberDto memberNotExisted = new MemberDto();
        memberDto.setNick("test");
        boolean memberDoesNotExist = memberCrudFacade.isExist(memberNotExisted);
        assertThat(memberDoesNotExist).isFalse();
    }

    @Test
    public void WHEN_try_to_update_existing_member_then_updated_member() throws EntityNotFoundException {
        MemberDto updatedMemberDto = new MemberDto();
        updatedMemberDto.setNick("updatedMember");
        memberDto = memberCrudFacade.update(updatedMemberDto, memberDto);
        assertThat(memberDto.getNick()).isEqualTo("updatedMember");
    }

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_update_not_existing_member_then_return_EntityNotFoundException() throws EntityNotFoundException {
        MemberDto memberNotExisted = new MemberDto();
        memberDto.setNick("test");
        MemberDto updatedMemberDto = new MemberDto();
        updatedMemberDto.setNick("updatedMember");
        MemberDto resultMemberDto = memberCrudFacade.update(updatedMemberDto, memberNotExisted);
        assertThat(resultMemberDto).isNull();
    }

    @Test
    public void WHEN_try_to_delete_existing_member_then_delete_member() throws EntityNotFoundException { //sprawdzic czy na pewno dobrze działa
        boolean isMemberDeleted = memberCrudFacade.delete(createdMemberDto);
        assertThat(isMemberDeleted).isTrue();
    }

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_delete_non_existing_member_then_throw_EntityNotFoundException() throws EntityNotFoundException {
        MemberDto nonExistingMember = new MemberDto();
        nonExistingMember.setNick("aaa");
        boolean isMemberDeleted = memberCrudFacade.delete(nonExistingMember);
        assertThat(isMemberDeleted).isFalse();
    }
}
