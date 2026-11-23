package automacao.dispositivo;

import automacao.mediator.Evento;

public class Persiana extends Dispositivo implements Abrivel {

    private boolean aberta;

    public Persiana(String comodo) {
        super(comodo);
    }

    @Override
    public void abrir() {
        if (aberta) {
            log("já está aberta");
            return;
        }
        aberta = true;
        log("aberta");
        notificar(Evento.ABERTO);
    }

    @Override
    public void fechar() {
        if (!aberta) {
            log("já está fechada");
            return;
        }
        aberta = false;
        log("fechada");
        notificar(Evento.FECHADO);
    }

    @Override
    public boolean isAberto() {
        return aberta;
    }

    @Override
    public String getTipo() {
        return "Persiana";
    }

    @Override
    public String getStatus() {
        return aberta ? "aberta" : "fechada";
    }
}
