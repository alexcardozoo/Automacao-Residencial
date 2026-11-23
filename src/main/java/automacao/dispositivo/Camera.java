package automacao.dispositivo;

import automacao.mediator.Evento;
import automacao.visitor.DispositivoVisitor;

public class Camera extends Dispositivo implements Ativavel {

    private boolean ativa;

    public Camera(String comodo) {
        super(comodo);
    }

    @Override
    public void ativar() {
        if (ativa) {
            log("já está gravando");
            return;
        }
        ativa = true;
        log("ativada, gravando");
        notificar(Evento.ATIVADO);
    }

    @Override
    public void desativar() {
        if (!ativa) {
            log("já está desativada");
            return;
        }
        ativa = false;
        log("desativada");
        notificar(Evento.DESATIVADO);
    }

    public void detectarMovimento() {
        if (!ativa) {
            log("movimento ignorado: câmera desativada");
            return;
        }
        log("movimento detectado");
        notificar(Evento.MOVIMENTO_DETECTADO);
    }

    @Override
    public boolean isAtivo() {
        return ativa;
    }

    @Override
    public void aceitar(DispositivoVisitor visitor) {
        visitor.visitar(this);
    }

    @Override
    public String getTipo() {
        return "Câmera";
    }

    @Override
    public String getStatus() {
        return ativa ? "gravando" : "desativada";
    }
}
