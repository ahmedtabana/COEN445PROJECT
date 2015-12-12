package coen445.client;
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

            Properties properties = new Properties();
            properties.setProperty("Meeting#1", "Jan 22");
            properties.setProperty("Meeting#2", "Jan 27");
            properties.setProperty("Meeting#3", "Jan 29");

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
            properties.store(new FileOutputStream("Meeting Schedule"),"Schedules");





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }//end function
}



