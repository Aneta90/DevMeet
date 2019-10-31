package pl.com.devmeet.devmeet.user.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate email exception")
class UserExceptionDuplicateEmail extends RuntimeException {
}
