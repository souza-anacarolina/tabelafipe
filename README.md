# tabelaFIPE — cliente simples para consultar a API FIPE

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

Uma aplicação de linha de comando (CLI) desenvolvida em Java que consome a [API FIPE da Parallelum](https://parallelum.com.br/fipe/api/v1/). O programa permite listar marcas, buscar modelos por trechos do nome (ignorando maiúsculas/minúsculas e acentos) e, ao selecionar um modelo específico, detalha as informações de preço para todos os anos disponíveis.

---

## 📋 Pré-requisitos

Para compilar e executar este projeto, você precisará de:
* **Java 11 ou superior**: O código utiliza recursos modernos da API de `String` e outras funcionalidades introduzidas a partir do Java 11.
* **Maven**: Para gerenciamento de dependências (ou utilize o wrapper incluído `mvnw` / `mvnw.cmd`).
* **Conexão com a Internet**: Necessária para que a aplicação consuma a API externa de forma dinâmica.

---

## 🚀 Como Executar

### 🛠️ Via IDE (IntelliJ IDEA, Eclipse, VS Code)
1. Importe o projeto `tabelafipe` como um projeto Maven.
2. Execute a classe que inicializa a aplicação e exibe o fluxo de resultados:
   `br.com.souza_anacarolina.tabelafipe.TabelafipeApplication`

### 💻 Via Linha de Comando (Windows PowerShell / Terminal)
Abra o terminal na raiz do projeto e execute os comandos abaixo.

1. **Compilar e empacotar o projeto:**
   ```powershell
   .\mvnw.cmd -DskipTests package
   
2. **Executar usando o plugin do Maven:**
   ```powershell
   .\mvnw.cmd exec:java -Dexec.mainClass="br.com.souza_anacarolina.tabelafipe.TabelafipeApplication"
   
3. **Alternativa (executando direto das classes compiladas):**
    ```powershell
   java -cp target\classes br.com.souza_anacarolina.tabelafipe.TabelafipeApplication

(No Linux/macOS, utilize ./mvnw em vez de .\mvnw.cmd)

## 🔄 Fluxo de Uso e Interação

A interação com o terminal segue o seguinte fluxo estruturado:

1. **Seleção de Tipo:** O programa solicita o tipo do veículo: CARRO, MOTO ou CAMINHÃO. (O sistema aceita variações de escrita e faz a conversão interna).
2. **Escolha da Marca:** O sistema lista as marcas disponíveis. Você deve digitar o nome (ou parte do nome) da marca desejada.
3. **Filtro de Modelo:** Quando o sistema solicitar "Informe um trecho do modelo que deseja consultar", digite o termo de busca(Ex: Ducato Minibus, Minibus, Palio).
4. **Resultado da Busca:** Graças ao sistema de busca flexível, entradas como palio, PÁLIO ou Pálio retornam os mesmos resultados. Todos os modelos correspondentes serão exibidos em uma lista.
5. **Detalhamento por Ano:** O usuário informa o modelo exato dentre os exibidos. O programa automaticamente descobre todos os anos disponíveis para aquele veículo e faz requisições em lote para trazer o preço e detalhes de cada ano.

## 🔍 Comportamento da Busca e Normalização

Para garantir uma excelente experiência de uso no terminal, o projeto implementa o método normalize(String s) utilizando a classe java.text.Normalizer. Isso garante que:

**o** Todo o texto seja convertido para letras minúsculas.

**o** Todos os acentos e caracteres especiais sejam removidos do critério de busca.

Isso significa que o usuário não precisa se preocupar com a grafia exata com acentuação para encontrar o veículo desejado.

## 🏗️ Arquitetura e Estrutura do Código
Abaixo estão os principais pacotes e classes para entender a estrutura e as responsabilidades do projeto:

### 🎮 Entrada e Exibição
**o** br.com.souza_anacarolina.tabelafipe.TabelafipeApplication

Ponto de entrada (main) da aplicação. É a classe responsável por rodar o projeto, interagir com o usuário e exibir os menus, opções e resultados no console.

### 🧠 Regras de Negócio e Processamento
**o** br.com.souza_anacarolina.tabelafipe.principal.Principal

Classe core encarregada de processar a lógica pesada dos códigos.

Armazena a URL base da API (ENDERECO).

Contém o método normalize(String s) e a lógica de filtragem estruturada de consultas, marcas, modelos e anos.

### ⚙️ Serviços e Infraestrutura

**o** br.com.souza_anacarolina.tabelafipe.service.ConsumoApi

Componente responsável por realizar as requisições HTTP e capturar as respostas em formato JSON bruto.

**o** br.com.souza_anacarolina.tabelafipe.service.ConverteDados

Responsável por deserializar as strings JSON recebidas da API para objetos e registros Java.

### 📦 Modelos de Dados (Mapeamento JSON)

DadosVeiculo: Representa pares de codigo e nome para listagem inicial de marcas e modelos.

ModelosResponse: Objeto wrapper retornado pela API contendo a lista completa de modelos.

DadosModelo: Representa os anos disponíveis (codigo/nome) vinculados a um modelo específico.

DetalheVeiculo: Mapeamento completo das propriedades detalhadas do veículo retornadas pela FIPE para cada ano.

InformacoesModelo / InformacoesVeiculo: Classes utilitárias focadas em formatar e exibir os dados de DetalheVeiculo de forma amigável no console.

## 🛠️ Tecnologias Utilizadas

**o** Java 11

**o** HttpClient (API nativa do Java para requisições)

**o** Jackson / Gson (Manipulação de arquivos JSON)

**o** Maven (Gerenciador de dependências)