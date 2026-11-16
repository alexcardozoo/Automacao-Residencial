package automacao.dispositivo;

/** Dispositivos que podem ser ativados e desativados (ex.: câmeras, alarmes). */
public interface Ativavel {

    void ativar();

    void desativar();

    boolean isAtivo();
}
