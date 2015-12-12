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
public class Read {

    public static void main(String[] args) throws IOException {
        //READ ONLY
        try{
            Properties pro = new Properties();
            FileInputStream in = new FileInputStream("Meeting Schedule");
            pro.load(in);

            //Option 1(Several names)
            for(int i=1;i<4;i++) {
                System.out.println(pro.getProperty("Meeting#"+i));
            }

                /*
                //Option 2
                String message = pro.getProperty("Meeting#1");
                System.out.println(message);
                */
        }
        catch (IOException e)
        {
            System.out.println("Error is" + e.getMessage());
            e.printStackTrace();
        }




    }//end function
}


