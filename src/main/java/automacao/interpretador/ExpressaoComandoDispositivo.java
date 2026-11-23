package automacao.interpretador;

import automacao.comando.Comando;
import automacao.comando.MacroComando;
import automacao.dispositivo.Dispositivo;
import automacao.mediator.CentralAutomacao;

import java.util.List;
import java.util.Optional;

public class ExpressaoComandoDispositivo implements Expressao<Comando> {

    private final ExpressaoAcao acao;
    private final ExpressaoDispositivo dispositivo;
    private final ExpressaoComodo comodo;

    public ExpressaoComandoDispositivo(ExpressaoAcao acao, ExpressaoDispositivo dispositivo, ExpressaoComodo comodo) {
        this.acao = acao;
        this.dispositivo = dispositivo;
        this.comodo = comodo;
    }

    @Override
    public Comando interpretar(Contexto contexto) {
        Acao acaoInterpretada = acao.interpretar(contexto);
        Class<? extends Dispositivo> tipo = dispositivo.interpretar(contexto);
        Optional<String> comodoInterpretado = comodo.interpretar(contexto);

        CentralAutomacao central = contexto.getCentral();
        List<? extends Dispositivo> alvos = comodoInterpretado
                .<List<? extends Dispositivo>>map(c -> central.buscarTodos(tipo, c))
                .orElseGet(() -> central.buscarTodos(tipo));

        if (alvos.isEmpty()) {
            throw new ComandoInvalidoException("nenhum dispositivo encontrado"
                    + comodoInterpretado.map(c -> " em '" + c + "'").orElse(""));
        }

        List<Comando> comandos = alvos.stream()
                .map(acaoInterpretada::criarComando)
                .toList();

        if (comandos.size() == 1) {
            return comandos.get(0);
        }
        return new MacroComando(acaoInterpretada.getVerbo() + " todos", comandos);
    }
}
