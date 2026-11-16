package automacao.dispositivo;

public class Camera extends Dispositivo implements Ativavel {

    private boolean ativa;

    public Camera(String comodo) {
        super(comodo);
    }

    @Override
    public void ativar() {
        if (ativa) {
            log("já está gravando");
            return;
        }
        ativa = true;
        log("ativada, gravando");
    }

    @Override
    public void desativar() {
        if (!ativa) {
            log("já está desativada");
            return;
        }
        ativa = false;
        log("desativada");
    }

    @Override
    public boolean isAtivo() {
        return ativa;
    }

    @Override
    public String getTipo() {
        return "Câmera";
    }

    @Override
    public String getStatus() {
        return ativa ? "gravando" : "desativada";
    }
}
