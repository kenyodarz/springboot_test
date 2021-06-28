package com.bykenyodarz.springboot_test.repositories;

import com.bykenyodarz.springboot_test.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
}
