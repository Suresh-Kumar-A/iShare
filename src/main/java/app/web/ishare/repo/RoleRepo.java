package app.web.ishare.repo;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import app.web.ishare.model.Role;

@Repository
public interface RoleRepo extends ListCrudRepository<Role, Integer> {

    public Optional<Role> findByRoleName(String roleName);

}
