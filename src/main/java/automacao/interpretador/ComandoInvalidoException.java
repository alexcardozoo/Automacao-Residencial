package automacao.interpretador;

public class ComandoInvalidoException extends RuntimeException {

    public ComandoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
