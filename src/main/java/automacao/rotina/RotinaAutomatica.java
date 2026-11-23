package automacao.rotina;

import automacao.comando.Comando;
import automacao.comando.MacroComando;
import automacao.mediator.CentralAutomacao;

import java.util.ArrayList;
import java.util.List;

public abstract class RotinaAutomatica {

    private final String nome;
    protected final CentralAutomacao central;
    private final List<Comando> comandosPlanejados = new ArrayList<>();
    private MacroComando ultimaExecucao;

    protected RotinaAutomatica(String nome, CentralAutomacao central) {
        this.nome = nome;
        this.central = central;
    }

    public final void executar() {
        log("iniciando");
        if (!podeExecutar()) {
            log("cancelada");
            return;
        }

        comandosPlanejados.clear();
        prepararAmbiente();
        acionarDispositivos();

        if (comandosPlanejados.isEmpty()) {
            log("nenhum dispositivo para acionar");
            ultimaExecucao = null;
        } else {
            ultimaExecucao = new MacroComando(nome, comandosPlanejados);
            ultimaExecucao.executar();
        }

        finalizar();
    }

    public final void desfazer() {
        if (ultimaExecucao == null) {
            log("nada para desfazer");
            return;
        }
        log("desfazendo");
        ultimaExecucao.desfazer();
        ultimaExecucao = null;
    }

    protected boolean podeExecutar() {
        return true;
    }

    protected abstract void prepararAmbiente();

    protected abstract void acionarDispositivos();

    protected void finalizar() {
        log("concluída");
    }

    protected final void adicionar(Comando comando) {
        comandosPlanejados.add(comando);
    }

    public String getNome() {
        return nome;
    }

    protected void log(String mensagem) {
        System.out.println("[Rotina " + nome + "] " + mensagem);
    }
}
