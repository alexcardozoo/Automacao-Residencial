package automacao.interpretador;

import automacao.comando.Comando;
import automacao.comando.ComandoAbrir;
import automacao.comando.ComandoAtivar;
import automacao.comando.ComandoDesativar;
import automacao.comando.ComandoDesligar;
import automacao.comando.ComandoFechar;
import automacao.comando.ComandoLigar;
import automacao.dispositivo.Abrivel;
import automacao.dispositivo.Ativavel;
import automacao.dispositivo.Dispositivo;
import automacao.dispositivo.Ligavel;

public enum Acao {
    LIGAR("ligar"),
    DESLIGAR("desligar"),
    ABRIR("abrir"),
    FECHAR("fechar"),
    ATIVAR("ativar"),
    DESATIVAR("desativar");

    private final String verbo;

    Acao(String verbo) {
        this.verbo = verbo;
    }

    public Comando criarComando(Dispositivo dispositivo) {
        return switch (this) {
            case LIGAR -> new ComandoLigar(exigir(dispositivo, Ligavel.class));
            case DESLIGAR -> new ComandoDesligar(exigir(dispositivo, Ligavel.class));
            case ABRIR -> new ComandoAbrir(exigir(dispositivo, Abrivel.class));
            case FECHAR -> new ComandoFechar(exigir(dispositivo, Abrivel.class));
            case ATIVAR -> new ComandoAtivar(exigir(dispositivo, Ativavel.class));
            case DESATIVAR -> new ComandoDesativar(exigir(dispositivo, Ativavel.class));
        };
    }

    public String getVerbo() {
        return verbo;
    }

    private <T> T exigir(Dispositivo dispositivo, Class<T> capacidade) {
        if (!capacidade.isInstance(dispositivo)) {
            throw new ComandoInvalidoException(dispositivo.getIdentificacao() + " não suporta a ação '" + verbo + "'");
        }
        return capacidade.cast(dispositivo);
    }
}
