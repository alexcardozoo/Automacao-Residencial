package automacao.rotina;

import automacao.comando.ComandoAtivar;
import automacao.comando.ComandoDesligar;
import automacao.comando.ComandoFechar;
import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;
import automacao.mediator.CentralAutomacao;

public class RotinaSaidaCasa extends RotinaAutomatica {

    public RotinaSaidaCasa(CentralAutomacao central) {
        super("saída de casa", central);
    }

    @Override
    protected boolean podeExecutar() {
        if (central.buscarTodos(Camera.class).isEmpty()) {
            log("nenhuma câmera registrada: a casa ficaria sem vigilância");
            return false;
        }
        return true;
    }

    @Override
    protected void prepararAmbiente() {
        central.buscarTodos(Lampada.class).forEach(lampada -> adicionar(new ComandoDesligar(lampada)));
        central.buscarTodos(ArCondicionado.class).forEach(ar -> adicionar(new ComandoDesligar(ar)));
    }

    @Override
    protected void acionarDispositivos() {
        central.buscarTodos(Portao.class).forEach(portao -> adicionar(new ComandoFechar(portao)));
        central.buscarTodos(Camera.class).forEach(camera -> adicionar(new ComandoAtivar(camera)));
    }

    @Override
    protected void finalizar() {
        log("casa vazia e protegida");
    }
}
