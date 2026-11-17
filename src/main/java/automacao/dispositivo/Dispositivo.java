package automacao.dispositivo;

import automacao.mediator.Evento;
import automacao.mediator.MediadorAutomacao;

public abstract class Dispositivo {

    private final String comodo;
    private MediadorAutomacao mediador;

    protected Dispositivo(String comodo) {
        if (comodo == null || comodo.isBlank()) {
            throw new IllegalArgumentException("O cômodo do dispositivo é obrigatório");
        }
        this.comodo = comodo.trim().toLowerCase();
    }

    public String getComodo() {
        return comodo;
    }

    public void conectar(MediadorAutomacao mediador) {
        this.mediador = mediador;
    }

    protected void notificar(Evento evento) {
        if (mediador != null) {
            mediador.notificar(this, evento);
        }
    }

    public abstract String getTipo();

    public abstract String getStatus();

    public String getIdentificacao() {
        return getTipo() + " (" + comodo + ")";
    }

    protected void log(String mensagem) {
        System.out.println("[" + getIdentificacao() + "] " + mensagem);
    }

    @Override
    public String toString() {
        return getIdentificacao() + " - " + getStatus();
    }
}
