package br.com.souza_anacarolina.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DetalheVeiculo(String Valor,
                             String Marca,
                             String Modelo,
                             int AnoModelo,
                             String Combustivel,
                             String CodigoFipe,
                             String MesReferencia) {
}
