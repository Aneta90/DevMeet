package pl.com.devmeet.devmeetcore.member_associated.place.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.meeting.domain.MeetingEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.PlaceVoteEntity;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "places")
@Entity

public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private MemberEntity member;

    private String placeName;

    private String description;

    private String website;

    private String location;

//    @OneToOne(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private AvailabilityEntity availability;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PlaceVoteEntity> placeVotes;

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
