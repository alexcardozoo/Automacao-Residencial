package automacao.comando;

import automacao.dispositivo.Abrivel;

public class ComandoFechar implements Comando {

    private final Abrivel alvo;
    private boolean estavaAberto;

    public ComandoFechar(Abrivel alvo) {
        this.alvo = alvo;
    }

    @Override
    public void executar() {
        estavaAberto = alvo.isAberto();
        alvo.fechar();
    }

    @Override
    public void desfazer() {
        if (estavaAberto) {
            alvo.abrir();
        }
    }

    @Override
    public String getDescricao() {
        return "fechar " + alvo.getIdentificacao();
    }
}
