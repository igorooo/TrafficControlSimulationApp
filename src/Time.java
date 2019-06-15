public class Time implements Runnable{

    int time;
    public static int TIME_UNIT = 200;
    public static int SCH_TIME_UNIT = 200;
    boolean DO;

    public Time(){
        time = 0;
        DO = true;
    }

    public void TimeRun(){
        time ++;
    }

    public synchronized int WhatTime(){
        return time;
    }

    public void STOP(){
        DO = false;
    }

    @Override
    public void run() {


        while(DO){

            TimeRun();


            try {
                Thread.sleep(TIME_UNIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
