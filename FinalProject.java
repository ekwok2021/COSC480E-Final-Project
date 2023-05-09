import javafx.application.Application;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
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

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class FinalProject extends Application{

    private final int WIDTH = 650;  // arbitrary number: change and update comments
    private final int HEIGHT = 700;
    TabPane tabPane;
    Tab home;
    Tab checkOut;
    Tab goalSetter;
    Tab calendar;
    Tab reviews;
    Order items;

    //home
    GridPane main;
    GridPane shopper = new GridPane();
    VBox shop;
    VBox cartSide;
    VBox showCase;
    HBox addtoCBox = new HBox();
    ProgressBar pb;
    private static final String IMAGE_CLASS = "image" ;
    private static final String SHOPPER_STYLE = "shopper" ;
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
    HashMap<String, Image> imageMap = new HashMap<>();
    ImageView showImage;

    //Goals
    Integer fatGoal = 78;
    Integer carbGoal = 364;
    Integer proteinGoal = 166;
    ProgressBar proteinPBar;
    ProgressBar carbPBar;
    ProgressBar fatPBar;
    ProgressBar proteinPBarCheckout;
    ProgressBar carbPBarCheckout;
    ProgressBar fatPBarCheckout;

    HashMap <LocalDate, Order> orderDates= new HashMap<>();

    Double currentFat = 0.0;
    Double currentCarb = 0.0;
    Double currentProtein = 0.0;

    //cart
    Order shoppingCart = new Order();
    VBox cartContainer = new VBox();
    private final TableView<Item> table = new TableView<>();
    private ObservableList<Item> observableCart = 
        FXCollections.observableArrayList();
    Double TOTALAMOUNT = 0.0;
    Double TOTALPRICE = 0.0;

    //checkout
    TableView<Item> checkoutTable= new TableView<>();
    private ObservableList<Item> observableCartCopy = 
        FXCollections.observableArrayList();

    //Calendar
    ObservableList<Item> Cart = FXCollections.observableArrayList();
    TableView<Item> ctable = new TableView<>();

    public void start(Stage stage) {
        items = readIn();
        createTabs();
        createHome();
        showCasing();
        main.add(cartSide, 2,1);
        fillCartContainer();
        checkoutTable();
        createCalendarTab();
        reviewTab();
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
        reviews = new Tab("Reviews");
        reviews.setClosable(false);
        tabPane.getTabs().addAll(home, checkOut, goalSetter, calendar, reviews);
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

        createPBarCheckout();

        setGoals();

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
        cartSide.setPadding(new Insets(5, 5, 0, 0));
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

    private ScrollPane createShopGrid(){
        scroll = new ScrollPane();
        scroll.setPrefHeight(Integer.MAX_VALUE);
        //scroll.setFitToHeight(true);
        //scroll.setFitToWidth(true);
        ColumnConstraints coll1 = new ColumnConstraints();
        coll1.setPercentWidth(50);
        ColumnConstraints coll2 = new ColumnConstraints();
        coll2.setPercentWidth(50);
        shopper.getColumnConstraints().addAll(coll1, coll2);
        shopper.setAlignment(Pos.CENTER);
        shopper.getStyleClass().add(SHOPPER_STYLE);

        //shopper.setPrefWidth(Integer.MAX_VALUE);
       // shopper.setPrefWidth(Integer.MAX_VALUE);
        
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
            Image image;
            try ( FileInputStream inputstream = new FileInputStream(searched.getItem(i).getImage()); ) {
                image = new Image(inputstream);
            } // fileIn is closed
            catch (IOException e) {
                image = new Image("Ceasar_Salad.png");
            }
            //FileInputStream inputstream = new FileInputStream(searched.getItem(i).getImage()); 
            imageMap.put(searched.getItem(i).getName(),image);
            ImageView imageView = new ImageView(image); 
            imageView.setFitHeight(50); 
            imageView.setFitWidth(50); 
            imageView.setPreserveRatio(true);  
            
            Label l = new Label(searched.getItem(i).getName());
            VBox graphic  = new VBox();
            graphic.setAlignment(Pos.CENTER);
            graphic.getChildren().addAll(imageView, l);
            Button show = new Button();
            show.getStyleClass().add(IMAGE_CLASS);
            show.setGraphic(graphic);
            buttons.put(show, searched.getItem(i));
            show.setOnAction(e -> {
                showItem(buttons.get(show));
            });
            temp.getChildren().addAll(show);
            temp.setAlignment(Pos.CENTER);
            temp.setSpacing(5);
            temp.setPadding(new Insets(5, 5, 5, 5));
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
        
        Image im = new Image("Grilled_Chicken_Sandwich.png");
        showImage = new ImageView(im);
        showCase.getChildren().addAll(nBox,catBox,caBox,pBox,cBox,fBox,mBox,showImage);
        showCase.setAlignment(Pos.CENTER_RIGHT);
        showCase.setPadding(new Insets(5, 5, 5, 5));
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
        showCase.getChildren().remove(showImage);
        name.setText(String.valueOf(i.getName()));
        carb.setText(String.valueOf(i.getCarbs()));
        cals.setText(String.valueOf(i.getCalorie()));
        money.setText(String.valueOf(i.getPrice()));
        cat.setText(String.valueOf(i.getCategory()));
        prot.setText(String.valueOf(i.getProtein()));
        fats.setText(String.valueOf(i.getFat()));
        showImage = new ImageView(imageMap.get(i.getName()));
        showImage.setFitHeight(150); 
        showImage.setFitWidth(150); 
        showImage.setPreserveRatio(true);  
        showCase.getChildren().addAll(showImage);
        addToCart.setOnAction(e->{
            if(!am.getText().equals("")){
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
                //observableCartCopy.add(i);
                i.setAmount(Integer.valueOf(am.getText()));
            }
            animate(addToCart);
           
            currentCarb += i.getCarbs()*Integer.valueOf(am.getText());
            currentFat += i.getFat()*Integer.valueOf(am.getText());
            currentProtein += i.getProtein()*Integer.valueOf(am.getText());
            updateGoalProgressBars();
            updateGoalProgressBarsCheckout();

            am.setText("");
        }
            
        });
        hide.setOnAction(e->{
            showCase.getChildren().remove(showImage);
            addtoCBox.setVisible(false);
            showCase.setVisible(false);
        });
    }
    //https://genuinecoder.com/javafx-animation-tutorial/
    private void animate(Button btn){
        
        Duration duration = Duration.millis(2500);
        //Create new rotate transition
        ScaleTransition scaleTransition = new ScaleTransition(duration, btn);
        ScaleTransition scaleTransition2 = new ScaleTransition(duration, btn);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), btn);
        //Rotate by 200 degree
        //Set how much X should enlarge
        scaleTransition.setToX(4);
        //Set how much Y should
        scaleTransition.setToY(4);
       
        rotateTransition.setByAngle(360);
        scaleTransition.play();
        rotateTransition.play();
         //Set how much X should enlarge
         scaleTransition2.setToX(1);
         //Set how much Y should
         scaleTransition2.setToY(1);
        scaleTransition2.play();
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
                currentCarb = f.getCarbs()*Double.valueOf(f.getAmount());
                currentFat = f.getFat()*Double.valueOf(f.getAmount());
                currentProtein = f.getProtein()*Double.valueOf(f.getAmount());
                updateGoalProgressBars();
                updateGoalProgressBarsCheckout();
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

        
        Button checkout = new Button("Checkout");
        checkout.setOnAction(e->{
            tabPane.getSelectionModel().select(checkOut);
        });

        table.setOnKeyPressed(e -> {
            Item it = table.getSelectionModel().getSelectedItem();
            if (e.getCode() == KeyCode.BACK_SPACE && it != null) {
                shoppingCart.removeItem(it);
                observableCart.remove(it);
                currentCarb -= it.getCarbs()*it.getAmount();
                currentFat -= it.getFat()*it.getAmount();
                currentProtein -= it.getProtein()*it.getAmount();
                updateGoalProgressBars();
                updateGoalProgressBarsCheckout();
            }
        });

        HBox checkOutBox = new HBox();
        checkOutBox.setAlignment(Pos.BOTTOM_RIGHT);
        checkOutBox.getChildren().addAll(checkout);

        cartContainer.getChildren().addAll(table, checkOutBox);

    }

    private void setGoals(){
        VBox goalBox = new VBox();

        TextField carbGoalField = new TextField(String.valueOf(carbGoal));
        Button carbGoalButton = new Button("Set Carb Goal"); 
        carbGoalButton.setOnAction(evt -> {
            carbGoal = Integer.valueOf(carbGoalField.getText());
            updateGoalProgressBars();
            updateGoalProgressBarsCheckout();
        });
        HBox carbGoalBox = new HBox();
        carbGoalBox.getChildren().addAll(carbGoalField, carbGoalButton);

        TextField proteinGoalField = new TextField(String.valueOf(proteinGoal));
        Button proteinGoalButton = new Button("Set Protein Goal");
        proteinGoalButton.setOnAction(evt -> {
            proteinGoal = Integer.valueOf(proteinGoalField.getText());
            updateGoalProgressBars();
            updateGoalProgressBarsCheckout();
        });
        HBox proteinGoalBox = new HBox();
        proteinGoalBox.getChildren().addAll(proteinGoalField, proteinGoalButton);

        TextField fatGoalField = new TextField(String.valueOf(fatGoal));
        Button fatGoalButton = new Button("Set Fat Goal");
        fatGoalButton.setOnAction(evt -> {
            fatGoal = Integer.valueOf(fatGoalField.getText());
            updateGoalProgressBars();
            updateGoalProgressBarsCheckout();
        });
        HBox fatGoalBox = new HBox();
        fatGoalBox.getChildren().addAll(fatGoalField, fatGoalButton);

        goalBox.getChildren().addAll(carbGoalBox, proteinGoalBox, fatGoalBox);
        goalSetter.setContent(goalBox);
    }

    private void checkoutTable(){
        VBox checkoutTableBox = new VBox();
        observableCartCopy = observableCart;
        checkoutTable.setItems(observableCartCopy);
        checkoutTable.setEditable(true);
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
                currentCarb = f.getCarbs()*Double.valueOf(f.getAmount());
                currentFat = f.getFat()*Double.valueOf(f.getAmount());
                currentProtein = f.getProtein()*Double.valueOf(f.getAmount());
                updateGoalProgressBars();
                updateGoalProgressBarsCheckout();
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

        checkoutTable.getColumns().addAll(nameCol, amountCol, priceCol);

        checkoutTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        checkoutTable.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        checkoutTable.setOnKeyPressed(e -> {
            Item it = checkoutTable.getSelectionModel().getSelectedItem();
            if (e.getCode() == KeyCode.BACK_SPACE && it != null) {
                shoppingCart.removeItem(it);
                observableCart.remove(it);
                currentCarb -= it.getCarbs()*it.getAmount();
                currentFat -= it.getFat()*it.getAmount();
                currentProtein -= it.getProtein()*it.getAmount();
                updateGoalProgressBars();
                updateGoalProgressBarsCheckout();
            }
        });
        
        checkoutTableBox.getChildren().addAll(checkoutTable, createPBarCheckout(), createDatePicker());
        checkoutTableBox.setAlignment(Pos.CENTER);
        checkoutTableBox.setSpacing(5);
        checkoutTableBox.setPadding(new Insets(5, 5, 5, 5));
        checkoutTableBox.setPrefWidth(Integer.MAX_VALUE);

        checkOut.setContent(checkoutTableBox);
    }

    private VBox createPBarCheckout(){
        int progressBarWidth = 380;

        HBox proteinBox = new HBox();
        proteinBox.setPadding(new Insets(5, 5, 5, 5));
        proteinBox.setPrefWidth(Integer.MAX_VALUE);
        Label proteinLabel = new Label("Protein: ");
        proteinLabel.setMinWidth(50);
        proteinPBarCheckout = new ProgressBar();
        proteinPBarCheckout.setStyle("-fx-accent: #F66B0E;");
        proteinPBarCheckout.setProgress(currentProtein / proteinGoal);
        proteinPBarCheckout.setPrefWidth(progressBarWidth);
        proteinBox.getChildren().addAll(proteinLabel, proteinPBarCheckout);
        proteinPBarCheckout.setPrefWidth(Integer.MAX_VALUE);

        HBox carbBox = new HBox();
        carbBox.setPadding(new Insets(5, 5, 5, 5));
        carbBox.setPrefWidth(Integer.MAX_VALUE);
        Label carbLabel = new Label("Carbs: ");
        carbLabel.setMinWidth(50);
        carbPBarCheckout = new ProgressBar();
        carbPBarCheckout.setStyle("-fx-accent: #205375;");
        carbPBarCheckout.setProgress(currentCarb / carbGoal);
        carbPBarCheckout.setPrefWidth(progressBarWidth);
        carbBox.getChildren().addAll(carbLabel, carbPBarCheckout);
        carbPBarCheckout.setPrefWidth(Integer.MAX_VALUE);

        HBox fatBox = new HBox();
        fatBox.setPadding(new Insets(5, 5, 5, 5));
        fatBox.setPrefWidth(Integer.MAX_VALUE);
        Label fatLabel = new Label("Fat: ");
        fatLabel.setMinWidth(50);
        fatPBarCheckout = new ProgressBar();
        fatPBarCheckout.setStyle("-fx-accent: #112B3C;");
        fatPBarCheckout.setProgress(currentFat / fatGoal);
        fatPBarCheckout.setPrefWidth(progressBarWidth);
        fatBox.getChildren().addAll(fatLabel, fatPBarCheckout);
        fatPBarCheckout.setPrefWidth(Integer.MAX_VALUE);

        VBox goalProgressBars = new VBox(10);
        //goalProgressBars.setPadding(new Insets(5, 5, 5, 5));
        goalProgressBars.setSpacing(5);
        goalProgressBars.setAlignment(Pos.CENTER);
        goalProgressBars.getChildren().addAll(proteinBox, carbBox, fatBox);
        goalProgressBars.setAlignment(Pos.BOTTOM_LEFT);
        goalProgressBars.setPrefWidth(Integer.MAX_VALUE);
        
        
        return goalProgressBars;
    }
    private void updateGoalProgressBarsCheckout() {
        proteinPBarCheckout.setProgress(Double.valueOf(currentProtein) / Double.valueOf(proteinGoal));
        
        //System.out.println("protein: " + currentProtein);
        carbPBarCheckout.setProgress(Double.valueOf(currentCarb) / Double.valueOf(carbGoal));
        
        //System.out.println("carb: " + currentCarb);
        fatPBarCheckout.setProgress(Double.valueOf(currentFat) / Double.valueOf(fatGoal));
        
        //System.out.println("fat: " + currentFat);
    }

    private HBox createDatePicker(){
        DatePicker datePicker = new DatePicker();
        Button checkout = new Button("Checkout");
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("MM dd, YYYY");

        checkout.setOnAction(evt -> {
            animate(checkout);
            LocalDate date = datePicker.getValue();
            System.out.println(date);
            System.out.println(shoppingCart);
            Order temp = new Order();
            for(int i = 0; i<shoppingCart.getTotal();i++){
                temp.addItem(shoppingCart.getItem(i));
                observableCartCopy.remove(shoppingCart.getItem(i));
                observableCart.remove(shoppingCart.getItem(i));
            }
            orderDates.put(date, temp);
            System.out.println(orderDates);
            /* 
            for(int j = 0; j<shoppingCart.getTotal(); j++){
                observableCartCopy.remove(shoppingCart.getItem(j));
                observableCart.remove(shoppingCart.getItem(j));
            }
            */
            shoppingCart.clearOrder();
            currentCarb = 0.0;
            currentFat = 0.0;
            currentProtein=0.0;
            updateGoalProgressBars();
            updateGoalProgressBarsCheckout();
        });
        HBox datePickerBox = new HBox();
        datePickerBox.setAlignment(Pos.CENTER);
        datePickerBox.setSpacing(5);
        datePickerBox.setPadding(new Insets(5, 5, 5, 5));
        datePickerBox.getChildren().addAll(datePicker, checkout);
        
        return datePickerBox;
    }


    private void createPBar(){
        int progressBarWidth = 380;

        HBox proteinBox = new HBox();
        proteinBox.setPadding(new Insets(5, 5, 5, 5));
        Label proteinLabel = new Label("Protein: ");
        proteinLabel.setMinWidth(50);
        proteinPBar = new ProgressBar();
        proteinPBar.setStyle("-fx-accent: #F66B0E;");
        proteinPBar.setProgress(currentProtein / proteinGoal);
        proteinPBar.setPrefWidth(progressBarWidth);
        proteinBox.getChildren().addAll(proteinLabel, proteinPBar);

        HBox carbBox = new HBox();
        carbBox.setPadding(new Insets(5, 5, 5, 5));
        Label carbLabel = new Label("Carbs: ");
        carbLabel.setMinWidth(50);
        carbPBar = new ProgressBar();
        carbPBar.setStyle("-fx-accent: #205375;");
        carbPBar.setProgress(currentCarb / carbGoal);
        carbPBar.setPrefWidth(progressBarWidth);
        carbBox.getChildren().addAll(carbLabel, carbPBar);

        HBox fatBox = new HBox();
        fatBox.setPadding(new Insets(5, 5, 5, 5));
        Label fatLabel = new Label("Fat: ");
        fatLabel.setMinWidth(50);
        fatPBar = new ProgressBar();
        fatPBar.setStyle("-fx-accent: #112B3C;");
        fatPBar.setProgress(currentFat / fatGoal);
        fatPBar.setPrefWidth(progressBarWidth);
        fatBox.getChildren().addAll(fatLabel, fatPBar);

        VBox goalProgressBars = new VBox(10);
        goalProgressBars.setPadding(new Insets(5, 5, 5, 5));
        goalProgressBars.getChildren().addAll(proteinBox, carbBox, fatBox);
        goalProgressBars.setAlignment(Pos.BOTTOM_LEFT);
        main.add(goalProgressBars, 1, 2);
    }

    private void updateGoalProgressBars() {
        proteinPBar.setProgress(Double.valueOf(currentProtein) / Double.valueOf(proteinGoal));
       // System.out.println("protein: " + currentProtein);
        carbPBar.setProgress(Double.valueOf(currentCarb) / Double.valueOf(carbGoal));
        //System.out.println("carb: " + currentCarb);
        fatPBar.setProgress(Double.valueOf(currentFat) / Double.valueOf(fatGoal));
       // System.out.println("fat: " + currentFat);
    }

    private void createCalendarTab(){

       
        ctable.setItems(Cart);
        TableColumn<Item, Integer> amountCol = new TableColumn<Item, Integer>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("amount"));
        amountCol.setPrefWidth(150);

        TableColumn<Item, String> nameCol = new TableColumn<Item, String>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<Item, Double> priceCol = new TableColumn<Item, Double>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        priceCol.setPrefWidth(150);

        ctable.getColumns().addAll(nameCol, amountCol, priceCol);

        ctable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ctable.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

        DatePicker dayPicker = new DatePicker();
        Button seeDate = new Button("See Order");
        seeDate.setOnAction(e->{
            Cart.clear();
            LocalDate date = dayPicker.getValue();
            popCart(date);
        });
        Button dateCheckout = new Button("Checkout");
        dateCheckout.setOnAction(e->{
            animate(dateCheckout);
            shoppingCart.clearOrder();
            observableCart.clear();
            for(Item i: Cart){
                shoppingCart.addItem(i);
                observableCart.add(i);
            }
            Cart.clear();
            tabPane.getSelectionModel().select(checkOut);
        });
        HBox dateSeer = new HBox();
        dateSeer.setSpacing(5);
        dateSeer.setAlignment(Pos.CENTER);
        dateSeer.setPadding(new Insets(5, 5, 5, 5));
        dateSeer.getChildren().addAll(dayPicker, seeDate, dateCheckout);
        VBox calendarTab = new VBox();
        calendarTab.setSpacing(5);
        calendarTab.setAlignment(Pos.CENTER);
        calendarTab.setPadding(new Insets(5, 5, 5, 5));
        calendarTab.getChildren().addAll(dateSeer, ctable);
        calendar.setContent(calendarTab);
    }
    private void popCart(LocalDate d){
        for(LocalDate od:orderDates.keySet()){
            if(d.compareTo(od)==0){
                Order o = orderDates.get(od);
                for(int i =0; i<o.getTotal();i++){
                    Cart.add(o.getItem(i));
                }
            }
        } 
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
                String image = values[7];
                Item item = new Item(name, 0, price, calories, protein, fat, carbs, category, image);
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

    private void reviewTab(){
        VBox reviewBox = new VBox();

            TextField reviewZero = new TextField("Best app ever-Jack");
            reviewZero.setPrefWidth(Integer.MAX_VALUE);
            TextField review = new TextField("Even better than real instacart!-Ethan");
            review.setPrefWidth(Integer.MAX_VALUE);
            TextField reviewOne = new TextField("One Star, never got my food...");
            reviewOne.setPrefWidth(Integer.MAX_VALUE);
            TextField reviewTwo = new TextField("Unbelievable, they stole my credit card info. I wish I could give 0 stars!");
            reviewTwo.setPrefWidth(Integer.MAX_VALUE);
            TextField reviewThree = new TextField("I got the wrong food, and the delivery driver was very rude. ONE STAR!");
            reviewThree.setPrefWidth(Integer.MAX_VALUE);
            TextField reviewFour = new TextField("Pretty sure this is just a pyramid scheme...how can I join?");
            reviewFour.setPrefWidth(Integer.MAX_VALUE);

            HBox r = new HBox();
            r.getChildren().addAll(getImage(0), review);
            HBox r0 = new HBox();
            r0.getChildren().addAll(getImage(0), reviewZero);
            HBox r1 = new HBox();
            r1.getChildren().addAll(getImage(1), reviewOne);
            HBox r2 = new HBox();
            r2.getChildren().addAll(getImage(1), reviewTwo);
            HBox r3 = new HBox();
            r3.getChildren().addAll(getImage(1), reviewThree);
            HBox r4 = new HBox();
            r4.getChildren().addAll(getImage(0), reviewFour);

            HBox r5 = new HBox();
            r5.getChildren().addAll(getImage(4));
            r5.setSpacing(20);
            r5.setAlignment(Pos.CENTER);
            reviewBox.setPadding(new Insets(10, 10, 10, 10));
            
            reviewBox.getChildren().addAll(r,r0,r4,r1,r2,r3,r5);
            reviewBox.setSpacing(5);
            reviewBox.setPadding(new Insets(5, 5, 5, 5));
            reviewBox.setPrefHeight(Integer.MAX_VALUE);
            reviews.setContent(reviewBox);
    }
    private ImageView getImage(Integer num){
        Image image;
        if(num == 1){
            try ( FileInputStream inputstream = new FileInputStream("oneStar.png")) {
                image = new Image(inputstream);
            } // fileIn is closed
            catch (IOException e) {
                image = new Image("Ceasar_Salad.png");
            }
            ImageView imageView = new ImageView(image); 
            imageView.setFitHeight(50); 
            imageView.setFitWidth(50); 
            imageView.setPreserveRatio(true);  
            return imageView;
        }
        else if(num==0){
            try ( FileInputStream inputstream = new FileInputStream("fiveStar.png")) {
                image = new Image(inputstream);
            } // fileIn is closed
            catch (IOException e) {
                image = new Image("Ceasar_Salad.png");
            }
            ImageView imageView = new ImageView(image); 
            imageView.setFitHeight(50); 
            imageView.setFitWidth(50); 
            imageView.setPreserveRatio(true);  
            return imageView;
        }
        try ( FileInputStream inputstream = new FileInputStream("award.png")) {
            image = new Image(inputstream);
        } // fileIn is closed
        catch (IOException e) {
            image = new Image("Ceasar_Salad.png");
        }
            ImageView imageView = new ImageView(image); 
            imageView.setFitHeight(500); 
            imageView.setFitWidth(500); 
            imageView.setPreserveRatio(true);  
            return imageView;
    }

}