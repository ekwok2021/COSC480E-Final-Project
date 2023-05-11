
import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.time.format.DateTimeFormatter;

import javafx.scene.paint.Color;


class CalendarFileHandler {
    
    public static void saveRecords(HashMap<LocalDate,Order> listIn) {
        
        try {
            FileOutputStream mapFile = new FileOutputStream("calendar.dat");
            DataOutputStream mapWriter = new DataOutputStream(mapFile);
            mapWriter.writeInt(listIn.size());
            Set<LocalDate> ad = listIn.keySet();
            for (LocalDate d: ad) {
                //LocalDate d = ad.get(i);
                String ds = d.toString();
                Order o = listIn.get(d);
                mapWriter.writeUTF(ds);
                mapWriter.writeInt(o.getTotal());
                for(int j = 0; j<o.getTotal(); j++){
                    if(o.getItem(j)!=null){
                        mapWriter.writeUTF(o.getItem(j).getName());
                        mapWriter.writeInt(o.getItem(j).getAmount());
                        mapWriter.writeDouble(o.getItem(j).getPrice());
                        mapWriter.writeDouble(o.getItem(j).getCalorie());
                        mapWriter.writeInt(o.getItem(j).getProtein());
                        mapWriter.writeInt(o.getItem(j).getFat());
                        mapWriter.writeInt(o.getItem(j).getCarbs());
                        mapWriter.writeUTF(o.getItem(j).getCategory());
                        mapWriter.writeUTF(o.getItem(j).getImage());
                    }
                }
            }
            mapWriter.flush();
            mapWriter.close();
        }
        catch(IOException ioe) {
            System.out.println("Error writing file");
        }
        
    }
    
    public static void readRecords(HashMap<LocalDate, Order> listIn) {
        
        try {
            FileInputStream mapFile = new FileInputStream("calendar.dat");
            DataInputStream mapReader = new DataInputStream(mapFile);
    
            int tot = 0;
            
            tot = mapReader.readInt();
            for (int j = 0; j<tot; j++) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String s = mapReader.readUTF();
                LocalDate tempDate = LocalDate.parse(s, formatter);
                int oSize = mapReader.readInt();
                Order tempO = new Order();
                for(int i = 0; i<oSize; i++){
                    String tempName = mapReader.readUTF();
                    int tempAmount = mapReader.readInt();
                    Double tempPrice = mapReader.readDouble();
                    Double tempCalorie = mapReader.readDouble();
                    int tempProtein = mapReader.readInt();
                    int tempFat = mapReader.readInt();
                    int tempCarb = mapReader.readInt();
                    String tempCategory = mapReader.readUTF();
                    String tempImage = mapReader.readUTF();
                    Item tempItem = new Item(tempName, tempAmount, tempPrice, tempCalorie, tempProtein, tempFat, tempCarb, tempCategory, tempImage);
                    tempO.addItem(tempItem);
                }
                listIn.put(tempDate, tempO);
                
            }
            mapReader.close();
        }
        
        catch(IOException ioe) {
            System.out.println("No records found");
        }
        
    }
}
