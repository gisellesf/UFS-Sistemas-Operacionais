package QuickFit;

import java.util.LinkedList;

public class Gerenciador {

    private int tamanhoUnidadeAlocacao;
    private int tamanhoMemoria;
    LinkedList<SegmentoMemoria> listaEncadeada;

    public Gerenciador(int tamanhoUnidadeAlocacao, int tamanhoMemoria) {
        this.listaEncadeada = new LinkedList<>();
        this.tamanhoMemoria = tamanhoMemoria;
        this.tamanhoUnidadeAlocacao = tamanhoUnidadeAlocacao;

        // Inicializa a lista encadeada com um segmento de memória que ocupa toda a memória
        listaEncadeada.add(new SegmentoMemoria(false, 0, tamanhoMemoria));
    }

    public void alocaProcessoQuickFit(Processo p) {
        int tamanhoProcesso = p.getComprimento();
        int indice = buscaSegmento(tamanhoProcesso);
        
        if (indice == -1) {
            System.out.println("Não há memória suficiente para alocar o processo de tamanho " + tamanhoProcesso);
            System.out.println("\n");
        } else {
            SegmentoMemoria seg = listaEncadeada.get(indice);
            SegmentoMemoria novoSeg = new SegmentoMemoria(true, seg.getPosicaoInicial(), tamanhoProcesso);
            listaEncadeada.add(indice + 1, novoSeg);
            
            // Atualiza o tamanho do segmento existente
            seg.setPosicaoInicial(seg.getPosicaoInicial() + tamanhoProcesso);
            seg.setComprimento(seg.getComprimento() - tamanhoProcesso);
            
            // Remove segmentos vazios
            if (seg.getComprimento() == 0) {
                listaEncadeada.remove(seg);
            }
            
            p.setSegmento(novoSeg);
        }
    }

    public int buscaSegmento(int tamanhoProcesso) {
        for (int i = 0; i < listaEncadeada.size(); i++) {
            SegmentoMemoria seg = listaEncadeada.get(i);
            if (!seg.isOcupado() && seg.getComprimento() >= tamanhoProcesso) {
                return i;
            }
        }
        return -1;
    }

    public void exibeListaEncadeada() {
        System.out.println("-------- Segmentos de Memória -------- \n");
        for (int i = 0; i < listaEncadeada.size(); i++) {
            SegmentoMemoria seg = listaEncadeada.get(i);
            if (listaEncadeada.size() - 1 == i) {
                System.out.print(seg);
            } else {
                System.out.print(seg + " --> ");
            }
        }
        System.out.println("\n\n");
    }

    public static void main(String[] args) {
        Gerenciador gerenciador = new Gerenciador(2, 62);

        Processo processoA  = new Processo(10);
        Processo processoB = new Processo(8);
        Processo processoC = new Processo(6);

        System.out.println("-------- Gerenciamento com Quick Fit -------- \n");
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoQuickFit(processoA);
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoQuickFit(processoB);
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoQuickFit(processoC);
        gerenciador.exibeListaEncadeada();

        processoC.setOnExecution(false);
        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcessoQuickFit(processoC);
        gerenciador.exibeListaEncadeada();
    }
}