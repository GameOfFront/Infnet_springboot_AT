package com.sistema.admin.Matricula.aplicacao;

import com.sistema.admin.Aluno.dominio.Aluno;
import com.sistema.admin.Aluno.infra.AlunoRepository;
import com.sistema.admin.Disciplina.dominio.Disciplina;
import com.sistema.admin.Disciplina.infra.DisciplinaRepository;
import com.sistema.admin.Matricula.api.dto.MatriculaDTO;
import com.sistema.admin.Matricula.dominio.Matricula;
import com.sistema.admin.Matricula.infra.MatriculaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlunoRepository alunoRepository,
                            DisciplinaRepository disciplinaRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Transactional
    public MatriculaDTO alocar(MatriculaDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));

        if (matriculaRepository.findByAlunoIdAndDisciplinaId(dto.alunoId(), dto.disciplinaId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Aluno já está matriculado nesta disciplina");
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setNota(dto.nota());

        matricula = matriculaRepository.save(matricula);

        return new MatriculaDTO(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getDisciplina().getId(),
                matricula.getNota()
        );
    }

    @Transactional
    public MatriculaDTO atribuirNota(Long matriculaId, Double nota) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        matricula.setNota(BigDecimal.valueOf(nota));
        matricula.setAtualizadoEm(OffsetDateTime.now());

        matricula = matriculaRepository.save(matricula);

        return new MatriculaDTO(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getDisciplina().getId(),
                matricula.getNota()
        );
    }

    @Transactional(readOnly = true)
    public List<MatriculaDTO> listarAprovados(Long disciplinaId) {
        return matriculaRepository.findAprovados(disciplinaId, BigDecimal.valueOf(7.0))
                .stream()
                .map(m -> new MatriculaDTO(
                        m.getId(),
                        m.getAluno().getId(),
                        m.getDisciplina().getId(),
                        m.getNota()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaDTO> listarReprovados(Long disciplinaId) {
        return matriculaRepository.findReprovados(disciplinaId, BigDecimal.valueOf(7.0))
                .stream()
                .map(m -> new MatriculaDTO(
                        m.getId(),
                        m.getAluno().getId(),
                        m.getDisciplina().getId(),
                        m.getNota()
                ))
                .toList();
    }
}
