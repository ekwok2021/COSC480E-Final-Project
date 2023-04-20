import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.KeyCode;
import java.text.NumberFormat;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition; 
import javafx.scene.shape.Circle; 
import javafx.scene.shape.Rectangle;
import javafx.util.Duration; 
import javafx.scene.Group; 
import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.VPos;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import java.io.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;


public class FinalProject extends Application{

    private final int WIDTH = 550;  // arbitrary number: change and update comments
    private final int HEIGHT = 400;
    TabPane tabPane;
    Tab home;
    Tab checkOut;
    Tab goalSetter;
    Tab calendar;
    public void start(Stage stage) {
        createTabs();
        creatHome();
        
        Scene scene = new Scene(tabPane, WIDTH, HEIGHT);
        //scene.getStylesheets().add(getClass().getResource("Donovan_project1style2.css").toExternalForm());
        //fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(50));
        stage.setMinHeight(HEIGHT);
        stage.setMinWidth(WIDTH);

        stage.setScene(scene);
        stage.setTitle("InstaCart Ripoff");
        stage.show(); 
    }
    private void createTabs(){
        tabPane = new TabPane();
        
        home = new Tab("Home");
        home.setClosable(false);
        checkOut = new Tab("Checkout");
        checkOut.setClosable(false);
        goalSetter = new Tab("Goal Setter");
        goalSetter.setClosable(false);
        calendar = new Tab("Calendar");
        calendar.setClosable(false);
        tabPane.getTabs().addAll(home, checkOut, goalSetter, calendar);
    }
    private void creatHome(){
        VBox main = new VBox();
        /* 
        Label label = new Label("");
        TitledPane cart = new TitledPane("cart",label);
        cart.setExpanded(false);
        cart.setPrefWidth(30);
        main.getChildren().addAll(cart);
        cart.setAlignment(Pos.TOP_RIGHT);
        home.setContent(main);
        */

        GridPane shopper = new GridPane();
        
    }

    private Order readIn(){
        Order order = new Order();
        ArrayList<Item> items = new ArrayList<>();

        String csvFile = "FoodD.csv";


        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line; 
            reader.readLine();

            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                String name = values[0];
                double price = Double.parseDouble(values[1]);
                double calories = Double.parseDouble(values[2]);
                int protein = Integer.parseInt(values[3]);
                int fat = Integer.parseInt(values[4]);
                int carbs = Integer.parseInt(values[5]);
                String category = values[6];
                Item item = new Item(name, 0, price, calories, protein, fat, carbs, category);
                items.add(item);
                order.addItem(item);
    
            }
        reader.close();
        }
        return order;



        


    }

}
