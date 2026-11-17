package automacao.comando;

import automacao.dispositivo.Ligavel;

public class ComandoDesligar implements Comando {

    private final Ligavel alvo;
    private boolean estavaLigado;

    public ComandoDesligar(Ligavel alvo) {
        this.alvo = alvo;
    }

    @Override
    public void executar() {
        estavaLigado = alvo.isLigado();
        alvo.desligar();
    }

    @Override
    public void desfazer() {
        if (estavaLigado) {
            alvo.ligar();
        }
    }

    @Override
    public String getDescricao() {
        return "desligar " + alvo.getIdentificacao();
    }
}
