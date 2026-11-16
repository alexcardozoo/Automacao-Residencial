package automacao;

import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Dispositivo;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Lampada luzSala = new Lampada("sala");
        ArCondicionado arQuarto = new ArCondicionado("quarto");
        Portao portaoGaragem = new Portao("garagem");
        Camera cameraGaragem = new Camera("garagem");

        List<Dispositivo> dispositivos = List.of(luzSala, arQuarto, portaoGaragem, cameraGaragem);

        System.out.println("=== Dispositivos da casa ===");
        dispositivos.forEach(System.out::println);

        System.out.println();
        System.out.println("=== Acionando dispositivos ===");
        luzSala.ligar();
        arQuarto.setTemperatura(22);
        arQuarto.ligar();
        portaoGaragem.abrir();
        cameraGaragem.ativar();

        System.out.println();
        System.out.println("=== Estado final ===");
        dispositivos.forEach(System.out::println);
    }
}
