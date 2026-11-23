package automacao.interpretador;

public interface Expressao<T> {

    T interpretar(Contexto contexto);
}
