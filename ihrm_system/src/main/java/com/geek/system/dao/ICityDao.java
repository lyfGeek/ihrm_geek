package com.geek.system.dao;

import com.geek.domain.system.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICityDao extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {
}
