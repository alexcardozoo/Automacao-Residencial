package automacao.dispositivo;

public class Lampada extends Dispositivo implements Ligavel {

    private boolean ligada;

    public Lampada(String comodo) {
        super(comodo);
    }

    @Override
    public void ligar() {
        if (ligada) {
            log("já está ligada");
            return;
        }
        ligada = true;
        log("ligada");
    }

    @Override
    public void desligar() {
        if (!ligada) {
            log("já está desligada");
            return;
        }
        ligada = false;
        log("desligada");
    }

    @Override
    public boolean isLigado() {
        return ligada;
    }

    @Override
    public String getTipo() {
        return "Lâmpada";
    }

    @Override
    public String getStatus() {
        return ligada ? "ligada" : "desligada";
    }
}
