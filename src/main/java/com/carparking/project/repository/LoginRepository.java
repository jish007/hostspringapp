package com.carparking.project.repository;

import com.carparking.project.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends CrudRepository<User, String> {

    public User findByEmailAndPassword(String email,String passWord);

    public User findByEmail(String email);

    @Query(value = "SELECT email FROM login WHERE active = 'ACTIVE' and rolename = 'ADMIN_USER'", nativeQuery = true)
    public String getActiveUser();

    @Query(value = "SELECT email FROM login WHERE is_banned = true and rolename = 'USER'", nativeQuery = true)
    List<String> bannedUsers();
}
