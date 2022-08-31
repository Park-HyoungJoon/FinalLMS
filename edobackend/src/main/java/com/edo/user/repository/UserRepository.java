package com.edo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edo.user.entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,String>{
	Optional<Users> findByEmail(String email);
	boolean existsByEmail(String email);
}
