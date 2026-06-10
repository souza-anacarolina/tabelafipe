package br.com.souza_anacarolina.tabelafipe.model;

public class InformacoesVeiculo {

    String Valor;
    String Marca;
    String Modelo;
    Long anoModelo;
    String Combustivel;
    String CodigoFipe;
    String MesReferencia;

    public InformacoesVeiculo(DetalheVeiculo detalheVeiculo) {
        this.anoModelo = detalheVeiculo.anoModelo();
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

    public Long getAnoModelo() {
        return anoModelo;
    }

    public String getModelo() {
        return Modelo;
    }

    @Override
    public String toString() {
        return "Informacoes do Veiculo\n" +
                "Valor=" + Valor + "\n" +
                "Marca='" + Marca +    "\n" +
                "Modelo='" + Modelo  + "\n" +
                "anoModelo=" + anoModelo + "\n" +
                "Combustivel='" + Combustivel  + "\n" +
                "CodigoFipe='" + CodigoFipe  + "\n" +
                "MesReferencia='" + MesReferencia
                ;}
}
