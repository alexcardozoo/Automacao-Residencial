package automacao.dispositivo;

public class ArCondicionado extends Dispositivo implements Ligavel {

    public static final int TEMPERATURA_MINIMA = 16;
    public static final int TEMPERATURA_MAXIMA = 30;
    private static final int TEMPERATURA_PADRAO = 24;

    private boolean ligado;
    private int temperatura = TEMPERATURA_PADRAO;

    public ArCondicionado(String comodo) {
        super(comodo);
    }

    @Override
    public void ligar() {
        if (ligado) {
            log("já está ligado");
            return;
        }
        ligado = true;
        log("ligado em " + temperatura + "°C");
    }

    @Override
    public void desligar() {
        if (!ligado) {
            log("já está desligado");
            return;
        }
        ligado = false;
        log("desligado");
    }

    @Override
    public boolean isLigado() {
        return ligado;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        if (temperatura < TEMPERATURA_MINIMA || temperatura > TEMPERATURA_MAXIMA) {
            throw new IllegalArgumentException("Temperatura deve estar entre "
                    + TEMPERATURA_MINIMA + "°C e " + TEMPERATURA_MAXIMA + "°C");
        }
        this.temperatura = temperatura;
        log("temperatura ajustada para " + temperatura + "°C");
    }

    @Override
    public String getTipo() {
        return "Ar-condicionado";
    }

    @Override
    public String getStatus() {
        return ligado ? "ligado em " + temperatura + "°C" : "desligado";
    }
}
