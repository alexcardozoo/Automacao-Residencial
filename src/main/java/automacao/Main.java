package automacao;

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

        System.out.println("=== Dispositivos registrados na central ===");
        central.getDispositivos().forEach(System.out::println);

        System.out.println();
        System.out.println("=== Abrindo o portão (a central aciona câmera e luz) ===");
        portaoGaragem.abrir();

        System.out.println();
        System.out.println("=== Ligando o ar com o portão aberto (a central avisa) ===");
        arQuarto.ligar();

        System.out.println();
        System.out.println("=== Fechando o portão (a central apaga a luz da garagem) ===");
        portaoGaragem.fechar();

        System.out.println();
        System.out.println("=== Câmera detecta movimento (a central acende a luz) ===");
        cameraGaragem.detectarMovimento();

        System.out.println();
        System.out.println("=== Estado final ===");
        central.getDispositivos().forEach(System.out::println);
    }
}
