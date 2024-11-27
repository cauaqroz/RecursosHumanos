package com.fatec.projetoCadastro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatec.projetoCadastro.Model.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.nome LIKE %:nome%")
    List<Usuario> findByNome(@Param("nome") String nome);

    

    @Query("SELECT u FROM Usuario u JOIN u.funcionario f WHERE f.situacao = :situacao")
    List<Usuario> findBySituacao(@Param("situacao") String situacao);

    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome")
    Usuario findByName(@Param("nome") String nome);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario findByEmail(@Param("email") String email);
}
