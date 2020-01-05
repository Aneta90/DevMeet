package pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.messenger_associated.message.domain.MessageEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messengers")
@Entity
public class MessengerEntity {

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID Id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberEntity member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private GroupEntity group;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MessageEntity> sent;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MessageEntity> received;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime creationTime;

    private boolean isActive;
}
