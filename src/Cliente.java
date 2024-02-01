import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public class Estudante implements Runnable{
    private ThreadLocal<Integer> tempo;
    private Random rand;
    private int id_aluno;
    private Integer temporizador;
    private List<Integer> alunosAtendidos;
    private Semaphore Adalgiza;
    private Semaphore Dora;
    private Semaphore Juliana;
    private boolean atendente1;
    private boolean atendente2;
    private boolean atendente3;


    public Estudante(Semaphore atendente1, Semaphore atendente2, Semaphore atendente3, ThreadLocal<Integer> tempo, int index, Random rand, List<Integer> alunosAtendidos) {
        this.Adalgiza = atendente1;
        this.Dora = atendente2;
        this.Juliana = atendente3;

        this.tempo = tempo;
        this.id_aluno = index;
        this.rand = rand;
        this.alunosAtendidos = alunosAtendidos;
    }

    @Override
    public void run() {
        try {
            while (this.alunosAtendidos.size() < 10) {
                sleep(rand.nextInt(2000, 5000));
                atendente1 = Adalgiza.tryAcquire();
                if (atendente1 && !atendente2 && !atendente3) {
                    System.out.println("Estudante " + (this.id_aluno + 1) + " esta sendo atendido pelo atendente " + "Adalgiza");

                    temporizador = rand.nextInt(1000, 5000);
                    tempo.set(temporizador);
                    sleep(temporizador);

                    System.out.println("Estudante " + (this.id_aluno + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                    synchronized (this.alunosAtendidos) {
                        this.alunosAtendidos.add(1);
                    }

                    Adalgiza.release();
                    break;
                } else {
                    atendente2 = Dora.tryAcquire();
                    if (atendente2 && !atendente1 && !atendente3) {
                        System.out.println("Estudante " + (this.id_aluno + 1) + " esta sendo atendido pelo atendente " + "Dora");

                        temporizador = rand.nextInt(1000, 5000);
                        tempo.set(temporizador);
                        sleep(temporizador);

                        System.out.println("Estudante " + (this.id_aluno + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                        synchronized (this.alunosAtendidos) {
                            this.alunosAtendidos.add(1);
                        }

                        Dora.release();
                        break;
                    } else {
                        atendente3 = Juliana.tryAcquire();
                        if (atendente3 && !atendente1 && !atendente2) {
                            System.out.println("Estudante " + (this.id_aluno + 1) + " esta sendo atendido pelo atendente " + "Juliana");

                            temporizador = rand.nextInt(1000, 5000);
                            tempo.set(temporizador);
                            sleep(temporizador);

                            System.out.println("Estudante " + (this.id_aluno + 1) + " terminou o atendimento. Tempo de atendimento: " + tempo.get() + " millisegundos");
                            synchronized (this.alunosAtendidos) {
                                this.alunosAtendidos.add(1);
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
