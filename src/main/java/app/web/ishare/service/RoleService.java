package app.web.ishare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import app.web.ishare.dto.RoleDto;
import app.web.ishare.model.Role;
import app.web.ishare.repo.RoleRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepo roleRepo;

    public Role createRole(Role role) throws Exception {
        return roleRepo.save(role);
    }

    public Optional<RoleDto> findRoleByName(String role) throws Exception {
        return Optional.ofNullable(convertToDto(
                roleRepo.findByRoleName(role)
                        .orElseThrow(() -> new RuntimeException("Role does not exist"))));
    }

    public List<RoleDto> getAllRoles() throws Exception {
        return roleRepo.findAll().stream().map(role -> convertToDto(role))
                .collect(Collectors.toList());
    }

    private RoleDto convertToDto(Role role) {
        if (Objects.isNull(role))
            throw new NullPointerException("Can't convert to Role Dto. Role is 'NULL'");

        RoleDto roleDto = RoleDto.builder().build();
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    }
}
