package automacao.comando;

import automacao.dispositivo.Ativavel;

public class ComandoAtivar implements Comando {

    private final Ativavel alvo;
    private boolean estavaAtivo;

    public ComandoAtivar(Ativavel alvo) {
        this.alvo = alvo;
    }

    @Override
    public void executar() {
        estavaAtivo = alvo.isAtivo();
        alvo.ativar();
    }

    @Override
    public void desfazer() {
        if (!estavaAtivo) {
            alvo.desativar();
        }
    }

    @Override
    public String getDescricao() {
        return "ativar " + alvo.getIdentificacao();
    }
}
