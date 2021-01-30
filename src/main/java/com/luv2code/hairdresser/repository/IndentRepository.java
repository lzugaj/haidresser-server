package com.luv2code.hairdresser.repository;

import com.luv2code.hairdresser.domain.Indent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndentRepository extends JpaRepository<Indent, Long> {

//    List<Indent> findAllByUserUsername();

}
