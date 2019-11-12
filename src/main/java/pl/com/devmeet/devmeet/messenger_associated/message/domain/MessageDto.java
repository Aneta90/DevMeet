package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {

    private MemberDto fromMember;

    private MemberDto toMember;

    private GroupDto toGroup;

    private String message;

    private DateTime creationTime;
}