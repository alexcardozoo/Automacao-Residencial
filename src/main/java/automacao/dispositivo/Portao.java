package automacao.dispositivo;

import automacao.mediator.Evento;

public class Portao extends Dispositivo implements Abrivel {

    private boolean aberto;

    public Portao(String comodo) {
        super(comodo);
    }

    @Override
    public void abrir() {
        if (aberto) {
            log("já está aberto");
            return;
        }
        aberto = true;
        log("aberto");
        notificar(Evento.ABERTO);
    }

    @Override
    public void fechar() {
        if (!aberto) {
            log("já está fechado");
            return;
        }
        aberto = false;
        log("fechado");
        notificar(Evento.FECHADO);
    }

    @Override
    public boolean isAberto() {
        return aberto;
    }

    @Override
    public String getTipo() {
        return "Portão";
    }

    @Override
    public String getStatus() {
        return aberto ? "aberto" : "fechado";
    }
}
