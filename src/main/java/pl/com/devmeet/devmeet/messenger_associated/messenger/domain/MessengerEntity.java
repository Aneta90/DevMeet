package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import javax.persistence.*;
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

    @OneToOne(mappedBy = "messenger", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberEntity member;

    @OneToOne(mappedBy = "messenger", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private GroupEntity group;
}
