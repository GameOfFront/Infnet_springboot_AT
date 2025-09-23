package com.sistema.admin.Aluno.aplicacao;

import com.sistema.admin.Aluno.api.dto.AlunoCreateDTO;
import com.sistema.admin.Aluno.api.dto.AlunoDTO;
import com.sistema.admin.Aluno.dominio.Aluno;
import com.sistema.admin.Aluno.infra.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoDTO cadastrar(AlunoCreateDTO dto) {
        System.out.println("➡️ [DEBUG] Tentando cadastrar aluno: " + dto);

        // validações de unicidade
        Optional<Aluno> existenteCpf = alunoRepository.findByCpf(dto.cpf());
        if (existenteCpf.isPresent()) {
            System.out.println("❌ [DEBUG] CPF já existe: " + dto.cpf());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }

        Optional<Aluno> existenteEmail = alunoRepository.findByEmail(dto.email());
        if (existenteEmail.isPresent()) {
            System.out.println("❌ [DEBUG] Email já existe: " + dto.email());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }

        // cria entidade
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setEmail(dto.email());
        aluno.setTelefone(dto.telefone());
        aluno.setEndereco(dto.endereco());

        aluno = alunoRepository.save(aluno);
        System.out.println("✅ [DEBUG] Aluno salvo com ID: " + aluno.getId());

        return new AlunoDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getEndereco()
        );
    }

    public List<AlunoDTO> listarTodos() {
        System.out.println("➡️ [DEBUG] Buscando todos os alunos");
        return alunoRepository.findAll()
                .stream()
                .map(aluno -> new AlunoDTO(
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getCpf(),
                        aluno.getEmail(),
                        aluno.getTelefone(),
                        aluno.getEndereco()
                ))
                .toList();
    }
}
