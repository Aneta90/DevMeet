package pl.com.devmeet.devmeet.domain.user;

import lombok.*;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public
class UserDto {

    private DefaultUserLoginTypeEnum login;

    private String phone;

    private String email;

    private String password;

    private DateTime creationTime;

    private DateTime modificationTime;

    private boolean isActive;

    private boolean loggedIn;

    private DateTime loginTime;
}
