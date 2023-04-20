import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.shape.Circle; 

public class Item{

    private String name;
    private int protein;
    private int fat;
    private int carbs;
    private Integer amount;
    private double price;
    private double calories;
    private String category;
    //image
    /**
     * Constructor
     * @param n: name of item.
     * @param a: amount of item.
     * @param p: price of item.
     * @param calories: calories of item.
     */
    public Item(String n, Integer a, double p, double calorieIn, int proteinIn, int fatIn, int carbsIn, String categoryIn){
        name = n;
        amount = a;
        price = p;
        calories = calorieIn;
        protein = proteinIn;
        fat = fatIn;
        carbs = carbsIn;
        category = categoryIn;
    }
    /**
     * Get method for name of fruit.
     * @return name of fruit.
     */
    public String getName(){
        return name;
    }
    /**
     * Get method for amount of fruit.
     * @return amount of fruit.
     */
    
    public Integer getAmount(){
        return amount;
    }
    /**
     * Set method for smount of fruit.
     * @param i: Integer that will be set as the amount of fruit.
     */
    public void setAmount(Integer i){
        amount = i;
    }
    /**
     * Get method for price of fruit.
     * @return price of fruit.
     */
    public double getPrice(){
        return price;
    }
    /**
     * Get method for Circle that corresponds to the fruit.
     * @return The circle that corresponds to the fruit.
     */
    public double getCalorie(){
        return calories;
    }
    /**
     * Get method for color of fruit.
     * @return color of fruit.
     */
    public int getProtein(){
        return protein;
    }

    public int getFat(){
        return fat;
    }

    public int getCarbs(){
        return carbs;
    }
    public String getCategory(){
        return category;
    }

    /**
     * To String method for fruit.
     * @return Fruit as a String.
     */
    public String toString() {
        return name + ", " + amount + ", " + price;
    }


}
