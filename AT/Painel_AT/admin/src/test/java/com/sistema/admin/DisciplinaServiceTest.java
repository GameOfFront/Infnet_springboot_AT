package com.sistema.admin;

import com.sistema.admin.Disciplina.api.dto.DisciplinaDTO;
import com.sistema.admin.Disciplina.aplicacao.DisciplinaService;
import com.sistema.admin.Disciplina.dominio.Disciplina;
import com.sistema.admin.Disciplina.infra.DisciplinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarDisciplinaComSucesso() {
        DisciplinaDTO dto = new DisciplinaDTO(null, "MAT101", "Matemática");

        when(disciplinaRepository.findByCodigo("MAT101")).thenReturn(Optional.empty());
        when(disciplinaRepository.findByNome("Matemática")).thenReturn(Optional.empty());

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setCodigo("MAT101");
        disciplina.setNome("Matemática");

        when(disciplinaRepository.save(any())).thenReturn(disciplina);

        DisciplinaDTO salvo = disciplinaService.cadastrar(dto);

        assertNotNull(salvo.id());
        assertEquals("MAT101", salvo.codigo());
    }

    @Test
    void deveLancarErroQuandoCodigoJaExistir() {
        DisciplinaDTO dto = new DisciplinaDTO(null, "MAT101", "Matemática");
        when(disciplinaRepository.findByCodigo("MAT101")).thenReturn(Optional.of(new Disciplina()));

        assertThrows(ResponseStatusException.class, () -> disciplinaService.cadastrar(dto));
    }

    @Test
    void deveLancarErroQuandoNomeJaExistir() {
        DisciplinaDTO dto = new DisciplinaDTO(null, "MAT102", "Português");
        when(disciplinaRepository.findByCodigo("MAT102")).thenReturn(Optional.empty());
        when(disciplinaRepository.findByNome("Português")).thenReturn(Optional.of(new Disciplina()));

        assertThrows(ResponseStatusException.class, () -> disciplinaService.cadastrar(dto));
    }
}
