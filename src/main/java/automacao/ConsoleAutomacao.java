package automacao;

import automacao.comando.ControleRemoto;
import automacao.dispositivo.Camera;
import automacao.interpretador.ComandoInvalidoException;
import automacao.interpretador.Contexto;
import automacao.interpretador.InterpretadorComando;
import automacao.mediator.CentralAutomacao;
import automacao.visitor.DiagnosticoVisitor;
import automacao.visitor.InventarioVisitor;

import java.util.List;
import java.util.Scanner;

public class ConsoleAutomacao {

    private final CentralAutomacao central;
    private final Contexto contexto;
    private final InterpretadorComando interpretador;
    private final ControleRemoto controle = new ControleRemoto();

    public ConsoleAutomacao(Contexto contexto) {
        this.contexto = contexto;
        this.central = contexto.getCentral();
        this.interpretador = new InterpretadorComando(contexto);
    }

    public void iniciar(Scanner entrada) {
        System.out.println("=== Central de Automação Residencial ===");
        System.out.println("Digite 'ajuda' para ver os comandos disponíveis.");

        while (true) {
            System.out.println();
            System.out.print("> ");
            if (!entrada.hasNextLine()) {
                break;
            }
            String linha = entrada.nextLine().trim();
            if (linha.isEmpty()) {
                continue;
            }
            if (!processar(linha)) {
                break;
            }
        }
        System.out.println("Até logo!");
    }

    private boolean processar(String linha) {
        String normalizada = InterpretadorComando.normalizar(linha);
        String[] partes = normalizada.split("\\s+", 2);

        switch (partes[0]) {
            case "sair" -> {
                return false;
            }
            case "ajuda" -> mostrarAjuda();
            case "status" -> central.getDispositivos().forEach(System.out::println);
            case "historico" -> mostrarHistorico();
            case "desfazer" -> controle.desfazerUltimo();
            case "diagnostico" -> System.out.println(central.aplicar(new DiagnosticoVisitor()).getRelatorio());
            case "inventario" -> System.out.println(central.aplicar(new InventarioVisitor()).getRelatorio());
            case "movimento" -> simularMovimento(partes.length > 1 ? partes[1] : "");
            default -> executarTexto(linha);
        }
        return true;
    }

    private void executarTexto(String linha) {
        try {
            controle.executar(interpretador.interpretar(linha));
        } catch (ComandoInvalidoException e) {
            System.out.println("Comando inválido: " + e.getMessage());
            System.out.println("Digite 'ajuda' para ver exemplos.");
        }
    }

    private void simularMovimento(String comodo) {
        if (comodo.isBlank()) {
            System.out.println("Use: movimento <cômodo>");
            return;
        }
        List<Camera> cameras = central.buscarTodos(Camera.class, comodo);
        if (cameras.isEmpty()) {
            System.out.println("Nenhuma câmera em '" + comodo + "'");
            return;
        }
        cameras.forEach(Camera::detectarMovimento);
    }

    private void mostrarHistorico() {
        List<String> historico = controle.getHistorico();
        if (historico.isEmpty()) {
            System.out.println("Nenhum comando executado ainda.");
            return;
        }
        System.out.println("Histórico (mais recente primeiro):");
        historico.forEach(item -> System.out.println("  - " + item));
    }

    private void mostrarAjuda() {
        System.out.println("""
                Comandos de dispositivos (texto livre):
                  ligar luz sala          desligar ar quarto
                  abrir portao            fechar portao garagem
                  ativar camera garagem   desativar camera
                  acender luz             (sem cômodo: todos do tipo)

                Rotinas:
                  executar rotina %s

                Sistema:
                  status                  estado de todos os dispositivos
                  historico               comandos executados
                  desfazer                desfaz o último comando, macro ou rotina
                  diagnostico             verifica problemas (visitor)
                  inventario              lista dispositivos por cômodo (visitor)
                  movimento <cômodo>      simula o sensor da câmera
                  sair""".formatted(String.join(" | ", contexto.getRotinas().keySet())));
    }
}
