package sample.util;

import java.io.*;

public class FicheirosBat {


    public FicheirosBat(){
    }

    public void save(){
        try {
            FileWriter fileWriter1 = new FileWriter("startDB.bat");
            fileWriter1.write("start /MIN C:\\xampp\\mysql_start.bat");

            FileWriter fileWriter2 = new FileWriter("stopDB.bat");
            fileWriter2.write("start /MIN C:\\xampp\\mysql_stop.bat");


            fileWriter1.close();
            fileWriter2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
