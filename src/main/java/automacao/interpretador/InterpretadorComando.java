package automacao.interpretador;

import automacao.comando.Comando;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class InterpretadorComando {

    private static final Set<String> PALAVRAS_IGNORADAS = Set.of(
            "a", "o", "as", "os", "da", "do", "das", "dos", "de", "na", "no", "nas", "nos", "condicionado");

    private final Contexto contexto;

    public InterpretadorComando(Contexto contexto) {
        this.contexto = contexto;
    }

    public Comando interpretar(String texto) {
        return analisar(texto).interpretar(contexto);
    }

    public Expressao<Comando> analisar(String texto) {
        List<String> tokens = tokenizar(texto);
        if (tokens.isEmpty()) {
            throw new ComandoInvalidoException("comando vazio");
        }

        if (tokens.get(0).equals("executar") || tokens.get(0).equals("rotina")) {
            List<String> resto = tokens.subList(1, tokens.size());
            if (!resto.isEmpty() && resto.get(0).equals("rotina")) {
                resto = resto.subList(1, resto.size());
            }
            if (resto.isEmpty()) {
                throw new ComandoInvalidoException("informe o nome da rotina");
            }
            return new ExpressaoExecutarRotina(resto.get(0));
        }

        if (tokens.size() < 2) {
            throw new ComandoInvalidoException("use: <ação> <dispositivo> [cômodo]");
        }

        String comodo = String.join(" ", tokens.subList(2, tokens.size()));
        return new ExpressaoComandoDispositivo(
                new ExpressaoAcao(tokens.get(0)),
                new ExpressaoDispositivo(tokens.get(1)),
                new ExpressaoComodo(comodo));
    }

    public static String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return semAcento.toLowerCase().trim();
    }

    private List<String> tokenizar(String texto) {
        if (texto == null) {
            return List.of();
        }
        return Arrays.stream(normalizar(texto).split("[\\s-]+"))
                .filter(token -> !token.isBlank())
                .filter(token -> !PALAVRAS_IGNORADAS.contains(token))
                .toList();
    }
}
