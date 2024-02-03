import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        int clientes = 10;
        Random rand = new Random();
        Timer timer = new Timer();

        Semaphore atendente1 = new Semaphore(1);
        Semaphore atendente2 = new Semaphore(1);
        Semaphore atendente3 = new Semaphore(1);
        ThreadLocal<Integer> tempo = new ThreadLocal<>();

        List<Integer> clientesAtendidos = new ArrayList<>();

        System.out.println("Atendimento Aberto");
        Cliente runnable = new Cliente(atendente1, atendente2, atendente3, tempo, rand, clientesAtendidos);
        for (int i = 0; i < clientes - 2; i++) {
            Thread cliente = new Thread(runnable);
            cliente.start();
        }

        for (int i = 0; i < 2; i++) {
            Thread prioridade = new Thread(new Prioridade(atendente1, atendente2, tempo, i, rand, runnable));
            prioridade.start();
        }

        timer.schedule(new Task(clientesAtendidos, clientes), 10000, 10000);
    }
}