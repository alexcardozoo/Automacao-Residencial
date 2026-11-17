package automacao.comando;

/**
 * Command: encapsula uma ação sobre a casa como um objeto.
 * Quem dispara o comando (controle, rotina, interpretador) não precisa
 * saber qual dispositivo será acionado nem como desfazer a ação.
 */
public interface Comando {

    void executar();

    /** Volta a casa ao estado anterior à execução deste comando. */
    void desfazer();

    String getDescricao();
}
