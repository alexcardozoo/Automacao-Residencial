package automacao.interpretador;

import java.util.Map;

public class ExpressaoAcao implements Expressao<Acao> {

    private static final Map<String, Acao> VOCABULARIO = Map.of(
            "ligar", Acao.LIGAR,
            "acender", Acao.LIGAR,
            "desligar", Acao.DESLIGAR,
            "apagar", Acao.DESLIGAR,
            "abrir", Acao.ABRIR,
            "fechar", Acao.FECHAR,
            "ativar", Acao.ATIVAR,
            "desativar", Acao.DESATIVAR);

    private final String palavra;

    public ExpressaoAcao(String palavra) {
        this.palavra = palavra;
    }

    @Override
    public Acao interpretar(Contexto contexto) {
        Acao acao = VOCABULARIO.get(palavra);
        if (acao == null) {
            throw new ComandoInvalidoException("ação desconhecida: '" + palavra + "'");
        }
        return acao;
    }
}
