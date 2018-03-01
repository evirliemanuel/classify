package io.ermdev.alice.controller;

import io.ermdev.alice.dto.RoleDto;
import io.ermdev.alice.entity.Role;
import io.ermdev.alice.repository.RoleRepository;
import mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private RoleRepository roleRepository;
    private SimpleMapper mapper;

    @Autowired
    public RoleController(RoleRepository roleRepository, SimpleMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @GetMapping("role/{roleId}")
    public RoleDto getById(@PathVariable("roleId") Long roleId) {
        return mapper.set(roleRepository.findById(roleId)).mapAllTo(RoleDto.class);
    }

    @GetMapping("role/all")
    public List<RoleDto> getAll() {
        return mapper.set(roleRepository.findAll()).mapToList(RoleDto.class);
    }

    @PostMapping("role/add")
    public RoleDto add(@RequestBody Role role) {
        return mapper.set(roleRepository.save(role)).mapAllTo(RoleDto.class);
    }

    @PutMapping("role/{roleId}")
    public RoleDto updateById(@PathVariable("roleId") Long roleId, @RequestBody Role role) {
        Role current_role = roleRepository.findById(roleId);
        if(role.getName() != null && !role.getName().trim().isEmpty()) {
            current_role.setName(role.getName());
        }
        return mapper.set(roleRepository.save(role)).mapAllTo(RoleDto.class);
    }

    @DeleteMapping("role/{roleId}")
    public RoleDto deleteById(@PathVariable("roleId") Long roleId) {
        Role role = roleRepository.findById(roleId);
        roleRepository.delete(role);
        return mapper.set(role).mapAllTo(RoleDto.class);
    }

}
