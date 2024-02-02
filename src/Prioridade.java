import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Prioridade implements Runnable{
    private ThreadLocal<Integer> tempo;
    private Random rand;
    private int id_cliente;
    private Integer temporizador;
    private List<Integer> clientesAtendidos;
    private Semaphore Adalgiza;
    private Semaphore Dora;
    private Semaphore Juliana;
    private boolean atendente1;
    private boolean atendente2;
    private boolean atendente3;
    public Prioridade(Semaphore atendente1, Semaphore atendente2, Semaphore atendente3, ThreadLocal<Integer> tempo, int index, Random rand, List<Integer> clientesAtendidos) {
        this.Adalgiza = atendente1;
        this.Dora = atendente2;
        this.Juliana = atendente3;

        this.tempo = tempo;
        this.id_cliente = index;
        this.rand = rand;
        this.clientesAtendidos = clientesAtendidos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (id_cliente == 2 && this.clientesAtendidos.size() > 2) {
                    atendente1 = this.Adalgiza.tryAcquire();
                }
                if (atendente1) {
                    System.out.println("Gravida " + (this.id_cliente + 1) + " esta sendo atendido pelo atendente " + "Adalgiza");

                    temporizador = rand.nextInt(2000, 5000);
                    tempo.set(temporizador);
                    sleep(temporizador);

                    System.out.println("Gravida " + (this.id_cliente + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                    synchronized (this.clientesAtendidos) {
                        this.clientesAtendidos.add(1);
                    }

                    Adalgiza.release();
                    break;
                } else {
                    sleep(rand.nextInt(500));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
