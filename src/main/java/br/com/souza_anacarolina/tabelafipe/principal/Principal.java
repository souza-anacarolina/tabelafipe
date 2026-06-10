package br.com.souza_anacarolina.tabelafipe.principal;

import br.com.souza_anacarolina.tabelafipe.model.DadosVeiculo;
import br.com.souza_anacarolina.tabelafipe.model.ModelosResponse;
import br.com.souza_anacarolina.tabelafipe.service.ConsumoApi;
import br.com.souza_anacarolina.tabelafipe.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

        var tipo = URLEncoder.encode(scanner.nextLine().toLowerCase(), StandardCharsets.UTF_8);

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

        System.out.println("\nDe acordo com a listagem acima informe o código da marca que deseja visualizar");
        var codigoMarca = URLEncoder.encode(scanner.nextLine().toLowerCase(), StandardCharsets.UTF_8);
        json = consumoApi.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarca + "/modelos");

        ModelosResponse response = converteDados.obterDados(json, ModelosResponse.class);

        DadosVeiculo[] modelos = response.modelos().toArray(new DadosVeiculo[0]);

        List<DadosVeiculo> modelosOrdenados = Arrays.stream(modelos)
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .collect(Collectors.toList());

        int codigo = Integer.parseInt(codigoMarca);

        String nomeMarcaEscolhida = marcasOrdenadas.stream()
                        .filter(m -> m.codigo() == codigo)
                                .map(DadosVeiculo::nome)
                                        .findFirst().orElse("Marca não encontrada");

        System.out.println("\nMODELOS DA MARCA " + nomeMarcaEscolhida + "\n");
        for (DadosVeiculo m : modelosOrdenados) {
            System.out.println(m.codigo() + " - " + m.nome());
        }


    }
}
