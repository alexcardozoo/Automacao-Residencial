package automacao;

import automacao.comando.ComandoAbrir;
import automacao.comando.ComandoDesligar;
import automacao.comando.ComandoFechar;
import automacao.comando.ComandoLigar;
import automacao.comando.ControleRemoto;
import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;
import automacao.mediator.CentralAutomacao;

public class Main {

    public static void main(String[] args) {
        CentralAutomacao central = new CentralAutomacao();

        Lampada luzSala = new Lampada("sala");
        Lampada luzGaragem = new Lampada("garagem");
        ArCondicionado arQuarto = new ArCondicionado("quarto");
        Portao portaoGaragem = new Portao("garagem");
        Camera cameraGaragem = new Camera("garagem");

        central.registrar(luzSala);
        central.registrar(luzGaragem);
        central.registrar(arQuarto);
        central.registrar(portaoGaragem);
        central.registrar(cameraGaragem);

        ControleRemoto controle = new ControleRemoto();

        titulo("Dispositivos registrados na central");
        central.getDispositivos().forEach(System.out::println);

        titulo("Executando comandos pelo controle");
        controle.executar(new ComandoLigar(luzSala));
        controle.executar(new ComandoLigar(arQuarto));
        controle.executar(new ComandoAbrir(portaoGaragem));

        titulo("Desfazendo o último comando (abrir portão)");
        controle.desfazerUltimo();

        titulo("Mais comandos");
        controle.executar(new ComandoDesligar(luzSala));
        controle.executar(new ComandoFechar(portaoGaragem));

        titulo("Histórico (mais recente primeiro)");
        controle.getHistorico().forEach(c -> System.out.println("- " + c));

        titulo("Desfazendo dois comandos");
        controle.desfazerUltimo();
        controle.desfazerUltimo();

        titulo("Estado final");
        central.getDispositivos().forEach(System.out::println);
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}
