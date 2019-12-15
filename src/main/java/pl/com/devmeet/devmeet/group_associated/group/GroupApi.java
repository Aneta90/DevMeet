package pl.com.devmeet.devmeet.group_associated.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupFoundButNotActiveException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<GroupDto> add(@RequestBody GroupDto newGroup) {
        try {
            this.group.add(newGroup);
        } catch (GroupAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newGroup.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> update(@PathVariable Long id,
                                           @RequestBody GroupDto updatedGroup) {
        if (!id.equals(updatedGroup.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id from path does not match with id in body!");

        try {
            group.update(group.findById(id), updatedGroup);
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (GroupException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        } catch (GroupFoundButNotActiveException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deactivateGroupById(@PathVariable Long id) {
        try {
            group.delete(group.findById(id));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (GroupFoundButNotActiveException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
