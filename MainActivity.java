package com.example.conexaomysql;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //Link do backend
    //Se usarmos localhost no Android ele irá procurar o backend
    //dentro do próprio dispositivo. Precisamos direcionar para
    //o computador que está rodando o backend.
    //Para isso usamos um IP reservado que é 10.0.2.2
    String url  = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Criar um objeto da biblioteca Volley
        //Esse objeto será responsável por enviar a requisição ao backend
        RequestQueue requisicao = Volley.newRequestQueue(this);

        //Criar os objetos para cada componente da tela
        EditText id = findViewById(R.id.campoID);
        EditText nome = findViewById(R.id.campoNome);
        EditText preco = findViewById(R.id.campoPreco);
        EditText qtde = findViewById(R.id.campoQuantidade);
        Button cadastrar = findViewById(R.id.btCadastrar);
        Button apagar = findViewById(R.id.btApagar);
        Button atualizar = findViewById(R.id.btAtualizar);
        Button listar = findViewById(R.id.btListar);

        //EVENTO DO BOTÃO CADASTRAR
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Montar o body criando um objeto JSON do produto
                JSONObject dadosProduto = new JSONObject();

                try {
                    //Passar o atributo e o valor de cada parte
                    //do produto
                    dadosProduto.put("id", id.getText().toString());
                    dadosProduto.put("nome", nome.getText().toString());
                    dadosProduto.put("preco", preco.getText().toString());
                    dadosProduto.put("qtde", qtde.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                //Configuração da requisição POST do /cadstrar
                JsonObjectRequest apiCadastrar = new JsonObjectRequest(
                        Request.Method.POST, //Usar o POST
                        url + "cadastrar", //Link do Backend
                        dadosProduto, //Dados enviados no Body
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Testar se possui o atributo "resposta"
                                if(response.has("resposta")){
                                    Toast.makeText(MainActivity.this,
                                            "Cadastrado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this,
                                            "Não cadastrado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this,
                                "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                    }
                }
                );
                //Para enviar ao backend usamos o objeto do Volley
                requisicao.add(apiCadastrar);
            }
        });

        //EVENTO DO BOTÃO ATUALIZAR
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Montar o body criando um objeto JSON do produto
                JSONObject dadosProduto = new JSONObject();

                try {
                    //Passar o atributo e o valor de cada parte
                    //do produto
                    dadosProduto.put("id", id.getText().toString());
                    dadosProduto.put("nome", nome.getText().toString());
                    dadosProduto.put("preco", preco.getText().toString());
                    dadosProduto.put("qtde", qtde.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                //Configuração da requisição PUT do ATUALIZAR
                JsonObjectRequest apiAtualizar = new JsonObjectRequest(
                        Request.Method.PUT, //Usar o PUT
                        url + "atualizar/" + id.getText().toString(), //Link do Backend
                        dadosProduto, //Dados enviados no Body
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Testar se possui o atributo "resposta"
                                if(response.has("resposta")){
                                    Toast.makeText(MainActivity.this,
                                            "Atualizado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this,
                                            "Não Atualizado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this,
                                "Erro ao Atualizado", Toast.LENGTH_SHORT).show();
                    }
                }
                );
                //Para enviar ao backend usamos o objeto do Volley
                requisicao.add(apiAtualizar);
            }
        });
        //EVENTO DO BOTÃO APAGAR

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Configuração da requisição PUT do ATUALIZAR
                JsonObjectRequest apiApagar = new JsonObjectRequest(
                        Request.Method.DELETE, //Usar o PUT
                        url + "apagar/" + id.getText().toString(), //Link do Backend
                        null, //Dados enviados no Body
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Testar se possui o atributo "resposta"
                                if(response.has("resposta")){
                                    Toast.makeText(MainActivity.this,
                                            "Apagado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this,
                                            "Não Apagado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this,
                                "Erro ao Apagar", Toast.LENGTH_SHORT).show();
                    }
                }
                );
                //Para enviar ao backend usamos o objeto do Volley
                requisicao.add(apiApagar);
            }
        });



    }
}