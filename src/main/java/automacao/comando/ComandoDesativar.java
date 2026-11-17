package automacao.comando;

import automacao.dispositivo.Ativavel;

public class ComandoDesativar implements Comando {

    private final Ativavel alvo;
    private boolean estavaAtivo;

    public ComandoDesativar(Ativavel alvo) {
        this.alvo = alvo;
    }

    @Override
    public void executar() {
        estavaAtivo = alvo.isAtivo();
        alvo.desativar();
    }

    @Override
    public void desfazer() {
        if (estavaAtivo) {
            alvo.ativar();
        }
    }

    @Override
    public String getDescricao() {
        return "desativar " + alvo.getIdentificacao();
    }
}
