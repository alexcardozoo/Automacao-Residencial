package automacao.mediator;

import automacao.dispositivo.Dispositivo;


public interface MediadorAutomacao {

    void registrar(Dispositivo dispositivo);

    void notificar(Dispositivo origem, Evento evento);
}
