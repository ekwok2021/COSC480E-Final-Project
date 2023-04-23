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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class FinalProject extends Application{

    private final int WIDTH = 550;  // arbitrary number: change and update comments
    private final int HEIGHT = 650;
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
    ProgressBar pb;
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
    TextField am;
    Button hide;
    Button addToCart;

    //cart
    Order shoppingCart = new Order();
    VBox cartContainer = new VBox();
    private final TableView<Item> table = new TableView<>();
    private ObservableList<Item> observableCart = 
        FXCollections.observableArrayList();
    Double TOTALAMOUNT = 0.0;
    Double TOTALPRICE = 0.0;

    public void start(Stage stage) {
        items = readIn();
        createTabs();
        createHome();
        showCasing();
        main.add(cartSide, 2,1);
        fillCartContainer();
        Scene scene = new Scene(tabPane, WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("finalProjectStyle.css").toExternalForm());
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
        //ProgressBar
        createPBar();


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
        cart.setContent(cartContainer);
    }

    private void createShop(){
        shop = new VBox();
        shop.getChildren().addAll(createSearch(),createShopGrid());
        shop.setPadding(new Insets(5, 10, 0, 5));
        shop.setSpacing(5);
        
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

    private void createPBar(){
        pb = new ProgressBar(0);
        pb.setPrefWidth(Integer.MAX_VALUE);
        HBox p = new HBox();
        p.getChildren().addAll(pb);
        p.setPadding(new Insets(5, 10, 0, 5));
        main.add(p, 1,2);
    }
    private ScrollPane createShopGrid(){
        scroll = new ScrollPane();
        scroll.setPrefHeight(Integer.MAX_VALUE);
        ColumnConstraints coll1 = new ColumnConstraints();
        coll1.setPercentWidth(50);
        ColumnConstraints coll2 = new ColumnConstraints();
        coll2.setPercentWidth(50);
        shopper.getColumnConstraints().addAll(coll1, coll2);
        shopper.setAlignment(Pos.CENTER);
        
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
            temp.setAlignment(Pos.CENTER);
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
        nLabel.setMinWidth(100);
        name = new TextField();
        name.setEditable(false);
        nBox.getChildren().addAll(nLabel, name);
        nBox.setAlignment(Pos.CENTER_RIGHT);

        HBox caBox = new HBox();
        Label caLabel = new Label("Calories:");
        caLabel.setMinWidth(100);
        cals = new TextField();
        cals.setEditable(false);
        caBox.getChildren().addAll(caLabel, cals);
        caBox.setAlignment(Pos.CENTER_RIGHT);
        
        HBox pBox = new HBox();
        Label pLabel = new Label("Protein(g):");
        pLabel.setMinWidth(100);
        prot = new TextField();
        prot.setEditable(false);
        pBox.getChildren().addAll(pLabel, prot);
        pBox.setAlignment(Pos.CENTER_RIGHT);

        HBox cBox = new HBox();
        Label cLabel = new Label("Carbs(g):");
        cLabel.setMinWidth(100);
        carb = new TextField();
        carb.setEditable(false);
        cBox.getChildren().addAll(cLabel, carb);
        cBox.setAlignment(Pos.CENTER_RIGHT);

        HBox fBox = new HBox();
        Label fLabel = new Label("Fat(g):");
        fLabel.setMinWidth(100);
        fats = new TextField();
        fats.setEditable(false);
        fBox.getChildren().addAll(fLabel, fats);
        fBox.setAlignment(Pos.CENTER_RIGHT);

        HBox mBox = new HBox();
        Label mLabel = new Label("Price($):");
        mLabel.setMinWidth(100);
        money = new TextField();
        money.setEditable(false);
        mBox.getChildren().addAll(mLabel, money);
        mBox.setAlignment(Pos.CENTER_RIGHT);

        HBox catBox = new HBox();
        Label catLabel = new Label("Category:");
        catLabel.setMinWidth(100);
        cat = new TextField();
        cat.setEditable(false);
        catBox.getChildren().addAll(catLabel, cat);
        catBox.setAlignment(Pos.CENTER_RIGHT);

        showCase.getChildren().addAll(nBox,catBox,caBox,pBox,cBox,fBox,mBox);
        showCase.setAlignment(Pos.CENTER_RIGHT);
        showCase.setPadding(new Insets(10, 10, 10, 10));
        showCase.setSpacing(5);
        showCase.setVisible(false);
        cartSide.getChildren().addAll(showCase);

        hide = new Button("Hide");
        hide.setMinWidth(50);
        addToCart = new Button("Add");
        addToCart.setMinWidth(50);
        am = new TextField();
        Label amT = new Label("Amount:");
        amT.setMinWidth(50);
        addtoCBox.getChildren().addAll(hide,amT, am, addToCart);
        addtoCBox.setAlignment(Pos.BOTTOM_RIGHT);
        addtoCBox.setSpacing(2);
        addtoCBox.setPadding(new Insets(10, 10, 10, 10));
        main.add(addtoCBox, 2,2);
        addtoCBox.setVisible(false);
    }

    private void showItem(Item i){
        showCase.setVisible(true);
        addtoCBox.setVisible(true);
        
        name.setText(String.valueOf(i.getName()));
        carb.setText(String.valueOf(i.getCarbs()));
        cals.setText(String.valueOf(i.getCalorie()));
        money.setText(String.valueOf(i.getPrice()));
        cat.setText(String.valueOf(i.getCategory()));
        prot.setText(String.valueOf(i.getProtein()));
        fats.setText(String.valueOf(i.getFat()));
        addToCart.setOnAction(e->{
            Boolean t = false;
            for(int j = 0; j<shoppingCart.getTotal(); j++){
                if(shoppingCart.getItem(j).getName().equals(i.getName())&&!am.getText().equals("")){
                    observableCart.remove(shoppingCart.getItem(j));
                    shoppingCart.getItem(j).setAmount(shoppingCart.getItem(j).getAmount()+Integer.valueOf(am.getText()));
                    observableCart.add(shoppingCart.getItem(j));
                    t = true;
                    break;
                }
            }
            if(t == false&&!am.getText().equals("")){
                shoppingCart.addItem(i);
                observableCart.add(i);
                i.setAmount(Integer.valueOf(am.getText()));
            }
            am.setText("");
            
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


    //cart
    private void fillCartContainer(){
        table.setItems(observableCart);
        table.setEditable(true);
        TableColumn<Item, Integer> amountCol = new TableColumn<Item, Integer>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("amount"));
        amountCol.setPrefWidth(150);
        amountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		amountCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, Integer>>() {
			@Override
			public void handle(CellEditEvent<Item, Integer> event) {
				Item f = event.getRowValue();
                TOTALAMOUNT=TOTALAMOUNT-f.getAmount();
                TOTALPRICE= TOTALPRICE-(f.getPrice()*f.getAmount());
				f.setAmount(event.getNewValue());
                TOTALAMOUNT+=f.getAmount();
                //amountTotal.setText(""+TOTALAMOUNT);
                TOTALPRICE+=(f.getAmount()*f.getPrice());
                //priceTotal.setText("$"+TOTALPRICE);
			}
		});

        TableColumn<Item, String> nameCol = new TableColumn<Item, String>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<Item, Double> priceCol = new TableColumn<Item, Double>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        priceCol.setPrefWidth(150);

        /* 
        TableColumn<Item, Double> calorieCol = new TableColumn<Item, Double>("Cals");
        calorieCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("calories"));
        calorieCol.setPrefWidth(150);
        */

        table.getColumns().addAll(nameCol, amountCol, priceCol);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

        //needs implemntation
        Button checkout = new Button("Checkout");
        checkout.setOnAction(e->{
            tabPane.getSelectionModel().select(checkOut);
        });

        table.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE && table.getSelectionModel().getSelectedItem() != null) {
                shoppingCart.removeItem(table.getSelectionModel().getSelectedItem());
                observableCart.remove(table.getSelectionModel().getSelectedItem());
            }
        });

        HBox checkOutBox = new HBox();
        checkOutBox.setAlignment(Pos.BOTTOM_RIGHT);
        checkOutBox.getChildren().addAll(checkout);

        cartContainer.getChildren().addAll(table, checkOutBox);

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