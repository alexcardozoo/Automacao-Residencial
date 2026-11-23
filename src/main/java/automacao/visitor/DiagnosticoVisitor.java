package automacao.visitor;

import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Dispositivo;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticoVisitor implements DispositivoVisitor {

    private static final int TEMPERATURA_MINIMA_RECOMENDADA = 18;

    private final List<String> linhas = new ArrayList<>();
    private int alertas;

    @Override
    public void visitar(Lampada lampada) {
        ok(lampada);
    }

    @Override
    public void visitar(ArCondicionado ar) {
        if (ar.isLigado() && ar.getTemperatura() < TEMPERATURA_MINIMA_RECOMENDADA) {
            alerta(ar, "temperatura abaixo de " + TEMPERATURA_MINIMA_RECOMENDADA + "°C, alto consumo de energia");
        } else {
            ok(ar);
        }
    }

    @Override
    public void visitar(Portao portao) {
        if (portao.isAberto()) {
            alerta(portao, "portão aberto, casa vulnerável");
        } else {
            ok(portao);
        }
    }

    @Override
    public void visitar(Camera camera) {
        if (!camera.isAtivo()) {
            alerta(camera, "câmera desativada, área sem vigilância");
        } else {
            ok(camera);
        }
    }

    @Override
    public void visitar(Dispositivo dispositivo) {
        linhas.add("[?] " + dispositivo + " -> sem diagnóstico específico");
    }

    public int getQuantidadeAlertas() {
        return alertas;
    }

    public String getRelatorio() {
        StringBuilder relatorio = new StringBuilder("Diagnóstico da casa\n");
        linhas.forEach(linha -> relatorio.append("  ").append(linha).append('\n'));
        relatorio.append(alertas == 0 ? "  Nenhum problema encontrado." : "  Total de alertas: " + alertas);
        return relatorio.toString();
    }

    private void ok(Dispositivo dispositivo) {
        linhas.add("[OK] " + dispositivo);
    }

    private void alerta(Dispositivo dispositivo, String motivo) {
        alertas++;
        linhas.add("[ALERTA] " + dispositivo + " -> " + motivo);
    }
}
