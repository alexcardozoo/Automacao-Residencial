package automacao.visitor;

import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Dispositivo;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InventarioVisitor implements DispositivoVisitor {

    private final Map<String, List<String>> porComodo = new TreeMap<>();
    private final Map<String, Integer> porTipo = new TreeMap<>();

    @Override
    public void visitar(Lampada lampada) {
        registrar(lampada, lampada.getTipo());
    }

    @Override
    public void visitar(ArCondicionado ar) {
        registrar(ar, ar.getTipo() + " (" + ar.getTemperatura() + "°C programado)");
    }

    @Override
    public void visitar(Portao portao) {
        registrar(portao, portao.getTipo());
    }

    @Override
    public void visitar(Camera camera) {
        registrar(camera, camera.getTipo());
    }

    @Override
    public void visitar(Dispositivo dispositivo) {
        registrar(dispositivo, dispositivo.getTipo());
    }

    public int getTotal() {
        return porTipo.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String getRelatorio() {
        StringBuilder relatorio = new StringBuilder("Inventário da casa\n");
        porComodo.forEach((comodo, itens) -> {
            relatorio.append("  ").append(comodo).append(":\n");
            itens.forEach(item -> relatorio.append("    - ").append(item).append('\n'));
        });
        relatorio.append("  Por tipo:\n");
        porTipo.forEach((tipo, quantidade) ->
                relatorio.append("    ").append(tipo).append(": ").append(quantidade).append('\n'));
        relatorio.append("  Total: ").append(getTotal()).append(" dispositivos");
        return relatorio.toString();
    }

    private void registrar(Dispositivo dispositivo, String descricao) {
        porComodo.computeIfAbsent(dispositivo.getComodo(), c -> new ArrayList<>()).add(descricao);
        porTipo.merge(dispositivo.getTipo(), 1, Integer::sum);
    }
}
