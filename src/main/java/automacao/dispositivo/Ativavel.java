package automacao.dispositivo;

public interface Ativavel {

    void ativar();

    void desativar();

    boolean isAtivo();

    String getIdentificacao();
}
