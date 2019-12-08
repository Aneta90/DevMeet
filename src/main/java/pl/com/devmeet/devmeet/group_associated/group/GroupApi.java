package pl.com.devmeet.devmeet.group_associated.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
class GroupApi {

    private GroupCrudFacade group;
    private MemberCrudFacade member;

    @Autowired
    public GroupApi(GroupCrudFacade group, MemberCrudFacade member) {
        this.group = group;
        this.member = member;
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups(@RequestParam(required = false) String searchText) {
        return new ResponseEntity<>(group.findBySearchText(searchText), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GroupDto> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(group.findById(id), HttpStatus.OK);
        } catch (GroupNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
