package NextFit;

import java.util.LinkedList;

public class Gerenciador {

    private int ultimoIndiceAlocacao;
    private int tamanhoUnidadeAlocacao;
    private int tamanhoMemoria;
    private int comprimentoMemoria;
    LinkedList<SegmentoMemoria> listaEncadeada;

    public Gerenciador(int tamanhoUnidadeAlocacao, int tamanhoMemoria) {
        this.listaEncadeada = new LinkedList<>();
        this.tamanhoMemoria = tamanhoMemoria;
        this.tamanhoUnidadeAlocacao = tamanhoUnidadeAlocacao;
        this.ultimoIndiceAlocacao = 0;
        this.comprimentoMemoria = tamanhoMemoria / tamanhoUnidadeAlocacao;

        listaEncadeada.add(new SegmentoMemoria(false, 0, comprimentoMemoria));
    }

    public void alocaProcessoNextFit(Processo p) {
        SegmentoMemoria seg = new SegmentoMemoria(true, 0, p.getComprimento());
        p.setSegmento(seg);

        int indice = buscaSegmento(seg.getComprimento(), ultimoIndiceAlocacao);

        if (indice == -1) {
            System.out.println("Não existe memória suficiente para alocar este processo de comprimento " + p.getComprimento());
            System.out.println("\n");
        } else {
            atualizaLista(indice, seg);
            ultimoIndiceAlocacao = indice; // Atualiza o último índice de alocação
        }
    }

    public int buscaSegmento(int comprimento, int indiceInicio) {
        for (int a = indiceInicio; a < listaEncadeada.size(); a++) {
            if (!listaEncadeada.get(a).isOcupado() && listaEncadeada.get(a).getComprimento() >= comprimento) {
                return a;
            }
        }
        for (int a = 0; a < indiceInicio; a++) {
            if (!listaEncadeada.get(a).isOcupado() && listaEncadeada.get(a).getComprimento() >= comprimento) {
                return a;
            }
        }
        return -1;
    }

    public void atualizaLista(int indice, SegmentoMemoria seg) {
        int comprimento = listaEncadeada.get(indice).getComprimento();
        if (comprimento == seg.getComprimento()) {
            seg.setPosicaoInicial(listaEncadeada.get(indice).getPosicaoInicial());
            listaEncadeada.set(indice, seg);
        } else {
            seg.setPosicaoInicial(listaEncadeada.get(indice).getPosicaoInicial());
            listaEncadeada.add(indice, seg);
            SegmentoMemoria segAntigo = listaEncadeada.get(indice + 1);
            segAntigo.setComprimento(segAntigo.getComprimento() - seg.getComprimento());
            segAntigo.setPosicaoInicial(seg.getPosicaoInicial() + seg.getComprimento());
            SegmentoMemoria aux;
            for (int a = indice + 2; a < listaEncadeada.size(); a++) {
                aux = listaEncadeada.get(a);
                aux.setPosicaoInicial(listaEncadeada.get(a - 1).getPosicaoInicial() + listaEncadeada.get(a - 1).getComprimento());
            }
        }
    }

    public void exibeListaEncadeada() {
        System.out.println("-------- Segmentos de Memória -------- \n");
        for (int a = 0; a < listaEncadeada.size(); a++) {
            if (listaEncadeada.size() - 1 == a) {
                System.out.print(listaEncadeada.get(a));
            } else {
                System.out.print(listaEncadeada.get(a) + " --> ");
            }
        }
        System.out.println("\n\n");
    }

    public static void main(String[] args) {
        Gerenciador gerenciador = new Gerenciador(2, 62);

        Processo processoA  = new Processo(10);
        Processo processoB = new Processo(8);
        Processo processoC = new Processo(6);

        System.out.println("-------- Gerencimento com Next Fit -------- \n");
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoNextFit(processoA);
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoNextFit(processoB);
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoNextFit(processoC);
        gerenciador.exibeListaEncadeada();

        processoC.setOnExecution(false);
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoNextFit(processoC);
        gerenciador.exibeListaEncadeada();
    }
}
