## DESAFIO SITUACIONAL SILLION TECHNOLOGIES

Este repositório tem como objetivo versionar o código utilizado para realizar um desafio técnico da empresa Sillion. Ele consiste em um código que faz o web scraping em uma página através de uma requisição http e busca por um conjunto de palavras inseridas pelo usuário. Para realizar, foram utilizadas as seguintes tecnologias:
- Java 8
- Bibliotecas:
  - Http Client
  - Jsoup
- Regular Expressions

Descrição dos métodos e suas funcionalidades:

- conectaUrl(String url): Este método tem como objetivo obter o conteúdo de um link especificado pelo usuário, retornando um objeto HttpEntity
- getBodyHtml(HttpEntity conexao): Ao receber um objeto do tipo HttpEntity, este método acessa a tag body do html da página e retorna o innerHtml do elemento
- decompoeFrase(String frase): Recebe uma frase como Entrada e retorna uma List com a frase decomposta por palavras e a frase inteira
- obterQuantidadeDeRepeticoesDaFrase(List<String> listaSubString, String  string): Conta a quantidade de ocorrência de cada subtring da lista na String de referência, no caso, a página web.

