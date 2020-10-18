package sample.dataBase;

import java.io.IOException;

public class DataBaseServer {

    public DataBaseServer(){

    }

    public void start(){
        try {
            Runtime.getRuntime().exec("cmd /c start /MIN C:\\xampp\\mysql_start.bat").waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    public void stop(){
        try {
            Runtime.getRuntime().exec("cmd /c start /MIN C:\\xampp\\mysql_stop.bat").waitFor();
        } catch (InterruptedException | IOException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
