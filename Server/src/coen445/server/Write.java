package coen445.server;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Christopher on 2015-12-12.
 */
public class Write {
    public static void main(String[] args) throws IOException {

        //WRITE
        try{

            Properties writeserver = new Properties();
            writeserver.setProperty("Meeting-1", "Dec 22");
            writeserver.setProperty("Meeting-2", "Dec 27");
            writeserver.setProperty("Meeting-3", "Dec 29");

            //Option 1
        /*    File file = new File("testfile2_1.properties");//Create file if it doesn't exist
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Favorite Things");
            fileOut.close();*/

            //Option 2
             /* FileOutputStream fileOut = new FileOutputStream("testfile2_2.properties");
              properties.store(fileOut, "Favorite Things");
             fileOut.close();
*/
            //Option 3
            writeserver.store(new FileOutputStream("Meeting Schedule.properties"),"Schedules");





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }//end function
}



