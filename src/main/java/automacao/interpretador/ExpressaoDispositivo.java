package automacao.interpretador;

import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Dispositivo;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;

import java.util.Map;

public class ExpressaoDispositivo implements Expressao<Class<? extends Dispositivo>> {

    private static final Map<String, Class<? extends Dispositivo>> VOCABULARIO = Map.of(
            "luz", Lampada.class,
            "lampada", Lampada.class,
            "ar", ArCondicionado.class,
            "portao", Portao.class,
            "camera", Camera.class);

    private final String palavra;

    public ExpressaoDispositivo(String palavra) {
        this.palavra = palavra;
    }

    @Override
    public Class<? extends Dispositivo> interpretar(Contexto contexto) {
        Class<? extends Dispositivo> tipo = VOCABULARIO.get(palavra);
        if (tipo == null) {
            throw new ComandoInvalidoException("dispositivo desconhecido: '" + palavra + "'");
        }
        return tipo;
    }
}
