package com.example.demo.sender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderTemplateRepository extends JpaRepository<SenderTemplate, Long> {

}
