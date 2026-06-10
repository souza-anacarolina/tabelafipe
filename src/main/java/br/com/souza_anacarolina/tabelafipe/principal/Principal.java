package br.com.souza_anacarolina.tabelafipe.principal;

import br.com.souza_anacarolina.tabelafipe.model.DadosModelo;
import br.com.souza_anacarolina.tabelafipe.model.DadosVeiculo;
import br.com.souza_anacarolina.tabelafipe.model.ModelosResponse;
import br.com.souza_anacarolina.tabelafipe.service.ConsumoApi;
import br.com.souza_anacarolina.tabelafipe.service.ConverteDados;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados converteDados = new ConverteDados();

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("OPÇÕES DISPONÍVEIS PARA CONSULTA\n\n" +
                "CARRO\n" +
                "CAMINHÃO\n" +
                "MOTO\n\n" +
                "Com qual opção gostaria de prosseguir?");

        var tipo = scanner.nextLine().toLowerCase();

        if (tipo.equals("carro")) tipo = "carros";
        if (tipo.equals("moto")) tipo = "motos";
        if (tipo.equals("caminhão") || tipo.equals("caminhao")) tipo = "caminhoes";
        var json = consumoApi.obterDados(ENDERECO + tipo + "/marcas");

        DadosVeiculo[] marcas = converteDados.obterDados(json, DadosVeiculo[].class);

        List<DadosVeiculo> marcasOrdenadas = Arrays.stream(marcas)
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .collect(Collectors.toList());

        System.out.println("\nMARCAS DISPONÍVEIS PARA CONSULTA\n");
        for (DadosVeiculo m : marcasOrdenadas) {
            System.out.println(m.codigo() + " - " + m.nome());
        }

        System.out.println("\nInforme o nome da marca que deseja visualizar os modelos disponíveis\n");
        var nomeMarca = scanner.nextLine();

        String codigoMarcaEscolhida = String.valueOf(marcasOrdenadas.stream()
                .filter(m -> m.nome().contains(nomeMarca))
                .map(DadosVeiculo::codigo)
                .findFirst().orElse(null));

        json = consumoApi.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarcaEscolhida + "/modelos");

        ModelosResponse responseModelos = converteDados.obterDados(json, ModelosResponse.class);

        DadosVeiculo[] modelos = responseModelos.modelos().toArray(new DadosVeiculo[0]);

        List<DadosVeiculo> modelosOrdenados = Arrays.stream(modelos)
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .collect(Collectors.toList());


        System.out.println("\nMODELOS DA MARCA " + nomeMarca + "\n");
        for (DadosVeiculo m : modelosOrdenados) {
            System.out.println(m.codigo() + " - " + m.nome());
        }

        System.out.println("\nInforme o modelo desejado\n");
        var nomeModelo = scanner.nextLine();

        String codigoModeloEscolhido = String.valueOf(modelosOrdenados.stream()
                .filter(m -> m.nome().contains(nomeModelo))
                .map(DadosVeiculo::codigo)
                .findFirst().orElse(null));

        json = consumoApi.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarcaEscolhida + "/modelos/" + codigoModeloEscolhido + "/anos");

        DadosModelo[] anosModelo = converteDados.obterDados(json, DadosModelo[].class);

        List<DadosModelo> anosOrdenados = Arrays.stream(anosModelo)
                .sorted(Comparator.comparing(DadosModelo::codigo))
                .collect(Collectors.toList());

        System.out.println("\nANOS DO MODELO " + nomeModelo);
        for (DadosModelo m : anosOrdenados) {
            System.out.println(m.codigo() + " - " + m.nome());
        }

    }
}
