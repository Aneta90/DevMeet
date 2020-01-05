package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeetcore.user.domain.UserDto;
import pl.com.devmeet.devmeetcore.user.domain.UserRepository;

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
                .email("test1@test1.pl")
                .password("testPass1")
                .isActive(true)
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
