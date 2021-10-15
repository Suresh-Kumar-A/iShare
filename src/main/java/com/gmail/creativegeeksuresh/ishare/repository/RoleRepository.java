package com.gmail.creativegeeksuresh.ishare.repository;

import com.gmail.creativegeeksuresh.ishare.model.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    public Role findByRoleName(String roleName);
    
}
