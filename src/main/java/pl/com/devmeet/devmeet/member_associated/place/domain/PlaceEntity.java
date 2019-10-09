package pl.com.devmeet.devmeet.member_associated.place.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.MeetingEntity;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.poll_associated.place_vote.domain.PlaceVoteEntity;

import javax.persistence.*;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "places")
@Entity
@Getter
@Setter
public class PlaceEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private MemberEntity member;

    private String placeName;

    private String description;

    private String website;

    private String location;

    @OneToOne(mappedBy = "place",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private AvailabilityEntity availability;

    @OneToOne(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private PlaceVoteEntity placeVote;

    @OneToOne(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MeetingEntity meeting;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime modificationTime;

    private boolean isActive;
}
