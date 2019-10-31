package pl.com.devmeet.devmeet.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private DefaultUserLoginTypeEnum login;
    private String phone;
    private String email;
    private String password;

    private DateTime creationTime;
    private DateTime modificationTime;

    private boolean loggedIn;
    private DateTime loginTime;

    private boolean isActive;
}
