package automacao;

import automacao.comando.ControleRemoto;
import automacao.dispositivo.ArCondicionado;
import automacao.dispositivo.Camera;
import automacao.dispositivo.Lampada;
import automacao.dispositivo.Portao;
import automacao.interpretador.ComandoInvalidoException;
import automacao.interpretador.Contexto;
import automacao.interpretador.InterpretadorComando;
import automacao.mediator.CentralAutomacao;
import automacao.rotina.RotinaNoturna;
import automacao.rotina.RotinaSaidaCasa;
import automacao.visitor.DiagnosticoVisitor;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        CentralAutomacao central = new CentralAutomacao();

        central.registrar(new Lampada("sala"));
        central.registrar(new Lampada("quarto"));
        central.registrar(new Lampada("garagem"));
        central.registrar(new ArCondicionado("quarto"));
        central.registrar(new ArCondicionado("sala"));
        central.registrar(new Portao("garagem"));
        central.registrar(new Camera("garagem"));

        Contexto contexto = new Contexto(central);
        contexto.registrarRotina("noturna", new RotinaNoturna(central));
        contexto.registrarRotina("saida", new RotinaSaidaCasa(central));

        InterpretadorComando interpretador = new InterpretadorComando(contexto);
        ControleRemoto controle = new ControleRemoto();

        List<String> textos = List.of(
                "ligar luz sala",
                "abrir portao",
                "Ligar o Ar-Condicionado do Quarto",
                "desligar ar quarto",
                "acender luz",
                "executar rotina noturna",
                "ativar camera garagem",
                "executar rotina saída de casa",
                "ligar portao",
                "voar luz sala",
                "ligar geladeira cozinha",
                "ligar luz banheiro",
                "executar rotina ferias");

        for (String texto : textos) {
            System.out.println();
            System.out.println("> " + texto);
            try {
                controle.executar(interpretador.interpretar(texto));
            } catch (ComandoInvalidoException e) {
                System.out.println("Comando inválido: " + e.getMessage());
            }
        }

        System.out.println();
        System.out.println(central.aplicar(new DiagnosticoVisitor()).getRelatorio());
    }
}
