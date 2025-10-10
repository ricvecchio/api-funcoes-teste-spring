package com.funcoes.repository;

import com.funcoes.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query("SELECT c FROM Conta c JOIN FETCH c.cliente")
    List<Conta> findAllWithCliente();
}
