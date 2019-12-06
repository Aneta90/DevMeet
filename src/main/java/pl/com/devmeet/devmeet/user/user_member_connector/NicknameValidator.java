package pl.com.devmeet.devmeet.user.user_member_connector;

import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 06.12.2019
 * Time: 00:06
 */
@NoArgsConstructor
class NicknameValidator {

    public boolean validate(String nickname) {
        if (!nickname.isEmpty())
            return true;
        else
            return false;
    }
}
