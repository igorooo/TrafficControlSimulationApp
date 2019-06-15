public class Result {


    public int MIGRATIONS,ASKS,USAGE,L;

    Result(){

        MIGRATIONS = ASKS = USAGE = L = 0;
    }

    public int AvarageUsage(){
        return USAGE / L;
    }



}
