package automacao.visitor;

import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Dispositivo;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;

public interface DispositivoVisitor {

    void visitar(Lampada lampada);

    void visitar(ArCondicionado arCondicionado);

    void visitar(Portao portao);

    void visitar(Camera camera);

    void visitar(Dispositivo dispositivo);
}
