package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import javax.persistence.*;
import java.util.PrimitiveIterator;
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

    @OneToMany(mappedBy = "permissions", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberEntity member;

    @OneToMany(mappedBy = "permissions", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private GroupEntity group;

    private PermissionTypeEnum type;

//    private boolean possibleToVote;
//
//    private boolean possibleToMessaging;
//
//    private boolean possibleToChangeGroupName;
//
//    private boolean possibleToBanMember;
}
