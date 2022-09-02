package com.exemplo.api.repository;

import com.exemplo.api.model.Livro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class LivroRepository {

    private int idCounter;
    private List<Livro> livros;

    public LivroRepository() {
        List<Livro> cadastrados = List.of(
                new Livro(++idCounter, "Philosophiae Naturalis Principia Mathematica", "1603863796", 1713, "Isaac Newton"),
                new Livro(++idCounter, "O Guia do Mochileiro das Galáxias", "9788599296578", 1979, "Douglas Adams"),
                new Livro(++idCounter, "Frankenstein", "8594318111", 1818, "Mary Shelley")
        );
        this.livros = new ArrayList<>(cadastrados);
    }

    public List<Livro> findAll() {
        List<Livro> copia = new ArrayList<>(livros);
        copia.sort(Comparator.comparing(Livro::getId));
        return copia;
    }

    public Livro findById(Integer id) {
        return livros.stream().filter(l -> l.getId().equals(id))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public Livro insert(Livro novo) {
        novo.setId(++idCounter);
        livros.add(novo);
        return novo;
    }

    public Livro update(Livro alterado) {
        boolean isExcluido = livros.removeIf(l -> l.getId().equals(alterado.getId()));
        if (!isExcluido)
            throw new IllegalArgumentException();
        livros.add(alterado);
        return alterado;
    }

    public void delete(Integer id) {
        livros.removeIf(l -> l.getId().equals(id));
    }

}
