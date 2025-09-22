package com.sistema.admin.Disciplina.api;

import com.sistema.admin.Disciplina.api.dto.DisciplinaDTO;
import com.sistema.admin.Disciplina.aplicacao.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO> cadastrar(@Valid @RequestBody DisciplinaDTO dto) {
        DisciplinaDTO salvo = disciplinaService.cadastrar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> listarTodas() {
        return ResponseEntity.ok(disciplinaService.listarTodas());
    }
}
