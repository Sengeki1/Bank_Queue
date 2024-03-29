import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Cliente implements Runnable{
    private ThreadLocal<Integer> tempo;
    private Random rand;
    private Integer temporizador;
    public List<Integer> clientesAtendidos;
    private Semaphore Adalgiza;
    private Semaphore Dora;
    private Semaphore Juliana;
    private boolean atendente1;
    private boolean atendente2;
    private boolean atendente3;
    
    public Cliente(Semaphore atendente1, Semaphore atendente2, Semaphore atendente3, ThreadLocal<Integer> tempo, Random rand, List<Integer> clientesAtendidos) {
        this.Adalgiza = atendente1;
        this.Dora = atendente2;
        this.Juliana = atendente3;

        this.tempo = tempo;
        this.rand = rand;
        this.clientesAtendidos = clientesAtendidos;
    }

    @Override
    public void run() {
        try {
            System.out.println("Cliente chegou");
            while (this.clientesAtendidos.size() < 10) {
                sleep(rand.nextInt(1000, 5000));
                atendente1 = Adalgiza.tryAcquire();
                if (atendente1 && !atendente2 && !atendente3) {
                    System.out.println("Cliente " + (Thread.currentThread().getName()) + " esta sendo atendido pelo atendente " + "Adalgiza");

                    temporizador = rand.nextInt(4000, 8000);
                    tempo.set(temporizador);
                    sleep(temporizador);

                    System.out.println("Cliente " + (Thread.currentThread().getName()) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                    synchronized (this.clientesAtendidos) {
                        this.clientesAtendidos.add(1);
                    }

                    Adalgiza.release();
                    break;
                } else {
                    atendente2 = Dora.tryAcquire();
                    if (atendente2 && !atendente1 && !atendente3) {
                        System.out.println("Cliente " + (Thread.currentThread().getName()) + " esta sendo atendido pelo atendente " + "Dora");

                        temporizador = rand.nextInt(4000, 10000);
                        tempo.set(temporizador);
                        sleep(temporizador);

                        System.out.println("Cliente " + (Thread.currentThread().getName()) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                        synchronized (this.clientesAtendidos) {
                            this.clientesAtendidos.add(1);
                        }

                        Dora.release();
                        break;
                    } else {
                        atendente3 = Juliana.tryAcquire();
                        if (atendente3 && !atendente1 && !atendente2) {
                            System.out.println("Cliente " + (Thread.currentThread().getName()) + " esta sendo atendido pelo atendente " + "Juliana");

                            temporizador = rand.nextInt(6000, 9000);
                            tempo.set(temporizador);
                            sleep(temporizador);

                            System.out.println("Cliente " + (Thread.currentThread().getName()) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                            synchronized (this.clientesAtendidos) {
                                this.clientesAtendidos.add(1);
                            }

                            Juliana.release();
                            break;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
