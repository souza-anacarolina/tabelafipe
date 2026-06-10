package br.com.souza_anacarolina.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModelosResponse(List<DadosVeiculo> modelos) {
}
