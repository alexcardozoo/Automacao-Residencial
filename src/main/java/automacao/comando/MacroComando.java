package automacao.comando;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MacroComando implements Comando {

    private final String nome;
    private final List<Comando> comandos;

    public MacroComando(String nome, List<Comando> comandos) {
        if (comandos == null || comandos.isEmpty()) {
            throw new IllegalArgumentException("A macro precisa de pelo menos um comando");
        }
        this.nome = nome;
        this.comandos = List.copyOf(comandos);
    }

    public MacroComando(String nome, Comando... comandos) {
        this(nome, List.of(comandos));
    }

    @Override
    public void executar() {
        for (Comando comando : comandos) {
            log("-> " + comando.getDescricao());
            comando.executar();
        }
    }

    @Override
    public void desfazer() {
        List<Comando> invertidos = new ArrayList<>(comandos);
        Collections.reverse(invertidos);
        for (Comando comando : invertidos) {
            log("<- desfazendo " + comando.getDescricao());
            comando.desfazer();
        }
    }

    public List<Comando> getComandos() {
        return comandos;
    }

    @Override
    public String getDescricao() {
        return "macro '" + nome + "' (" + comandos.size() + " comandos)";
    }

    private void log(String mensagem) {
        System.out.println("[Macro " + nome + "] " + mensagem);
    }
}
