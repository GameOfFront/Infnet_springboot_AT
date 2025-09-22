package com.sistema.admin;

import com.sistema.admin.Aluno.dominio.Aluno;
import com.sistema.admin.Aluno.infra.AlunoRepository;
import com.sistema.admin.Disciplina.dominio.Disciplina;
import com.sistema.admin.Disciplina.infra.DisciplinaRepository;
import com.sistema.admin.Matricula.api.dto.MatriculaDTO;
import com.sistema.admin.Matricula.aplicacao.MatriculaService;
import com.sistema.admin.Matricula.dominio.Matricula;
import com.sistema.admin.Matricula.infra.MatriculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private MatriculaService matriculaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 🔹 Teste de alocação
    @Test
    void deveAlocarAlunoEmDisciplina() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(matriculaRepository.findByAlunoIdAndDisciplinaId(1L, 1L)).thenReturn(Optional.empty());

        Matricula matricula = new Matricula();
        matricula.setId(1L);
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setNota(BigDecimal.ZERO);

        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        MatriculaDTO dto = new MatriculaDTO(null, 1L, 1L, BigDecimal.ZERO);
        MatriculaDTO salvo = matriculaService.alocar(dto);

        assertNotNull(salvo);
        assertEquals(1L, salvo.alunoId());
        assertEquals(1L, salvo.disciplinaId());
    }

    // 🔹 Teste de atribuição de nota
    @Test
    void deveAtribuirNota() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);

        Matricula matricula = new Matricula();
        matricula.setId(1L);
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setNota(BigDecimal.ZERO);

        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(matricula));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        MatriculaDTO atualizado = matriculaService.atribuirNota(1L, 8.5);

        assertEquals(BigDecimal.valueOf(8.5), atualizado.nota());
    }

    // 🔹 Teste listar aprovados
    @Test
    void deveListarAprovados() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);

        Matricula matricula = new Matricula();
        matricula.setId(1L);
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setNota(BigDecimal.valueOf(9));

        when(matriculaRepository.findAprovados(1L, BigDecimal.valueOf(7.0)))
                .thenReturn(List.of(matricula));

        List<MatriculaDTO> aprovados = matriculaService.listarAprovados(1L);

        assertFalse(aprovados.isEmpty());
        assertEquals(1L, aprovados.get(0).alunoId());
    }

    // 🔹 Teste listar reprovados
    @Test
    void deveListarReprovados() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);

        Disciplina disciplina = new Disciplina();
        disciplina.setId(2L);

        Matricula matricula = new Matricula();
        matricula.setId(2L);
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setNota(BigDecimal.valueOf(5));

        when(matriculaRepository.findReprovados(2L, BigDecimal.valueOf(7.0)))
                .thenReturn(List.of(matricula));

        List<MatriculaDTO> reprovados = matriculaService.listarReprovados(2L);

        assertFalse(reprovados.isEmpty());
        assertEquals(2L, reprovados.get(0).alunoId());
    }

    // 🔹 Teste de exceção ao não encontrar aluno
    @Test
    void deveLancarExcecaoQuandoAlunoNaoEncontrado() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        MatriculaDTO dto = new MatriculaDTO(null, 99L, 1L, BigDecimal.ZERO);

        assertThrows(ResponseStatusException.class, () -> matriculaService.alocar(dto));
    }

    // 🔹 Teste de exceção ao não encontrar disciplina
    @Test
    void deveLancarExcecaoQuandoDisciplinaNaoEncontrada() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(99L)).thenReturn(Optional.empty());

        MatriculaDTO dto = new MatriculaDTO(null, 1L, 99L, BigDecimal.ZERO);

        assertThrows(ResponseStatusException.class, () -> matriculaService.alocar(dto));
    }

    // 🔹 Teste de duplicidade
    @Test
    void deveLancarExcecaoQuandoMatriculaDuplicada() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(matriculaRepository.findByAlunoIdAndDisciplinaId(1L, 1L))
                .thenReturn(Optional.of(new Matricula()));

        MatriculaDTO dto = new MatriculaDTO(null, 1L, 1L, BigDecimal.ZERO);

        assertThrows(ResponseStatusException.class, () -> matriculaService.alocar(dto));
    }
}
