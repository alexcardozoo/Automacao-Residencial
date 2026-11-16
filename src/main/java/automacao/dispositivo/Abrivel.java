package automacao.dispositivo;

/** Dispositivos que podem ser abertos e fechados. */
public interface Abrivel {

    void abrir();

    void fechar();

    boolean isAberto();
}
