package com.springJPA.Spring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepoository  extends JpaRepository<Student, Long> {
}
