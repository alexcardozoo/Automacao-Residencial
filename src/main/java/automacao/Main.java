package automacao;

import automacao.comando.ComandoAbrir;
import automacao.comando.ComandoAtivar;
import automacao.comando.ComandoDesligar;
import automacao.comando.ComandoFechar;
import automacao.comando.ComandoLigar;
import automacao.comando.ControleRemoto;
import automacao.comando.MacroComando;
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

        titulo("Comando simples");
        controle.executar(new ComandoLigar(luzSala));

        MacroComando chegarEmCasa = new MacroComando("chegar em casa",
                new ComandoAbrir(portaoGaragem),
                new ComandoLigar(luzSala),
                new ComandoLigar(arQuarto));

        MacroComando sairDeCasa = new MacroComando("sair de casa",
                new ComandoDesligar(luzSala),
                new ComandoDesligar(arQuarto),
                new ComandoFechar(portaoGaragem),
                new ComandoAtivar(cameraGaragem));

        titulo("Executando macro 'chegar em casa'");
        controle.executar(chegarEmCasa);

        titulo("Estado após a macro");
        central.getDispositivos().forEach(System.out::println);

        titulo("Desfazendo a última operação (a macro inteira)");
        controle.desfazerUltimo();

        titulo("Estado após desfazer");
        central.getDispositivos().forEach(System.out::println);

        titulo("Executando macro 'sair de casa'");
        controle.executar(sairDeCasa);

        titulo("Histórico (mais recente primeiro)");
        controle.getHistorico().forEach(c -> System.out.println("- " + c));

        titulo("Desfazendo a macro 'sair de casa' e o comando simples");
        controle.desfazerUltimo();
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
