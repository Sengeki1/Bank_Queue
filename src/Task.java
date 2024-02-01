import java.util.List;
import java.util.TimerTask;

public class Task extends TimerTask {
    private List<Integer> alunosAtendidos;
    private int aluno;
    private int fila;
    public Task(List<Integer> alunosAtendidos, int aluno) {
        this.alunosAtendidos = alunosAtendidos;
        this.aluno = aluno;
    }
    @Override
    public void run() {
        if (this.alunosAtendidos.size() < 10) {
            fila = this.aluno - this.alunosAtendidos.size();
            System.out.println("Existem " + fila + " estudantes na fila");
        } else {
            System.out.println("Todos os estudantes foram atendidos");
            this.cancel();
        }
    }
}
