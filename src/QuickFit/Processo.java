class Processo {

    private boolean onExecution;
    private int comprimento;
    private SegmentoMemoria segmento; //segmento de memória onde este processo está alocado

    public Processo(int comprimento) {
        this.comprimento = comprimento;
    }

    public boolean isOnExecution() {
        return onExecution;
    }

    public void setOnExecution(boolean onExecution) {
        this.onExecution = onExecution;
        
        if (!onExecution && segmento != null) {
            segmento.setOcupado(false);
        }
    }

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }

    public SegmentoMemoria getSegmento() {
        return segmento;
    }

    public void setSegmento(SegmentoMemoria segmento) {
        this.segmento = segmento;
    }
}