package com.devjansen.literaura.service;

import com.devjansen.literaura.model.dto.DadosGutendex;
import com.devjansen.literaura.model.dto.LivroDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GutendexServiceUnitTest {

    @Mock
    private ConsumoApi consumoApi;

    @Mock
    private ConverteDados converteDados;

    @InjectMocks
    private GutendexService service;

    @Test
    @DisplayName("Deve retornar vazio quando a API nao retorna resultados")
    void buscaSemResultados() {
        when(consumoApi.obterDados(anyString())).thenReturn("{}");
        when(converteDados.obterDados("{}", DadosGutendex.class))
                .thenReturn(new DadosGutendex(0, null, null, List.of()));

        var resultado = service.buscarLivro("Livro Inexistente");

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve encontrar correspondencia exata")
    void buscaExata() {
        var livroDTO = new LivroDTO(1L, "Dom Casmurro", List.of(), List.of("pt"), 100);
        var dados = new DadosGutendex(1, null, null, List.of(livroDTO));

        when(consumoApi.obterDados(anyString())).thenReturn("json");
        when(converteDados.obterDados("json", DadosGutendex.class)).thenReturn(dados);

        var resultado = service.buscarLivro("Dom Casmurro");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().titulo()).isEqualTo("Dom Casmurro");
    }

    @Test
    @DisplayName("Nao deve dar match parcial errado (Ex: Rings em Sufferings)")
    void buscaRegexBarreiras() {
        // Cenario: Buscamos "Rings", API retorna "Sufferings"
        var livroRuim = new LivroDTO(1L, "Sufferings of a Sailor", List.of(), List.of("en"), 10);
        var dados = new DadosGutendex(1, null, null, List.of(livroRuim));

        when(consumoApi.obterDados(anyString())).thenReturn("json");
        when(converteDados.obterDados("json", DadosGutendex.class)).thenReturn(dados);

        var resultado = service.buscarLivro("Rings");

        assertThat(resultado).isEmpty();
    }
}
