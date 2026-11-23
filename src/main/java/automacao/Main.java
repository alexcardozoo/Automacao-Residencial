package automacao;

import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Persiana;
import automacao.dispositivo.Portao;
import automacao.interpretador.Contexto;
import automacao.mediator.CentralAutomacao;
import automacao.rotina.RotinaNoturna;
import automacao.rotina.RotinaSaidaCasa;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CentralAutomacao central = montarCasa();

        Contexto contexto = new Contexto(central);
        contexto.registrarRotina("noturna", new RotinaNoturna(central));
        contexto.registrarRotina("saida", new RotinaSaidaCasa(central));

        try (Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8)) {
            new ConsoleAutomacao(contexto).iniciar(entrada);
        }
    }

    private static CentralAutomacao montarCasa() {
        CentralAutomacao central = new CentralAutomacao();
        central.registrar(new Lampada("sala"));
        central.registrar(new Lampada("quarto"));
        central.registrar(new Lampada("garagem"));
        central.registrar(new ArCondicionado("sala"));
        central.registrar(new ArCondicionado("quarto"));
        central.registrar(new Portao("garagem"));
        central.registrar(new Camera("garagem"));
        central.registrar(new Persiana("sala"));
        central.registrar(new Persiana("quarto"));
        return central;
    }
}
