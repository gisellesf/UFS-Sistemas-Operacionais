package BestFit;

public class BlocoDeMemoria {
    int startAddress;
    int size;
    boolean free;
    BlocoDeMemoria next;

    public BlocoDeMemoria(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.free = true;
        this.next = null;
    }
}