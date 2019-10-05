package pl.com.devmeet.devmeet.group_associated.group.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.MeetingDto;
import pl.com.devmeet.devmeet.group_associated.permission.domain.PermissionDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "groups")
@Entity
public class GroupEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID Id;

    private String groupName;
    private String website;
    private String description;

    @OneToOne(mappedBy = "group", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MessengerDto messenger;

    private Integer membersLimit;
    private Integer memberCounter;
    private Integer meetingCounter;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member")
    private List<MemberDto> members;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "permission")
    private List<PermissionDto> permissions;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "poll")
    private List<PollDto> polls;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "meeting")
    private List<MeetingDto> meetings;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime modificationTime;
    private boolean isActive;
}
