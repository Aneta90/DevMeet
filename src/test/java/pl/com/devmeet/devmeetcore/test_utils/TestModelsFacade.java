package pl.com.devmeet.devmeetcore.test_utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeetcore.user.domain.DefaultUserLoginTypeEnum;
import pl.com.devmeet.devmeetcore.user.domain.UserDto;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 30.11.2019
 * Time: 13:31
 */
@Getter
@NoArgsConstructor
public class TestModelsFacade {

    private UserDto firstUser;
    private UserDto secondUser;

    private MemberDto firstMember;
    private MemberDto secondMember;

    private AvailabilityDto firstMemberAvailability1;
    private AvailabilityDto firstMemberAvailability2;
    private AvailabilityDto secondMemberAvailability1;
    private AvailabilityDto secondMemberAvailability2;

    private PlaceDto firstMemberPlace1;
    private PlaceDto firstMemberPlace2;
    private PlaceDto secondMemberPlace1;
    private PlaceDto secondMemberPlace2;

    private GroupDto groupDto;

    private MessengerDto firstMemberMessenger;
    private MessengerDto secondMemberMessenger;
    private MessengerDto groupMessenger;


    public UserDto initFirstUser(){
        return UserDto.builder()
                .login(DefaultUserLoginTypeEnum.EMAIL)
                .email("test1@test1.pl")
                .phone("221234567")
                .password("testPass1")
                .isActive(true)
                .loggedIn(true)
                .build();
    }

    public UserDto initSecondUser(){
        return UserDto.builder()
                .login(DefaultUserLoginTypeEnum.EMAIL)
                .email("test@test.pl")
                .phone("221234567")
                .password("testPass")
                .isActive(true)
                .loggedIn(true)
                .build();
    }

    public MemberDto initFirstMember(){
        return MemberDto.builder()
                .user(initFirstUser())
                .nick("testMember2")
//                .messenger(initFirstMemberMessenger())
                .isActive(true)
                .modificationTime(DateTime.now())
                .creationTime(DateTime.now())
                .build();
    }

    public MemberDto initSecondMember(){
        return MemberDto.builder()
                .user(initSecondUser())
                .nick("testMember")
//                .messenger(initSecondMemberMessenger())
                .isActive(true)
                .modificationTime(DateTime.now())
                .creationTime(DateTime.now())
                .build();
    }

    public GroupDto initGroup(){
        GroupDto groupDto =  GroupDto.builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .creationTime(null)
                .modificationTime(null)
                .isActive(false)
                .build();

//        MessengerDto messengerDto = initGroupMessenger(groupDto);
//        groupDto.setMessenger(messengerDto);

        return groupDto;
    }

    public MessengerDto initFirstMemberMessenger(MemberDto memberDto){
        return MessengerDto.builder()
                .member(memberDto)
                .build();
    }

    public MessengerDto initSecondMemberMessenger(MemberDto memberDto){
        return MessengerDto.builder()
                .member(memberDto)
                .build();
    }

    public MessengerDto initGroupMessenger(GroupDto groupDto){
        return MessengerDto.builder()
                .group(groupDto)
                .build();
    }

}
