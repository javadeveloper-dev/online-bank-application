package com.banking_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking_app.entity.User;

@Repository
public interface IUserRegisterDAO extends JpaRepository<User, Long> {

	

}
