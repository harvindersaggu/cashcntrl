package com.cashcontrol.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cashcontrol.entity.Authority;

@Repository
public interface UserRolesRepository extends CrudRepository<Authority, Long> {
	
}