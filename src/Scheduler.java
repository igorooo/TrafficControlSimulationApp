import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Scheduler implements Runnable {

    int AMOUNT_OF_CPUS;
    int AMOUNT_OF_TASKS_TO_TAKE = 10;
    ArrayList<CPU> cpus;
    ArrayList<Thread> threads;
    Random rand = new Random();
    Result res;

    int UPPER_LIMIT, LOWER_LIMIT, Z_VAR, WHICH_ALGORITHM;
    Time time;

    boolean DO;

    JTextArea log,result,usage;


    public Scheduler(int AMOUNT_OF_CPUS, int UPPER_LIMIT, int LOWER_LIMIT, int Z_VAR, int WHICH_ALGORITHM, JTextArea log, JTextArea result, JTextArea usage){

        this.AMOUNT_OF_CPUS = AMOUNT_OF_CPUS;
        this.time = new Time();
        this.Z_VAR = Z_VAR;
        this.UPPER_LIMIT = UPPER_LIMIT;
        this.LOWER_LIMIT = LOWER_LIMIT;
        this. WHICH_ALGORITHM = WHICH_ALGORITHM;
        this.log = log;
        this.result = result;
        this.usage = usage;
        res = new Result();

        cpus = new ArrayList<>();
        threads = new ArrayList<>();

        this.DO = true;

    }

    public CPU getNextRandomCPU(){

        int r = rand.nextInt(AMOUNT_OF_CPUS);
        return cpus.get(r);
    }

    public void FirstAlgorithm(){


        for(CPU cpu: cpus){

            if( !cpu.AboveUpperLimit() ){
                continue;
            }



            CPU ncpu = getNextRandomCPU();

            int i = 0;

            while( ncpu.AboveUpperLimit() &&  (i < Z_VAR) ){

                res.ASKS++;
                ncpu = getNextRandomCPU();
                while( (cpu.ID == ncpu.ID) ){
                    ncpu = getNextRandomCPU();
                }
               i++;
            }

            if( !ncpu.AboveUpperLimit()){

                Proces proc = cpu.process.get(cpu.process.size()-1);

                res.MIGRATIONS++;
                ncpu.process.add(proc);
                cpu.process.remove(proc);

                System.out.println("Taking from: " + cpu.ID + " to: " + ncpu.ID);
                log.setText( "Taking proc. from: " + cpu.ID + " to: " + ncpu.ID + "\n" +log.getText() );

            }



            for(CPU cpuu: cpus){
                System.out.println("CPU.ID: "+cpuu.ID + " Current USAGE: " + cpuu.CurrentUsage());
            }
        }


    }

    public void SecondAlgorithm() throws InterruptedException {


        for(CPU cpu: cpus){

            if( !cpu.AboveUpperLimit() ){
                continue;
            }

            CPU ncpu = getNextRandomCPU();

            int i = 0;

            while( ncpu.AboveUpperLimit() && i < cpus.size()){

                Thread.sleep(1);
                res.ASKS++;

                ncpu = getNextRandomCPU();
                while( (cpu.ID == ncpu.ID) ){
                    ncpu = getNextRandomCPU();
                }
                i++;
            }

            if( !ncpu.AboveUpperLimit()){

                Proces proc = cpu.process.get(cpu.process.size()-1);

                res.MIGRATIONS++;
                ncpu.process.add(proc);
                cpu.process.remove(proc);

                System.out.println("Taking from: " + cpu.ID + " to: " + ncpu.ID);
                log.setText( "Taking proc. from: " + cpu.ID + " to: " + ncpu.ID + "\n" +log.getText() );

            }

            for(CPU cpuu: cpus){
                System.out.println("CPU.ID: "+cpuu.ID + " Current USAGE: " + cpuu.CurrentUsage());
            }
        }


    }

    public void ThirdAlgorithm(){


        for(CPU cpu: cpus){

            CPU ncpu;
            Proces proc;

            int i;


            if( cpu.UnderLowerLimit() ){


                ncpu = getNextRandomCPU();
                i = 0;

                while( !ncpu.AboveUpperLimit() &&  (i < cpus.size()) ){



                    ncpu = getNextRandomCPU();
                    while( (cpu.ID == ncpu.ID) ){
                        ncpu = getNextRandomCPU();
                    }
                    i++;
                }

                if( ncpu.AboveUpperLimit() ){

                    System.out.println("----------->Taking tasks from busy cpu");
                    for( int j = 0; j < AMOUNT_OF_TASKS_TO_TAKE ; j++ ){

                        proc = ncpu.process.get( ncpu.process.size() -1 );

                        res.MIGRATIONS++;
                        res.ASKS++;
                        cpu.process.add(proc);
                        ncpu.process.remove(proc);

                        System.out.println("Taking from: " + cpu.ID + " to: " + ncpu.ID);
                        log.setText( "Moving proc. from: " + ncpu.ID + " to: " + cpu.ID + "  (cuz of low usage)\n"+log.getText() );



                        for(CPU cpuu: cpus){
                            System.out.println("CPU.ID: "+cpuu.ID + " Current USAGE: " + cpuu.CurrentUsage());
                        }

                    }
                }
            }



            if( !cpu.AboveUpperLimit() ){
                continue;
            }

            ncpu = getNextRandomCPU();

            i = 0;

            while( ncpu.AboveUpperLimit() &&  (i < Z_VAR) ){

                res.ASKS++;
                ncpu = getNextRandomCPU();
                while( (cpu.ID == ncpu.ID) ){
                    ncpu = getNextRandomCPU();
                }
                i++;
            }

            if( !ncpu.AboveUpperLimit()){

                proc = cpu.process.get(cpu.process.size()-1);

                res.MIGRATIONS++;
                ncpu.process.add(proc);
                cpu.process.remove(proc);

                System.out.println("Taking from: " + cpu.ID + " to: " + ncpu.ID);
                log.setText( "Taking proc. from: " + cpu.ID + " to: " + ncpu.ID + "\n" +log.getText() );

            }

            for(CPU cpuu: cpus){
                System.out.println("CPU.ID: "+cpuu.ID + " Current USAGE: " + cpuu.CurrentUsage());
            }
        }


    }



    public void STOP(){
        DO = false;
    }

    @Override
    public void run() {

        for(int i = 0; i < AMOUNT_OF_CPUS; i++){

            CPU cpu = new CPU(UPPER_LIMIT,LOWER_LIMIT,i, time,rand.nextInt(4));

            cpus.add( cpu );

            threads.add( new Thread(cpu) );

        }

        Thread TIME_THREAD = new Thread(time);

        TIME_THREAD.start();

        for(Thread thread: threads){

            thread.start();
        }


        while(DO){

            if(WHICH_ALGORITHM == 1){

                FirstAlgorithm();
            }

            if(WHICH_ALGORITHM == 2){

                try {
                    SecondAlgorithm();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(WHICH_ALGORITHM == 3){

                ThirdAlgorithm();
            }

            usage.setText("");

            for(CPU cpuu: cpus){
                int cu = cpuu.CurrentUsage();
                usage.setText(usage.getText()+ "CPU.ID: "+cpuu.ID + " Load lvl: " + cpuu.BUSY_LEVEL + " Current USAGE: " + cu + "%\n");
                res.USAGE += cu;
                res.L ++;
            }


            try {
                Thread.sleep(time.SCH_TIME_UNIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        result.setText("Amount of inquiryies: " + res.ASKS + "\nAmount of migrations: "+ res.MIGRATIONS + "\nAvarage usage: " + res.AvarageUsage());

        for(CPU cpu: cpus){
            cpu.STOP();
        }

        time.STOP();

    }
}
