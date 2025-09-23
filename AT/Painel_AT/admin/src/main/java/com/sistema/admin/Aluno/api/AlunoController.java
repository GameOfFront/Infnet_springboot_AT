package com.sistema.admin.Aluno.api;

import com.sistema.admin.Aluno.api.dto.AlunoCreateDTO;
import com.sistema.admin.Aluno.api.dto.AlunoDTO;
import com.sistema.admin.Aluno.aplicacao.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> cadastrar(@Valid @RequestBody AlunoCreateDTO dto) {
        AlunoDTO salvo = alunoService.cadastrar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }
}
