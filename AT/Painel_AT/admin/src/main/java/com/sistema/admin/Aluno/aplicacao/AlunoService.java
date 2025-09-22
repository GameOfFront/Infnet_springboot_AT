package com.sistema.admin.Aluno.aplicacao;

import com.sistema.admin.Aluno.api.dto.AlunoDTO;
import com.sistema.admin.Aluno.dominio.Aluno;
import com.sistema.admin.Aluno.infra.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public AlunoDTO cadastrar(AlunoDTO dto) {
        // validações de unicidade
        Optional<Aluno> existenteCpf = alunoRepository.findByCpf(dto.cpf());
        if (existenteCpf.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }

        Optional<Aluno> existenteEmail = alunoRepository.findByEmail(dto.email());
        if (existenteEmail.isPresent()) {
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

        return new AlunoDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getEndereco()
        );
    }

    @Transactional(readOnly = true)
    public List<AlunoDTO> listarTodos() {
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
