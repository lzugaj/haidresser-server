package com.luv2code.hairdresser.repository;

import com.luv2code.hairdresser.domain.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {

}
