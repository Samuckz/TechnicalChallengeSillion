import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        System.out.println("Digite a url usada como referência: ");
        String url = in.nextLine();
        System.out.println("Digite a sequência de palavras chave a ser pesquisada: ");
        String fraseComposta = in.nextLine();

        HttpEntity conexao = conectaUrl(url);
        String body = getBodyHtml(conexao);
        List<String> frasesDecompostas = decompoeFrase(fraseComposta);
        obterQuantidadeDeRepeticoesDaFrase(frasesDecompostas, body);

    }

    /***
     * Objetivo: Acessar determinada url e obter o conteúdo em texto da página citada
     * Entradas: Url em formato String
     * Saida: Conteúdo HTML da página web em formato de String
     ***/
    public static HttpEntity conectaUrl(String url){

        HttpClient conexao = HttpClients.createDefault();
        HttpGet getConexao = new HttpGet(url);

        try{
            HttpResponse response = conexao.execute(getConexao);

            if(response.getStatusLine().getStatusCode() == 404){
                throw new RuntimeException("Página não encontrada");
            }

            // Obtendo o código de status da resposta
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);

            // Obtendo o conteúdo da resposta, se houver
            HttpEntity corpoResposta = response.getEntity();

            return corpoResposta;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /***
     * Objetivo: Realizar o parse de HttpEntity para String através da biblioteca Jsoup
     * Entradas: Http Entity obtido no metodo conectaUrl
     * Saida: String contendo o body da pagina pesquisada
     ***/
    public static String getBodyHtml(HttpEntity corpoResposta) throws IOException {
        try{
            String content = EntityUtils.toString(corpoResposta, StandardCharsets.UTF_8);
            Document document = Jsoup.parse(content);
            String body = document.body().text();
            return body;

        } catch (IOException ex){
            throw new IOException("Erro ao realizar a conversão:\n" + ex.getMessage(), ex);
        }
    }

    /***
     * Objetivo: Decompor a frase inserida pelo usuário, de forma aobter os elementos a serem procurados na página web
     * Entradas: Frase composta em formato de String
     * Saida: ArrayList de String, contendo os elementos da frase decomposta
     ***/
    public static List<String> decompoeFrase(String frase){
        String[] frases = frase.split(" ");
        List<String> frasesDecompostas = new ArrayList<>();
        frasesDecompostas.add(frase);
        if(frases.length != 1){
            for(String f : frases){
                frasesDecompostas.add(f);
            }
        }

        return frasesDecompostas;
    }


    /***
     * Objetivo: Pesquisar a quantidade de repetições de cada elemento da frase decomposta
     * Entradas: List de String com os elementos da frase decomposta e o conteúdo do body da pagina pesquisada
     * Saida: Void, imprimindo a quantidade de repetições das frases encontradas na pagina
     ***/
    public static void obterQuantidadeDeRepeticoesDaFrase(List<String> fraseDecomposta, String paginaWeb){
        Map<String, Integer> relacaoFraseQuantidade = new HashMap<>();
        fraseDecomposta.stream().forEach(frase -> {
            Integer qnt = StringUtils.countMatches(paginaWeb, frase);
            relacaoFraseQuantidade.put(frase, qnt);
        });

        relacaoFraseQuantidade.forEach((frase, quantidade) -> {
            System.out.println(String.format("Frase: %s \nQuantidade: %d", frase, quantidade));
        });

    }
}
