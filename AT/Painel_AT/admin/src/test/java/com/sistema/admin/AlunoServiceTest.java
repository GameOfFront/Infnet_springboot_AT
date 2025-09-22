package com.sistema.admin;

import com.sistema.admin.Aluno.api.dto.AlunoDTO;
import com.sistema.admin.Aluno.aplicacao.AlunoService;
import com.sistema.admin.Aluno.dominio.Aluno;
import com.sistema.admin.Aluno.infra.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarAlunoComSucesso() {
        AlunoDTO dto = new AlunoDTO(null, "João", "123", "joao@email.com", "1199999", "Rua A");

        when(alunoRepository.findByCpf("123")).thenReturn(Optional.empty());
        when(alunoRepository.findByEmail("joao@email.com")).thenReturn(Optional.empty());

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João");
        aluno.setCpf("123");
        aluno.setEmail("joao@email.com");
        aluno.setTelefone("1199999");
        aluno.setEndereco("Rua A");

        when(alunoRepository.save(any())).thenReturn(aluno);

        AlunoDTO salvo = alunoService.cadastrar(dto);

        assertNotNull(salvo.id());
        assertEquals("João", salvo.nome());
    }

    @Test
    void deveLancarErroQuandoCpfJaExistir() {
        AlunoDTO dto = new AlunoDTO(null, "Maria", "123", "maria@email.com", "1198888", "Rua B");
        when(alunoRepository.findByCpf("123")).thenReturn(Optional.of(new Aluno()));

        assertThrows(ResponseStatusException.class, () -> alunoService.cadastrar(dto));
    }

    @Test
    void deveLancarErroQuandoEmailJaExistir() {
        AlunoDTO dto = new AlunoDTO(null, "Pedro", "456", "pedro@email.com", "1197777", "Rua C");
        when(alunoRepository.findByCpf("456")).thenReturn(Optional.empty());
        when(alunoRepository.findByEmail("pedro@email.com")).thenReturn(Optional.of(new Aluno()));

        assertThrows(ResponseStatusException.class, () -> alunoService.cadastrar(dto));
    }
}
