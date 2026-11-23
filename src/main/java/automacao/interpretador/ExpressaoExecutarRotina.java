package automacao.interpretador;

import automacao.comando.Comando;
import automacao.rotina.ComandoExecutarRotina;

public class ExpressaoExecutarRotina implements Expressao<Comando> {

    private final String nomeRotina;

    public ExpressaoExecutarRotina(String nomeRotina) {
        this.nomeRotina = nomeRotina;
    }

    @Override
    public Comando interpretar(Contexto contexto) {
        return contexto.buscarRotina(nomeRotina)
                .map(ComandoExecutarRotina::new)
                .orElseThrow(() -> new ComandoInvalidoException("rotina desconhecida: '" + nomeRotina
                        + "'. Disponíveis: " + String.join(", ", contexto.getRotinas().keySet())));
    }
}
