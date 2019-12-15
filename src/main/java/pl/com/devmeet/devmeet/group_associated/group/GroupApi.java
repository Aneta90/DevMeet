package pl.com.devmeet.devmeet.group_associated.group;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
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
    public ResponseEntity<List<GroupDto>> getAllOrFiltered(@RequestParam(required = false) String searchText) {
        return new ResponseEntity<>(group.findBySearchText(searchText), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("{id}")
    public ResponseEntity<GroupDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(group.findById(id), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<GroupDto> add(@RequestBody GroupDto newGroup) {
        this.group.add(newGroup);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newGroup.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newGroup);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> update(@PathVariable Long id,
                                           @RequestBody GroupDto updatedGroup) {
        if (!id.equals(updatedGroup.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id from path does not match with id in body!");
        group.update(group.findById(id), updatedGroup);
        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public HttpStatus deactivateById(@PathVariable Long id) {
        group.delete(group.findById(id));
        return HttpStatus.OK;
    }
}
