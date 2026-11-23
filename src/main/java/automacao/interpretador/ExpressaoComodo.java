package automacao.interpretador;

import java.util.Optional;

public class ExpressaoComodo implements Expressao<Optional<String>> {

    private final String nome;

    public ExpressaoComodo(String nome) {
        this.nome = nome == null || nome.isBlank() ? null : nome;
    }

    @Override
    public Optional<String> interpretar(Contexto contexto) {
        return Optional.ofNullable(nome);
    }
}
