package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeet.user.domain.DefaultUserLoginTypeEnum;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 27.11.2019
 * Time: 23:04
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
class MemberReceiverBuilder {

    private UserRepository userRepository;
    private MemberRepository memberRepository;
    private MessengerRepository messengerRepository;

    private UserDto userDto;
    private MemberDto memberDto;
    private MessengerDto messengerDto;

    public void build(){
        initUser();
        initMember();
        initMessenger();
    }

    private void initUser() {
        this.userDto = UserDto.builder()
                .login(DefaultUserLoginTypeEnum.EMAIL)
                .email("test1@test1.pl")
                .phone("221234567")
                .password("testPass1")
                .isActive(true)
                .loggedIn(true)
                .build();
    }

    private void initMember() {
        this.memberDto = MemberDto.builder()
                .user(userDto)
                .nick("testMember2")
                .isActive(true)
                .modificationTime(DateTime.now())
                .creationTime(DateTime.now())
                .build();
    }

    private void initMessenger() {
        this.messengerDto = MessengerDto.builder()
                .member(memberDto)
                .build();
    }
}
