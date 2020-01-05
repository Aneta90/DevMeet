package pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.messenger_associated.message.domain.MessageDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessengerDto {

    private MemberDto member;
    private GroupDto group;

    private List<MessageDto> sent;
    private List<MessageDto> received;

    private DateTime creationTime;
    private boolean isActive;
}
