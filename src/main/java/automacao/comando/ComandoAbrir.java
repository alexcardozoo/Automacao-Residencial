package automacao.comando;

import automacao.dispositivo.Abrivel;

public class ComandoAbrir implements Comando {

    private final Abrivel alvo;
    private boolean estavaAberto;

    public ComandoAbrir(Abrivel alvo) {
        this.alvo = alvo;
    }

    @Override
    public void executar() {
        estavaAberto = alvo.isAberto();
        alvo.abrir();
    }

    @Override
    public void desfazer() {
        if (!estavaAberto) {
            alvo.fechar();
        }
    }

    @Override
    public String getDescricao() {
        return "abrir " + alvo.getIdentificacao();
    }
}
