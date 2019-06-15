import java.util.ArrayList;
import java.util.Random;

public class CPU implements Runnable {

    Time time;
    Random rand = new Random();
    ArrayList<Proces> process;
    ArrayList<Proces> delete;
    int UPPER_LIMIT, LOWER_LIMIT, ID;
    boolean DO;
    int BUSY_LEVEL;  // <1 : 4>  1 = min busy, 4 - max


    public CPU(int UPPER_LIMIT, int LOWER_LIMIT, int ID, Time time, int BUSY_LEVEL){

        this.time = time;
        this.process = new ArrayList<>();
        this.delete = new ArrayList<>();
        this.UPPER_LIMIT = UPPER_LIMIT;
        this.LOWER_LIMIT = LOWER_LIMIT;
        this.ID = ID;
        this.BUSY_LEVEL = BUSY_LEVEL;
    }

    public synchronized void CheckForEndOfProcess(){

        for(Proces proces: process){


            if( time.WhatTime() > proces.FinishTime() ){
                delete.add(proces);
            }
        }

        for(Proces proces: delete){

            process.remove(proces);
        }

        delete.clear();
    }

    public synchronized void addNewProcess(){

        Proces CURRENT_PROCES;

        if(rand.nextInt(20)  < BUSY_LEVEL){

            if(CurrentUsage() < 95){

                CURRENT_PROCES = new Proces(time.WhatTime());
                process.add(CURRENT_PROCES);
            }

            //  System.out.println("--------->Current Usage: " + CurrentUsage());

        }
    }

    public synchronized int CurrentUsage(){

        int total = 0;

        for(Proces proces: process){
            total += proces.CpuUsage();
        }

        return total;
    }

    public synchronized boolean AboveUpperLimit(){
        return CurrentUsage() > UPPER_LIMIT;
    }

    public synchronized boolean UnderLowerLimit(){
        return CurrentUsage() < LOWER_LIMIT;
    }

    public synchronized void STOP(){
        DO = false;
    }

    @Override
    public void run() {

        DO = true;

        while(DO){

            addNewProcess();

            CheckForEndOfProcess();

            try {
                Thread.sleep( time.TIME_UNIT );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
