import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        int alunos = 10;
        Random rand = new Random();
        Timer timer = new Timer();

        Semaphore atendente1 = new Semaphore(1);
        Semaphore atendente2 = new Semaphore(1);
        Semaphore atendente3 = new Semaphore(1);
        ThreadLocal<Integer> tempo = new ThreadLocal<>();

        List<Integer> alunosAtendidos = new ArrayList<>();

        System.out.println("Atendimento Aberto");
        for (int i = 0; i < alunos; i++) {
            Thread aluno = new Thread(new Estudante(atendente1, atendente2, atendente3, tempo, i, rand, alunosAtendidos));
            aluno.start();
        }

        timer.schedule(new Task(alunosAtendidos, alunos), 10000, 10000);
    }
}