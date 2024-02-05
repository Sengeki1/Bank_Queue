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
    private List<Integer> clientesAtendidos;

    public Prioridade(Semaphore atendente1, Semaphore atendente2, ThreadLocal<Integer> tempo, int index, Random rand, List<Integer> clientesAtendidos) {
        this.Adalgiza = atendente1;
        this.Dora = atendente2;

        this.tempo = tempo;
        this.id_cliente = index;
        this.rand = rand;
        this.clientesAtendidos = clientesAtendidos;
    }

    @Override
    public void run() {
        if (this.id_cliente == 2) {
            System.out.println("Gravida chegou");
        } else {
            System.out.println("Idoso chegou");
        }

        try {
            sleep(1000);
            while (true) {
                synchronized (this.clientesAtendidos) {

                    if (this.id_cliente == 2) {
                        this.atendente1 = this.Adalgiza.tryAcquire();
                        if (this.atendente1) {
                            System.out.println("Gravida " + (this.id_cliente + 1) + " esta sendo atendido pelo atendente " + "Adalgiza");

                            this.temporizador = rand.nextInt(4000, 7000);
                            this.tempo.set(temporizador);
                            sleep(temporizador);

                            System.out.println("Gravida " + (this.id_cliente + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                            synchronized (this.clientesAtendidos) {
                                this.clientesAtendidos.add(1);
                            }

                            this.Adalgiza.release();
                            break;
                        }
                    } else {
                        this.atendente2 = this.Dora.tryAcquire();

                        if (this.atendente2) {
                            System.out.println("Idoso " + (this.id_cliente + 1) + " esta sendo atendido pelo atendente " + "Dora");

                            this.temporizador = rand.nextInt(5000, 8000);
                            this.tempo.set(temporizador);
                            sleep(temporizador);

                            System.out.println("Idoso " + (this.id_cliente + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                            synchronized (this.clientesAtendidos) {
                                this.clientesAtendidos.add(1);
                            }

                            this.Dora.release();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

