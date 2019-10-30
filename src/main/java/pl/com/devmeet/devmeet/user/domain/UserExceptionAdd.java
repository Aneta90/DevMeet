package pl.com.devmeet.devmeet.user.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Adding user error!. Set all required fields without id or use update.")
class UserExceptionAdd extends RuntimeException {
}
