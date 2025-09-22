package com.sistema.admin.Disciplina.aplicacao;

import com.sistema.admin.Disciplina.api.dto.DisciplinaDTO;
import com.sistema.admin.Disciplina.dominio.Disciplina;
import com.sistema.admin.Disciplina.infra.DisciplinaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Transactional
    public DisciplinaDTO cadastrar(DisciplinaDTO dto) {
        // validações de unicidade
        Optional<Disciplina> existenteCodigo = disciplinaRepository.findByCodigo(dto.codigo());
        if (existenteCodigo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código já cadastrado");
        }

        Optional<Disciplina> existenteNome = disciplinaRepository.findByNome(dto.nome());
        if (existenteNome.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome já cadastrado");
        }

        // cria entidade
        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo(dto.codigo());
        disciplina.setNome(dto.nome());

        disciplina = disciplinaRepository.save(disciplina);

        return new DisciplinaDTO(
                disciplina.getId(),
                disciplina.getCodigo(),
                disciplina.getNome()
        );
    }

    @Transactional(readOnly = true)
    public List<DisciplinaDTO> listarTodas() {
        return disciplinaRepository.findAll()
                .stream()
                .map(d -> new DisciplinaDTO(
                        d.getId(),
                        d.getCodigo(),
                        d.getNome()
                ))
                .toList();
    }
}
