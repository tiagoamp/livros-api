package com.exemplo.api.controller;

import com.exemplo.api.model.Livro;
import com.exemplo.api.repository.LivroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/livros")
public class LivroController {

    private final LivroRepository repository;

    public LivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @Operation( summary = "Consultar Livros", description = "Consulta todos os livros registrados",
        responses = { @ApiResponse(responseCode = "200", description = "Consulta realizada") }
    )
    @GetMapping
    public ResponseEntity<List<Livro>> consultar() {
        List<Livro> livros = repository.findAll();
        return ResponseEntity.ok(livros);
    }

    @Operation( summary = "Consultar Livros por id", description = "Consulta de livro pelo identificador",
        responses = {
                @ApiResponse(responseCode = "200", description = "Consulta realizada"),
                @ApiResponse(responseCode = "404", description = "Registro não encontrado")
        }
    )
    @GetMapping("{id}")
    public ResponseEntity<Livro> consultar(@PathVariable("id") Integer id) {
        Livro livro = repository.findById(id);
        return ResponseEntity.ok(livro);
    }

    @Operation( summary = "Cadastrar novo Livro", description = "Cadastra novo livro",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cadastro realizado"),
                    @ApiResponse(responseCode = "400", description = "Requisição com dados inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<Livro> adicionar(@RequestBody Livro livro) {
        Livro inserido = repository.insert(livro);
        URI uri = URI.create(inserido.getId().toString());
        return ResponseEntity.created(uri).body(inserido);
    }

    @Operation( summary = "Alterar dados de Livro", description = "Altera daoos de um livro cadastrado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cadastro atualizado"),
                    @ApiResponse(responseCode = "400", description = "Requisição com dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<Livro> alterar(@PathVariable("id") Integer id, @RequestBody Livro livro) {
        livro.setId(id);
        Livro atualizado = repository.update(livro);
        return ResponseEntity.ok(atualizado);
    }

    @Operation( summary = "Excluir Livro", description = "Exclui um livro existente",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Exclusão realizada"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity remover(@PathVariable("id") Integer id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }

}
