package pl.com.devmeet.devmeet.user.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DefaultUserLoginTypeEnum login;

    private String password;

    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberEntity member;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime creationTime;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime modificationTime;

    private boolean isActive;

    private boolean loggedIn;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime loginTime;

    public UserEntity(DefaultUserLoginTypeEnum login, String password, String email, String phone, MemberEntity member, DateTime creationTime, DateTime modificationTime, boolean isActive, boolean loggedIn, DateTime loginTime) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.member = member;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.isActive = isActive;
        this.loggedIn = loggedIn;
        this.loginTime = loginTime;
    }
}
