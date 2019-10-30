package pl.com.devmeet.devmeet.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
class UserApi {

    private UserCrudFacade userCrudFacade;

    @Autowired
    public UserApi(UserCrudFacade userCrudFacade) {
        this.userCrudFacade = userCrudFacade;
    }

    // get

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userCrudFacade.readAll();
    }
}
