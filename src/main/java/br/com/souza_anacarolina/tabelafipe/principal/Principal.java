package br.com.souza_anacarolina.tabelafipe.principal;

import br.com.souza_anacarolina.tabelafipe.model.DadosVeiculo;
import br.com.souza_anacarolina.tabelafipe.service.ConsumoApi;
import br.com.souza_anacarolina.tabelafipe.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados converteDados = new ConverteDados();

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1";

    public void exibeMenu(){
        System.out.println("OPÇÕES DISPONÍVEIS PARA CONSULTA\n" +
                "CARRO\n" +
                "CAMINHÃO\n" +
                "MOTO\n\n" +
                "Com qual opção gostaria de prosseguir?");

        var tipo = URLEncoder.encode(scanner.nextLine(), StandardCharsets.UTF_8);
        var json = consumoApi.obterDados(ENDERECO + tipo);
        DadosVeiculo dadosVeiculo = converteDados.obterDados(json, DadosVeiculo.class);
        System.out.println(dadosVeiculo);

    }
}
