package com.luv2code.hairdresser.repository;

import com.luv2code.hairdresser.domain.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
