package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.user.domain.UserDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberDto {

    private UserDto user;
    private String nick;

    private List<GroupDto> groups;

    private List<AvailabilityDto> availabilities;
    private List<PlaceDto> places;

    private MessengerDto messenger;

    private DateTime creationTime;
    private DateTime modificationTime;

    private boolean isActive;
}
