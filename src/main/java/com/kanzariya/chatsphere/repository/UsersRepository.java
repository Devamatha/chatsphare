package com.kanzariya.chatsphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kanzariya.chatsphere.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByEmailAndPassword(String email, String password);

	Optional<Users> findByMobileNumberAndPassword(String mobileNumber, String password);

	@Query("SELECT u.password, u.role FROM Users u WHERE u.email = :email")
	List<Object[]> findPasswordAndRoleByEmail(@Param("email") String email);

	@Query("SELECT u.user_Id, u.fullName FROM Users u WHERE u.email = :input OR u.mobileNumber=:input")
	List<Object[]> findUser_IdAndFullNameByEmailOrMobile(@Param("input") String input);

	
	@Query("SELECT u.password, u.role FROM Users u WHERE u.email = :input OR u.mobileNumber = :input")
	List<Object[]> findPasswordAndRoleByEmailOrMobile(@Param("input") String input);
	
	Users findByEmail(String email);
	
    List<Users> findByFullNameContainingIgnoreCase(String fullName);



}
