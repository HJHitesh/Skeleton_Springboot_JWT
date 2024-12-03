package com.travelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelo.model.Role;
import com.travelo.model.User;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {

}
