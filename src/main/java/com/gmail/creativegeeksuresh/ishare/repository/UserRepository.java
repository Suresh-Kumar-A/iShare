package com.gmail.creativegeeksuresh.ishare.repository;

import com.gmail.creativegeeksuresh.ishare.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    public User findByUid(String uid);

    public User findByUsername(String username);
    
}
