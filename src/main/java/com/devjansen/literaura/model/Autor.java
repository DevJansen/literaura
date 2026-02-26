package com.devjansen.literaura.model;


import com.devjansen.literaura.model.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 500)
    private String nome;

    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<com.devjansen.literaura.model.Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(AutorDTO autorDTO) {
        this.nome = autorDTO.nome();
        this.anoNascimento = autorDTO.anoNascimento();
        this.anoFalecimento = autorDTO.anoFalecimento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<com.devjansen.literaura.model.Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<com.devjansen.literaura.model.Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor: " + nome +
                " (Nasc: " + (anoNascimento != null ? anoNascimento : "?") +
                " - Falec: " + (anoFalecimento != null ? anoFalecimento : "?") + ")";
    }
}
