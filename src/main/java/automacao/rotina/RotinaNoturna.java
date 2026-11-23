package automacao.rotina;

import automacao.comando.ComandoAtivar;
import automacao.comando.ComandoDesligar;
import automacao.comando.ComandoFechar;
import automacao.comando.ComandoLigar;
import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;
import automacao.mediator.CentralAutomacao;

public class RotinaNoturna extends RotinaAutomatica {

    private static final String COMODO_DORMIR = "quarto";

    public RotinaNoturna(CentralAutomacao central) {
        super("noturna", central);
    }

    @Override
    protected void prepararAmbiente() {
        central.buscarTodos(Lampada.class).forEach(lampada -> adicionar(new ComandoDesligar(lampada)));
        central.buscarTodos(ArCondicionado.class).stream()
                .filter(ar -> !ar.getComodo().equals(COMODO_DORMIR))
                .forEach(ar -> adicionar(new ComandoDesligar(ar)));
        central.buscarTodos(Portao.class).forEach(portao -> adicionar(new ComandoFechar(portao)));
    }

    @Override
    protected void acionarDispositivos() {
        central.buscarTodos(Camera.class).forEach(camera -> adicionar(new ComandoAtivar(camera)));
        central.buscarTodos(ArCondicionado.class, COMODO_DORMIR).forEach(ar -> adicionar(new ComandoLigar(ar)));
    }

    @Override
    protected void finalizar() {
        log("casa pronta para dormir. Boa noite!");
    }
}
