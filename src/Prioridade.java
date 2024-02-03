import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Prioridade implements Runnable{
    private ThreadLocal<Integer> tempo;
    private Random rand;
    private int id_cliente;
    private Integer temporizador;
    private Semaphore Adalgiza;
    private Semaphore Dora;
    private boolean atendente1;
    private boolean atendente2;
    private Cliente cliente;

    public Prioridade(Semaphore atendente1, Semaphore atendente2, ThreadLocal<Integer> tempo, int index, Random rand, Cliente cliente) {
        this.Adalgiza = atendente1;
        this.Dora = atendente2;

        this.tempo = tempo;
        this.id_cliente = index;
        this.rand = rand;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        while (true) {
            if (this.cliente.clientesAtendidos.size() > 3) {
                try {
                    if (this.id_cliente == 0) {
                        this.atendente1 = this.Adalgiza.tryAcquire();

                        if (this.atendente1) {
                            System.out.println("Gravida " + (this.id_cliente + 1) + " esta sendo atendido pelo atendente " + "Adalgiza");

                            this.temporizador = rand.nextInt(4000, 7000);
                            this.tempo.set(temporizador);
                            sleep(temporizador);

                            System.out.println("Gravida " + (this.id_cliente + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                            synchronized (cliente.clientesAtendidos) {
                                cliente.clientesAtendidos.add(1);
                            }

                            this.Adalgiza.release();
                            break;
                        }
                    }

                    if (this.id_cliente == 1) {
                        this.atendente2 = this.Dora.tryAcquire();

                        if (this.atendente2) {
                            System.out.println("Idoso " + (this.id_cliente + 1) + " esta sendo atendido pelo atendente " + "Dora");

                            this.temporizador = rand.nextInt(5000, 8000);
                            this.tempo.set(temporizador);
                            sleep(temporizador);

                            System.out.println("Idoso " + (this.id_cliente + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                            synchronized (cliente.clientesAtendidos) {
                                cliente.clientesAtendidos.add(1);
                            }

                            this.Dora.release();
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
