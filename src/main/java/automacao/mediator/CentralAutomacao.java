package automacao.mediator;

import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Dispositivo;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CentralAutomacao implements MediadorAutomacao {

    private final List<Dispositivo> dispositivos = new ArrayList<>();

    @Override
    public void registrar(Dispositivo dispositivo) {
        dispositivos.add(dispositivo);
        dispositivo.conectar(this);
    }

    @Override
    public void notificar(Dispositivo origem, Evento evento) {
        if (origem instanceof Portao portao) {
            reagirAoPortao(portao, evento);
        } else if (origem instanceof Camera camera) {
            reagirACamera(camera, evento);
        } else if (origem instanceof ArCondicionado ar) {
            reagirAoArCondicionado(ar, evento);
        }
    }

    private void reagirAoPortao(Portao portao, Evento evento) {
        String comodo = portao.getComodo();
        if (evento == Evento.ABERTO) {
            log("portão da " + comodo + " aberto: ativando câmeras e acendendo luzes do local");
            buscarTodos(Camera.class, comodo).forEach(Camera::ativar);
            buscarTodos(Lampada.class, comodo).forEach(Lampada::ligar);
        } else if (evento == Evento.FECHADO) {
            log("portão da " + comodo + " fechado: apagando luzes do local");
            buscarTodos(Lampada.class, comodo).forEach(Lampada::desligar);
        }
    }

    private void reagirACamera(Camera camera, Evento evento) {
        if (evento == Evento.MOVIMENTO_DETECTADO) {
            String comodo = camera.getComodo();
            log("movimento detectado na " + comodo + ": acendendo luzes do local");
            buscarTodos(Lampada.class, comodo).forEach(Lampada::ligar);
        }
    }

    private void reagirAoArCondicionado(ArCondicionado ar, Evento evento) {
        if (evento == Evento.LIGADO) {
            buscarTodos(Portao.class).stream()
                    .filter(Portao::isAberto)
                    .forEach(portao -> log("aviso: " + ar.getIdentificacao()
                            + " ligado com " + portao.getIdentificacao() + " aberto"));
        }
    }

    public List<Dispositivo> getDispositivos() {
        return Collections.unmodifiableList(dispositivos);
    }

    public <T extends Dispositivo> List<T> buscarTodos(Class<T> tipo) {
        return dispositivos.stream()
                .filter(tipo::isInstance)
                .map(tipo::cast)
                .toList();
    }

    public <T extends Dispositivo> List<T> buscarTodos(Class<T> tipo, String comodo) {
        return buscarTodos(tipo).stream()
                .filter(d -> d.getComodo().equalsIgnoreCase(comodo))
                .toList();
    }

    public <T extends Dispositivo> Optional<T> buscar(Class<T> tipo, String comodo) {
        return buscarTodos(tipo, comodo).stream().findFirst();
    }

    private void log(String mensagem) {
        System.out.println("[Central] " + mensagem);
    }
}
