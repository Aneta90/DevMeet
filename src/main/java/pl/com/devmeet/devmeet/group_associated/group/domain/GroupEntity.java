package pl.com.devmeet.devmeet.group_associated.group.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.MeetingEntity;
import pl.com.devmeet.devmeet.group_associated.permission.domain.PermissionEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tgroup")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String groupName;
    private String website;
    private String description;

    @OneToOne(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MessengerEntity messenger;

    private Integer membersLimit;
    private Integer memberCounter;
    private Integer meetingCounter;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MemberEntity> members;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PermissionEntity> permissions;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PollEntity> polls;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MeetingEntity> meetings;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime modificationTime;
    private boolean isActive;
}
