package automacao.comando;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Invoker do padrão Command. Executa qualquer {@link Comando} sem conhecer
 * seus detalhes e guarda o histórico para permitir desfazer.
 */
public class ControleRemoto {

    private final Deque<Comando> historico = new ArrayDeque<>();

    public void executar(Comando comando) {
        log("executando: " + comando.getDescricao());
        comando.executar();
        historico.push(comando);
    }

    /** Desfaz o último comando executado. Retorna false se não houver nada a desfazer. */
    public boolean desfazerUltimo() {
        if (historico.isEmpty()) {
            log("nada para desfazer");
            return false;
        }
        Comando ultimo = historico.pop();
        log("desfazendo: " + ultimo.getDescricao());
        ultimo.desfazer();
        return true;
    }

    /** Histórico do mais recente para o mais antigo. */
    public List<String> getHistorico() {
        return historico.stream().map(Comando::getDescricao).toList();
    }

    private void log(String mensagem) {
        System.out.println("[Controle] " + mensagem);
    }
}
