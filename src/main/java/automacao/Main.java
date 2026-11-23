package automacao;

import automacao.comando.ComandoAbrir;
import automacao.comando.ComandoLigar;
import automacao.comando.ControleRemoto;
import automacao.comando.MacroComando;
import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;
import automacao.mediator.CentralAutomacao;
import automacao.rotina.ComandoExecutarRotina;
import automacao.rotina.RotinaNoturna;
import automacao.rotina.RotinaSaidaCasa;

public class Main {

    public static void main(String[] args) {
        CentralAutomacao central = new CentralAutomacao();

        Lampada luzSala = new Lampada("sala");
        Lampada luzGaragem = new Lampada("garagem");
        ArCondicionado arQuarto = new ArCondicionado("quarto");
        ArCondicionado arSala = new ArCondicionado("sala");
        Portao portaoGaragem = new Portao("garagem");
        Camera cameraGaragem = new Camera("garagem");

        central.registrar(luzSala);
        central.registrar(luzGaragem);
        central.registrar(arQuarto);
        central.registrar(arSala);
        central.registrar(portaoGaragem);
        central.registrar(cameraGaragem);

        ControleRemoto controle = new ControleRemoto();

        titulo("Chegando em casa (macro)");
        controle.executar(new MacroComando("chegar em casa",
                new ComandoAbrir(portaoGaragem),
                new ComandoLigar(luzSala),
                new ComandoLigar(arSala)));

        titulo("Estado antes da rotina noturna");
        central.getDispositivos().forEach(System.out::println);

        titulo("Rotina noturna");
        controle.executar(new ComandoExecutarRotina(new RotinaNoturna(central)));

        titulo("Estado após a rotina noturna");
        central.getDispositivos().forEach(System.out::println);

        titulo("Desfazendo a rotina noturna");
        controle.desfazerUltimo();

        titulo("Estado após desfazer");
        central.getDispositivos().forEach(System.out::println);

        titulo("Rotina de saída de casa");
        controle.executar(new ComandoExecutarRotina(new RotinaSaidaCasa(central)));

        titulo("Estado final");
        central.getDispositivos().forEach(System.out::println);

        titulo("Saída de casa numa casa sem câmeras (hook cancela a rotina)");
        CentralAutomacao casaSemCamera = new CentralAutomacao();
        casaSemCamera.registrar(new Lampada("sala"));
        new RotinaSaidaCasa(casaSemCamera).executar();
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}
