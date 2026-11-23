package automacao.rotina;

import automacao.comando.Comando;

public class ComandoExecutarRotina implements Comando {

    private final RotinaAutomatica rotina;

    public ComandoExecutarRotina(RotinaAutomatica rotina) {
        this.rotina = rotina;
    }

    @Override
    public void executar() {
        rotina.executar();
    }

    @Override
    public void desfazer() {
        rotina.desfazer();
    }

    @Override
    public String getDescricao() {
        return "executar rotina " + rotina.getNome();
    }
}
