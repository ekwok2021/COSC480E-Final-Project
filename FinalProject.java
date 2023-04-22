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
import java.util.HashMap; 

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
    VBox cartSide;
    VBox showCase;
    HBox addtoCBox = new HBox();
    //search
    Order searched = new Order();
    ScrollPane scroll;
    TextField searchBar;
    Button search;
    HashMap<Button, Item> buttons = new HashMap<>();;
    //showcase
    TextField name;
    TextField cals;
    TextField prot;
    TextField carb;
    TextField fats;
    TextField money;
    TextField cat;

    Order shoppingCart = new Order();

    public void start(Stage stage) {
        items = readIn();
        createTabs();
        createHome();
        showCasing();
        main.add(cartSide, 2,1);
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
        col1.setPercentWidth(0);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(40);
        main.getColumnConstraints().addAll(col1, col2, col3);
    }

    private void createCart(){
        cartSide = new VBox();
        Label label = new Label("");
        TitledPane cart = new TitledPane("cart",label);
        VBox cartBox = new VBox();
        cartBox.getChildren().addAll(cart);
        cart.setAlignment(Pos.TOP_RIGHT);
        cart.setExpanded(false);
        cartSide.getChildren().addAll(cart);
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
        //System.out.println(searched.toString());
        buttons.clear();
        int row = 0;
        int col = 0;
        for(int i =0; i<searched.getTotal();i++){
            VBox temp = new VBox();
            Label l = new Label(searched.getItem(i).getName());
            Button show = new Button("Show");
            buttons.put(show, searched.getItem(i));
            show.setOnAction(e -> {
                showItem(buttons.get(show));
            });
            temp.getChildren().addAll(l,show);
            temp.setAlignment(Pos.CENTER);
            shopper.add(temp, col, row);
            shopper.setAlignment(Pos.CENTER);
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

    private void showCasing(){
        showCase = new VBox();
        HBox nBox = new HBox();
        Label nLabel = new Label("Name:");
        name = new TextField();
        name.setEditable(false);
        nBox.getChildren().addAll(nLabel, name);
        nBox.setAlignment(Pos.CENTER_RIGHT);

        HBox caBox = new HBox();
        Label caLabel = new Label("Calories:");
        cals = new TextField();
        cals.setEditable(false);
        caBox.getChildren().addAll(caLabel, cals);
        caBox.setAlignment(Pos.CENTER_RIGHT);
        
        HBox pBox = new HBox();
        Label pLabel = new Label("Protein(g):");
        prot = new TextField();
        prot.setEditable(false);
        pBox.getChildren().addAll(pLabel, prot);
        pBox.setAlignment(Pos.CENTER_RIGHT);

        HBox cBox = new HBox();
        Label cLabel = new Label("Carbs(g):");
        carb = new TextField();
        carb.setEditable(false);
        cBox.getChildren().addAll(cLabel, carb);
        cBox.setAlignment(Pos.CENTER_RIGHT);

        HBox fBox = new HBox();
        Label fLabel = new Label("Fat(g):");
        fats = new TextField();
        fats.setEditable(false);
        fBox.getChildren().addAll(fLabel, fats);
        fBox.setAlignment(Pos.CENTER_RIGHT);

        HBox mBox = new HBox();
        Label mLabel = new Label("Price($):");
        money = new TextField();
        money.setEditable(false);
        mBox.getChildren().addAll(mLabel, money);
        mBox.setAlignment(Pos.CENTER_RIGHT);

        HBox catBox = new HBox();
        Label catLabel = new Label("Category:");
        cat = new TextField();
        cat.setEditable(false);
        catBox.getChildren().addAll(catLabel, cat);
        catBox.setAlignment(Pos.CENTER_RIGHT);

        showCase.getChildren().addAll(nBox,catBox,caBox,pBox,cBox,fBox,mBox);
        showCase.setVisible(false);
        cartSide.getChildren().addAll(showCase);
    }

    private void showItem(Item i){
        showCase.setVisible(true);
        Button hide = new Button("Hide");
        Button addToCart = new Button("Add");
        addToCart.setMinWidth(50);
        TextField am = new TextField();
        Label amT = new Label("Amount:");
        amT.setMinWidth(50);
        addtoCBox.getChildren().addAll(amT, am, addToCart);
        addtoCBox.setAlignment(Pos.BOTTOM_RIGHT);
        main.add(addtoCBox, 2,2);
        name.setText(String.valueOf(i.getName()));
        carb.setText(String.valueOf(i.getCarbs()));
        cals.setText(String.valueOf(i.getCalorie()));
        money.setText(String.valueOf(i.getPrice()));
        cat.setText(String.valueOf(i.getCategory()));
        prot.setText(String.valueOf(i.getProtein()));
        fats.setText(String.valueOf(i.getFat()));
        addToCart.setOnAction(e->{
            shoppingCart.addItem(i);
        });
        hide.setOnAction(e->{
            addtoCBox.setVisible(false);
            showCase.setVisible(false);
        });
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