package com.example.appfinal

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.random.Random

// POR QUE ESTAMOS USANDO COROUTINES?
// O grande problema é evitar que a tela trave! Tarefas demoradas (tipo buscar algo na internet ou fazer um
// cálculo gigante) tem que sair da Thread Principal (a "Main Thread", que desenha a tela).
// As Coroutines fazem esse trabalho de bastidores sem congelar a UI.
// Usar o 'lifecycleScope' é uma mão na roda no Android, porque ele cancela as tarefas sozinho
// quando a Activity é fechada, evitando que o app quebre ou vaze memória.

class MainActivity : AppCompatActivity() {

    // Componentes da UI (os IDs do activity_main.xml)
    private lateinit var tvStatus: TextView
    private lateinit var btnApiCall: Button
    private lateinit var btnCalculation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialização dos elementos
        tvStatus = findViewById(R.id.tvStatus)
        btnApiCall = findViewById(R.id.btnSimulateApi)
        btnCalculation = findViewById(R.id.btnSimulateCalculation)

        // TAREFA 1: SIMULAÇÃO DE I/O (CHAMADA DE API) - Usando 'launch' para não esperar retorno
        btnApiCall.setOnClickListener {
            // Inicia a coroutine na Thread principal (já que estamos no OnClickListener).
            // 'launch' só dispara a coroutine, não precisamos do resultado na hora.
            lifecycleScope.launch {
                tvStatus.text = "Iniciando busca de dados! A tela não vai travar (espero)..."

                // Chamamos a função suspend, que vai lá para o background.
                val result = simulateApiCall()

                // O fluxo volta para cá (Thread Principal) automaticamente para atualizar o texto.
                tvStatus.text = result
            }
        }

        // TAREFA 2: CÁLCULO INTENSIVO - Usando 'async' e 'await' para fazer duas coisas ao mesmo tempo
        btnCalculation.setOnClickListener {
            // 'launch' como coordenador das duas sub-tarefas.
            lifecycleScope.launch {
                tvStatus.text = "Iniciando cálculo e espera em paralelo. Segura aí..."

                // TAREFA A: Cálculo (Precisa de CPU, então uso o Dispatchers.Default)
                // 'async' promete um resultado futuro.
                val deferredResult1 = async(Dispatchers.Default) { calculateFactor(10) }

                // TAREFA B: Simulação de espera aleatória (também rodando no Default em paralelo)
                val deferredResult2 = async(Dispatchers.Default) { generateRandomNumber(3000) }

                // 'await' pausa a coroutine ATUAL (o launch) até que AMBOS os resultados cheguem.
                val result1 = deferredResult1.await()
                val result2 = deferredResult2.await()

                // Mostra o resultado final.
                tvStatus.text = "Cálculo Completo:\n- Fatorial de 10: $result1\n- Número Aleatório (atraso variável): $result2"
            }
        }
    }

    /**
     * Função 'suspend' para simular a demora de uma chamada de rede.
     * O 'suspend' avisa ao Kotlin que esta função vai pausar e retomar a execução.
     */
    private suspend fun simulateApiCall(): String {
        // Mudo o contexto para o 'Dispatchers.IO', que é feito para operações de Input/Output (I/O).
        // Isso joga o trabalho para um pool de threads específico para espera.
        return withContext(Dispatchers.IO) {
            // 'delay' suspende a coroutine por 2 segundos. O segredo é que ele não trava a Thread!
            val timeMillis = 2000L
            delay(timeMillis)

            // Gerando um número para simular os dados recebidos.
            val data = Random.nextInt(100)
            "Sucesso! Dados recebidos: $data (Após ${timeMillis / 1000}s na thread de I/O)"
        }
    }

    /**
     * Função 'suspend' que faz um cálculo intenso (Fatorial de 10).
     * O retorno é Long porque o fatorial estoura o limite de Int.
     */
    private suspend fun calculateFactor(n: Int): Long {
        val timeMillis = 1000L
        // Simulo o tempo que o processador levaria.
        delay(timeMillis)
        // Uso 'fold(1L)' para garantir que o acumulador comece como Long e não dê erro de tipagem.
        return (1..n).fold(1L) { acc, i -> acc * i.toLong() }
    }

    // Função 'suspend' simples que só usa um atraso aleatório.
    private suspend fun generateRandomNumber(maxDelay: Long): Int {
        val delayTime = Random.nextLong(maxDelay)
        delay(delayTime) // Pausa rápida.
        return Random.nextInt(1000)
    }
}
