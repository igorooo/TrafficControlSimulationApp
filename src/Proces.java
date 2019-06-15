import java.util.Random;

public class Proces {

    Random rand = new Random();
    private int CPU_USAGE, TIME_AMOUNT, FINISH_TIME;

    public Proces(int CurrentTime){
        this.CPU_USAGE =  rand.nextInt(6) +1;
        this.TIME_AMOUNT = rand.nextInt(400) ;
        this.FINISH_TIME = CurrentTime + TIME_AMOUNT;
    }

    public int CpuUsage(){
        return CPU_USAGE;
    }

    public int FinishTime(){
        return FINISH_TIME;
    }




}
