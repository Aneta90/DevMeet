package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {

    private MessengerDto sender;

    private MessengerDto receiver;

    private String message;

    private DateTime creationTime;

    private boolean isActive;
}