package br.com.souza_anacarolina.tabelafipe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InformacoesModelo {

    String Valor;
    String Marca;
    String Modelo;
    int AnoModelo;
    String Combustivel;
    String CodigoFipe;
    String MesReferencia;
    LocalDateTime dataConsulta = LocalDateTime.now();
    Locale ptBr = new Locale("pt", "BR");
    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy HH:mm", ptBr);

    public InformacoesModelo(DetalheVeiculo detalheVeiculo) {
        this.AnoModelo = detalheVeiculo.AnoModelo();
        this.Marca = detalheVeiculo.Marca();
        this.Modelo = detalheVeiculo.Modelo();
        this.CodigoFipe = detalheVeiculo.CodigoFipe();
        this.Combustivel = detalheVeiculo.Combustivel();
        this.MesReferencia = detalheVeiculo.MesReferencia();
        this.Valor = detalheVeiculo.Valor();
    }

    public String getMesReferencia() {
        return MesReferencia;
    }

    public String getCodigoFipe() {
        return CodigoFipe;
    }

    public String getCombustivel() {
        return Combustivel;
    }

    public String getValor() {
        return Valor;
    }

    public String getMarca() {
        return Marca;
    }

    public int getAnoModelo() {
        return AnoModelo;
    }

    public String getModelo() {
        return Modelo;
    }

    @Override
    public String toString() {
        return
                "Mês de Referência: " + MesReferencia + "\n" +
                        "Código Fipe: " + CodigoFipe + "\n" +
                        "Marca: " + Marca + "\n" +
                        "Modelo: " + Modelo + "\n" +
                        "Ano Modelo: " + AnoModelo + "\n" +
                        "Combustível: " + Combustivel + "\n" +
                        "Data da consulta: " + dataConsulta.format(formatador) + "\n" +
                        "Preço Médio: " + Valor
                ;
    }
}
