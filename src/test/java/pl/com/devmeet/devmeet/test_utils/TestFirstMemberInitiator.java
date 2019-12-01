package pl.com.devmeet.devmeet.test_utils;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 30.11.2019
 * Time: 13:14
 */
class TestFirstMemberInitiator implements TestObjectInitiator<MemberRepository, MemberCrudFacade, MemberDto> {

    private MemberRepository repository;
    private MemberDto testMemberDto;

    public TestFirstMemberInitiator(MemberRepository repository) {
        this.repository = repository;


    }

    @Override
    public MemberCrudFacade initFacade() {
        return null;
    }

    @Override
    public MemberDto initAndSaveTestObject() {
        return null;
    }
}
