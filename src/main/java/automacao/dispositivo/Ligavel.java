package automacao.dispositivo;

/** Dispositivos que podem ser ligados e desligados. */
public interface Ligavel {

    void ligar();

    void desligar();

    boolean isLigado();
}
