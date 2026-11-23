package automacao.interpretador;

import automacao.mediator.CentralAutomacao;
import automacao.rotina.RotinaAutomatica;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Contexto {

    private final CentralAutomacao central;
    private final Map<String, RotinaAutomatica> rotinas = new TreeMap<>();

    public Contexto(CentralAutomacao central) {
        this.central = central;
    }

    public CentralAutomacao getCentral() {
        return central;
    }

    public void registrarRotina(String nome, RotinaAutomatica rotina) {
        rotinas.put(InterpretadorComando.normalizar(nome), rotina);
    }

    public Optional<RotinaAutomatica> buscarRotina(String nome) {
        return Optional.ofNullable(rotinas.get(nome));
    }

    public Map<String, RotinaAutomatica> getRotinas() {
        return Collections.unmodifiableMap(rotinas);
    }
}
