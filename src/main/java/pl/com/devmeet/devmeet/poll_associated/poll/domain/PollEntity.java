package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.AvailabilityVoteEntity;
import pl.com.devmeet.devmeet.poll_associated.place_vote.domain.PlaceVoteEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "polls")
@Entity
@Getter
@Setter
public class PollEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private GroupEntity group;

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PlaceVoteEntity> placeVotes;

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AvailabilityVoteEntity> availabilityVotes;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;
    private boolean isActive;
}
