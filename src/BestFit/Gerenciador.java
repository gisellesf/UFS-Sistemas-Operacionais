package BestFit;

public class Gerenciador {
    BlocoDeMemoria head;

    public Gerenciador(int temanho) {
        this.head = new BlocoDeMemoria(0, temanho);
    }

    public void allocateMemory(int processSize) {
        BlocoDeMemoria prev = null;
        BlocoDeMemoria curr = head;
        BlocoDeMemoria bestFitBlock = null;
        int minFragmentation = Integer.MAX_VALUE;

        while (curr != null) {
            if (curr.free && curr.size >= processSize) {
                int fragmentation = curr.size - processSize;
                if (fragmentation < minFragmentation) {
                    bestFitBlock = curr;
                    minFragmentation = fragmentation;
                }
            }
            prev = curr;
            curr = curr.next;
        }

        if (bestFitBlock != null) {
            bestFitBlock.free = false;
            if (bestFitBlock.size > processSize) {
                BlocoDeMemoria newBlock = new BlocoDeMemoria(bestFitBlock.startAddress + processSize, bestFitBlock.size - processSize);
                newBlock.next = bestFitBlock.next;
                bestFitBlock.next = newBlock;
                bestFitBlock.size = processSize;
            }
            System.out.println("Memory allocated successfully.");
        } else {
            System.out.println("Insufficient memory to allocate.");
        }
    }

    public void deallocateMemory(int startAddress) {
        BlocoDeMemoria prev = null;
        BlocoDeMemoria curr = head;

        while (curr != null && curr.startAddress != startAddress) {
            prev = curr;
            curr = curr.next;
        }

        if (curr != null && !curr.free) {
            curr.free = true;

            // Merge adjacent free blocks
            if (curr.next != null && curr.next.free) {
                curr.size += curr.next.size;
                curr.next = curr.next.next;
            }
            if (prev != null && prev.free) {
                prev.size += curr.size;
                prev.next = curr.next;
            }
            System.out.println("Memory deallocated successfully.");
        } else {
            System.out.println("Invalid memory block to deallocate.");
        }
    }

    public void displayMemory() {
        BlocoDeMemoria curr = head;
        while (curr != null) {
            System.out.println("Start Address: " + curr.startAddress + ", Size: " + curr.size + ", Free: " + curr.free);
            curr = curr.next;
        }
    }

    public static void main(String[] args) {
        Gerenciador memoryManager = new Gerenciador(100);

        memoryManager.allocateMemory(20);
        memoryManager.allocateMemory(30);
        memoryManager.allocateMemory(15);

        System.out.println("Memory after allocation:");
        memoryManager.displayMemory();

        memoryManager.deallocateMemory(20);

        System.out.println("Memory after deallocation:");
        memoryManager.displayMemory();
    }
    
}
