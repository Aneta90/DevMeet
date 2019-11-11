package pl.com.devmeet.devmeet.messenger_associated.message.domain;

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
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messeges")
@Entity
public class MessageEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

 //   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
 //   private MemberEntity fromMember;

 //   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
 //   private MemberEntity toMember;

 //   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
 //   private GroupEntity toGroup;

    private String message;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

}
