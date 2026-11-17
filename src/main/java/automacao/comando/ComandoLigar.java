package automacao.comando;

import automacao.dispositivo.Ligavel;

public class ComandoLigar implements Comando {

    private final Ligavel alvo;
    private boolean estavaLigado;

    public ComandoLigar(Ligavel alvo) {
        this.alvo = alvo;
    }

    @Override
    public void executar() {
        estavaLigado = alvo.isLigado();
        alvo.ligar();
    }

    @Override
    public void desfazer() {
        if (!estavaLigado) {
            alvo.desligar();
        }
    }

    @Override
    public String getDescricao() {
        return "ligar " + alvo.getIdentificacao();
    }
}
