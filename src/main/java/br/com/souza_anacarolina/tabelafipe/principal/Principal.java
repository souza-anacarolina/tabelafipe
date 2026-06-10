package br.com.souza_anacarolina.tabelafipe.principal;

import br.com.souza_anacarolina.tabelafipe.model.*;
import br.com.souza_anacarolina.tabelafipe.service.ConsumoApi;
import br.com.souza_anacarolina.tabelafipe.service.ConverteDados;

import java.text.Normalizer;
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

    private String normalize(String s) {
        if (s == null) return "";
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }


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

        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nMARCAS DISPONÍVEIS PARA CONSULTA\n");
        System.out.println("=".repeat(50) + "\n");

        for (DadosVeiculo m : marcasOrdenadas) {
            System.out.println(m.codigo() + " - " + m.nome());
        }

        System.out.println("\nInforme o nome da marca que deseja consultar\n");
        var nomeMarca = scanner.nextLine();

        String codigoMarcaEscolhida = String.valueOf(marcasOrdenadas.stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeMarca.toLowerCase()))
                .map(DadosVeiculo::codigo)
                .findFirst().orElse(null));

        json = consumoApi.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarcaEscolhida + "/modelos");

        ModelosResponse responseModelos = converteDados.obterDados(json, ModelosResponse.class);

        DadosVeiculo[] modelos = responseModelos.modelos().toArray(new DadosVeiculo[0]);

        List<DadosVeiculo> modelosOrdenados = Arrays.stream(modelos)
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .collect(Collectors.toList());

        System.out.println("\nInforme um trecho do modelo que deseja consultar\n");
        String trechoModelo = scanner.nextLine();

        List<DadosVeiculo> encontrados = modelosOrdenados.stream()
                .filter(m -> normalize(m.nome()).contains(normalize(trechoModelo)))
                .collect(Collectors.toList());

        while (encontrados.isEmpty()) {
            System.out.println("Nenhum modelo encontrado para: " + trechoModelo);
            break;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nMODELOS ENCONTRADOS: \n");
        System.out.println("=".repeat(50) + "\n");

        for (int i = 0; i < encontrados.size(); i++) {
            DadosVeiculo d = encontrados.get(i);
            System.out.println(d.nome());
        }

        System.out.println("\nEscolha um modelo da lista");
        String nomeModeloEscolhido = scanner.nextLine();

        DadosVeiculo modeloEscolhido = encontrados.stream()
                .filter(m -> normalize(m.nome()).contains(normalize(nomeModeloEscolhido)))
                .findFirst()
                .orElse(null);

        if (modeloEscolhido == null) {
            System.out.println("Modelo não encontrado na lista!");
            return;
        }

        String codigoModeloEscolhido = String.valueOf(modeloEscolhido.codigo());

        json = consumoApi.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarcaEscolhida + "/modelos/" + codigoModeloEscolhido + "/anos");

        DadosModelo[] anosModelo = converteDados.obterDados(json, DadosModelo[].class);

        List<DadosModelo> anosOrdenados = Arrays.stream(anosModelo)
                .sorted(Comparator.comparing(DadosModelo::codigo))
                .collect(Collectors.toList());

        System.out.println("\n" + "=".repeat(80));
        System.out.println("INFORMAÇÕES DO MODELO: " + modeloEscolhido.nome());
        System.out.println("=".repeat(80) + "\n");

        for (DadosModelo ano : anosOrdenados) {
            String codigoAno = ano.codigo();

            json = consumoApi.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarcaEscolhida + "/modelos/" + codigoModeloEscolhido + "/anos/" + codigoAno);

            DetalheVeiculo detalheVeiculo = converteDados.obterDados(json, DetalheVeiculo.class);
            InformacoesModelo informacoesModelo = new InformacoesModelo(detalheVeiculo);

            System.out.println(informacoesModelo);
            System.out.println("-".repeat(80) + "\n");
        }
    }
}
