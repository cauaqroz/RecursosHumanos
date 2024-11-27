package com.fatec.projetoCadastro.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fatec.projetoCadastro.Model.Endereco;
import com.fatec.projetoCadastro.Model.Funcionario;
import com.fatec.projetoCadastro.Model.FuncionarioDTO;
import com.fatec.projetoCadastro.Model.Usuario;
import com.fatec.projetoCadastro.Model.UsuarioDTO;
import com.fatec.projetoCadastro.Repository.EnderecoRepository;
import com.fatec.projetoCadastro.Repository.FuncionarioRepository;
import com.fatec.projetoCadastro.Repository.UsuarioRepository;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;



@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "cadastro";
    }

    @PostMapping("/cadastro")
public String cadastrar(@Valid @ModelAttribute UsuarioDTO usuarioDTO, BindingResult bindingResult,Model model) {
    
    model.addAttribute("usuario", new UsuarioDTO());
    if (bindingResult.hasErrors()) {
        model.addAttribute("usuario",  usuarioDTO);
        return "cadastro";
    }
    Usuario usuarioExistenteNome = usuarioRepository.findByName(usuarioDTO.getNome());
    Usuario usuarioExistenteEmail = usuarioRepository.findByEmail(usuarioDTO.getEmail());
    if (usuarioExistenteNome != null) {
        model.addAttribute("erroNome", "Nome de usuário já está em uso");
        model.addAttribute("usuario",  usuarioDTO);
        return "cadastro";
    }
    if (usuarioExistenteEmail != null) {
        model.addAttribute("erroEmail", "Email já está em uso");
        model.addAttribute("usuario",  usuarioDTO);
        return "cadastro";
    }


    Usuario usuario = convertDtoToUsuario(usuarioDTO);
    if (usuario.getDataNascimento() == null) {
        usuario.setDataNascimento(LocalDate.now());
    }
    Funcionario funcionario = convertDtoToFuncionario(usuarioDTO.getFuncionario());
    funcionarioRepository.save(funcionario);
    usuario.setFuncionario(funcionario);
    usuarioRepository.save(usuario);
    return "redirect:/usuarios/consulta";
}

    private Usuario convertDtoToUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento() != null ? usuarioDTO.getDataNascimento() : null);
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setSenha(usuarioDTO.getSenha());


         Endereco endereco = new Endereco();
        endereco.setRua(usuarioDTO.getEndereco().getRua());
        endereco.setNumero(usuarioDTO.getEndereco().getNumero());
        endereco.setComplemento(usuarioDTO.getEndereco().getComplemento());
        endereco.setBairro(usuarioDTO.getEndereco().getBairro());
        endereco.setCidade(usuarioDTO.getEndereco().getCidade());
        endereco.setEstado(usuarioDTO.getEndereco().getEstado());
        endereco.setCep(usuarioDTO.getEndereco().getCep());

        endereco = enderecoRepository.save(endereco);
        usuario.setEndereco(endereco);

        return usuario;
    }
private Funcionario convertDtoToFuncionario(FuncionarioDTO funcionarioDTO) {
    Funcionario funcionario = new Funcionario();
    funcionario.setCargo(funcionarioDTO.getCargo());
    funcionario.setSetor(funcionarioDTO.getSetor());
    funcionario.setSalario(funcionarioDTO.getSalario());
    funcionario.setTurno(funcionarioDTO.getTurno());
    funcionario.setContrato(funcionarioDTO.getContrato());
    funcionario.setSituacao(funcionarioDTO.getSituacao());
    return funcionario;
}

    @GetMapping("/consulta")
    public String consulta(Model model) {
        model.addAttribute("nome", "");
        return "consulta";
    }

    @PostMapping("/consulta")
    public String buscar(@RequestParam String nome, Model model) {
        List<Usuario> usuarios = usuarioRepository.findByNome(nome);
        model.addAttribute("usuarios", usuarios);
        return "lista";
    }

    @GetMapping("/listarTodos")
    public String listarTodos(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "lista";
    }
    @GetMapping("/listarAtivos")
public String listarAtivos(Model model) {
    List<Usuario> usuarios = usuarioRepository.findBySituacao("Ativo");
    model.addAttribute("usuarios", usuarios);
    return "lista";
}

@GetMapping("/listarInativos")
public String listarInativos(Model model) {
    List<Usuario> usuarios = usuarioRepository.findBySituacao("Desativado");
    model.addAttribute("usuarios", usuarios);
    return "lista";
}
}

