package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.messenger_associated.message.domain.MessageDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessengerDto {

    private MemberDto member;
    private GroupDto group;

    private List<MessageDto> messages;

    private DateTime creationTime;
    private boolean isActive;
}
