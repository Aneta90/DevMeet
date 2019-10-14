package pl.com.devmeet.devmeet.member_associated.member.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.permission.domain.PermissionEntity;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceEntity;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.AvailabilityVoteEntity;
import pl.com.devmeet.devmeet.poll_associated.place_vote.domain.PlaceVoteEntity;
import pl.com.devmeet.devmeet.user.domain.UserEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "members")
@Entity
public class MemberEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID Id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private UserEntity user;

    @Column(unique = true)
    private String nick;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<GroupEntity> groups;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AvailabilityEntity> availabilities;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,  orphanRemoval = true)
    private List<PlaceEntity> places;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MessengerEntity messenger;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PermissionEntity> permissions;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AvailabilityVoteEntity> availabilityVotes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PlaceVoteEntity> placeVotes;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime modificationTime;

    private boolean isActive;
}