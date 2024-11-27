package com.fatec.projetoCadastro.Repository;
// FuncionarioRepository
import org.springframework.data.jpa.repository.JpaRepository;
import com.fatec.projetoCadastro.Model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
}
