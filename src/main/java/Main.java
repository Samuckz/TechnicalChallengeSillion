import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);

        System.out.println("Digite a url usada como referência: ");
        String url = in.nextLine();

        System.out.println("Digite a sequência de palavras chave a ser pesquisada: ");
        String fraseComposta = in.nextLine();

        HttpEntity body = conectaUrl(url);

        List<String> frasesDecompostas = decompoeFrase(fraseComposta);


    }

    /***
     * Objetivo: Acessar Determinada url e obter o conteúdo em texto da página citada
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
            if (corpoResposta != null) {
                String content = EntityUtils.toString(corpoResposta);
                System.out.println("Response Content: " + content);
            }

            return corpoResposta;

        } catch (IOException e) {
            throw new RuntimeException(e);
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
        if(frasesDecompostas.size() != 1){
            for(String f : frases){
                frasesDecompostas.add(f);
            }
        }
        return frasesDecompostas;
    }
}
