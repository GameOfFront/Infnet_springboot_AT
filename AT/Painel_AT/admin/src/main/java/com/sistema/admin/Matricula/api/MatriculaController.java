package com.sistema.admin.Matricula.api;

import com.sistema.admin.Matricula.api.dto.MatriculaDTO;
import com.sistema.admin.Matricula.aplicacao.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> alocar(@Valid @RequestBody MatriculaDTO dto) {
        MatriculaDTO salvo = matriculaService.alocar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}/nota")
    public ResponseEntity<MatriculaDTO> atribuirNota(@PathVariable Long id, @RequestParam Double nota) {
        MatriculaDTO atualizado = matriculaService.atribuirNota(id, nota);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/aprovados/{disciplinaId}")
    public ResponseEntity<List<MatriculaDTO>> listarAprovados(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(matriculaService.listarAprovados(disciplinaId));
    }

    @GetMapping("/reprovados/{disciplinaId}")
    public ResponseEntity<List<MatriculaDTO>> listarReprovados(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(matriculaService.listarReprovados(disciplinaId));
    }
}
