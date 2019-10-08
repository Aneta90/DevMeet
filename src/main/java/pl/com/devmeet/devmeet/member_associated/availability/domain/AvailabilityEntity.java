package pl.com.devmeet.devmeet.member_associated.availability.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceEntity;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.AvailabilityVoteEntity;
import pl.com.devmeet.devmeet.poll_associated.place_vote.domain.PlaceVoteEntity;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "availabilities")
@Entity
@Getter
@Setter
public class AvailabilityEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID Id;

    @OneToMany(mappedBy = "availabilities",fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberEntity member;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime beginTime;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime endTime;

    private boolean remoteWork;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private PlaceEntity place;

    @OneToOne(mappedBy = "availability", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private AvailabilityVoteEntity availabilityVote;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime modificationTime;

    private boolean isActive;
}
