package pl.com.devmeet.devmeetcore.group_associated.permission.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "permission")
@Entity
@Getter
@Setter
public class PermissionEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private GroupEntity group;

//    private PermissionTypeEnum type;

    private boolean possibleToVote;
    private boolean possibleToMessaging;
    private boolean possibleToChangeGroupName;
    private boolean possibleToBanMember;

    private boolean memberBaned;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime modificationTime;
    private boolean isActive;
}
