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
import javafx.scene.control.ScrollPane;
import javafx.collections.transformation.FilteredList;

public class FinalProject extends Application{

    private final int WIDTH = 550;  // arbitrary number: change and update comments
    private final int HEIGHT = 400;
    TabPane tabPane;
    Tab home;
    Tab checkOut;
    Tab goalSetter;
    Tab calendar;
    Order items;

    //home
    GridPane main;
    GridPane shopper = new GridPane();
    VBox shop;
    //search
    Order searched = new Order();
    ScrollPane scroll;
    TextField searchBar;
    Button search;

    public void start(Stage stage) {
        items = readIn();
        createTabs();
        createHome();
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
    private void createHome(){
        //Create Main Pane
        createHomeGrid();
        //Create Cart
        createCart();
        //shopping interface
        createShop();


        home.setContent(main);
    }

    private void createHomeGrid(){
        main = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(10);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        main.getColumnConstraints().addAll(col1, col2, col3);
    }

    private void createCart(){
        Label label = new Label("");
        TitledPane cart = new TitledPane("cart",label);
        VBox cartBox = new VBox();
        cartBox.getChildren().addAll(cart);
        cart.setAlignment(Pos.TOP_RIGHT);
        cart.setExpanded(false);
        main.add(cartBox,2,1);
    }

    private void createShop(){
        shop = new VBox();
        shop.getChildren().addAll(createSearch(),createShopGrid());
        
        main.add(shop, 1,1);
    }

    private HBox createSearch(){
        searchBar = new TextField();
        searchBar.setPrefWidth(Integer.MAX_VALUE);
        search = new Button("Search");
        search.setMinWidth(80);
        HBox searchBox = new HBox();
        searchBox.getChildren().addAll(searchBar, search);
        search.setOnAction(e -> {
            System.out.println("Searched");
            shopper.getChildren().clear();
            filterList(searchBar.getText());
            createShopGrid();
        });
        return searchBox;
    }

    private ScrollPane createShopGrid(){
        scroll = new ScrollPane();
        scroll.setPrefHeight(Integer.MAX_VALUE);
        ColumnConstraints coll1 = new ColumnConstraints();
        coll1.setPercentWidth(50);
        ColumnConstraints coll2 = new ColumnConstraints();
        coll2.setPercentWidth(50);
        shopper.getColumnConstraints().addAll(coll1, coll2);

        populateShopGrid();

        return scroll;
    }

    private void populateShopGrid(){
        System.out.println(searched.toString());
        int row = 0;
        int col = 0;
        for(int i =0; i<searched.getTotal();i++){
            VBox temp = new VBox();
            Label l = new Label(searched.getItem(i).getName());
            temp.getChildren().addAll(l);
            temp.setAlignment(Pos.CENTER);
            shopper.add(temp, col, row);
            if(i!=0&&i%2!=0){
                row++;
            }
            if(i%2==0){
                col = 1;
            }
            else{
                col = 0;
            }
        }
        scroll.setContent(shopper);
    }

    //search functions
    private boolean searchFindsOrder(Item item, String searchText){
        return (item.getName().toLowerCase().contains(searchText.toLowerCase())) ||
                (item.getCategory().toLowerCase().contains(searchText.toLowerCase()));
    }

    private void filterList(String searchText){
        Order filteredList = new Order();
        for (int i = 0; i < items.getTotal(); i++){
            if(searchFindsOrder(items.getItem(i), searchText)) filteredList.addItem(items.getItem(i));
        }
        searched = filteredList;
    }






    private Order readIn(){
        Order order = new Order();
        ArrayList<Item> itemsArray = new ArrayList<>();

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
                itemsArray.add(item);
                //System.out.println(item.toString());
                order.addItem(item);
                searched.addItem(item);
    
            }
        reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order;
    }
}