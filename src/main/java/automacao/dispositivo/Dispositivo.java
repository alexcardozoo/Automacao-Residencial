package automacao.dispositivo;

/**
 * Base de todos os dispositivos da casa.
 * Cada dispositivo é identificado pelo seu tipo e pelo cômodo onde está instalado.
 */
public abstract class Dispositivo {

    private final String comodo;

    protected Dispositivo(String comodo) {
        if (comodo == null || comodo.isBlank()) {
            throw new IllegalArgumentException("O cômodo do dispositivo é obrigatório");
        }
        this.comodo = comodo.trim().toLowerCase();
    }

    public String getComodo() {
        return comodo;
    }

    /** Nome do tipo do dispositivo, ex.: "Lâmpada". */
    public abstract String getTipo();

    /** Descrição curta do estado atual, ex.: "ligada". */
    public abstract String getStatus();

    public String getIdentificacao() {
        return getTipo() + " (" + comodo + ")";
    }

    protected void log(String mensagem) {
        System.out.println("[" + getIdentificacao() + "] " + mensagem);
    }

    @Override
    public String toString() {
        return getIdentificacao() + " - " + getStatus();
    }
}
