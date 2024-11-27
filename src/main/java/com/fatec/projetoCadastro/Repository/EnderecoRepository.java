package com.fatec.projetoCadastro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.projetoCadastro.Model.Endereco;


public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
