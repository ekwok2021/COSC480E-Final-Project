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
import java.util.ArrayList;

public class Order{

    private ArrayList<Item> ORDER;
    /**
     * Constructor of fruitList.
     */
    public Order(){
        ORDER=new ArrayList();
    }
    /**
     * Get method for fruitList.
     * @return Fruit at position i.
     */
    public Item getItem(int i){
        return ORDER.get(i);
    }
    /**
     * Remove method for fruitList.
     * @param f: Fruit to be removed.
     */
    public void removeItem(Item f){
        ORDER.remove(f);
    }
    /**
     * Add method for fruitList.
     * @param f: Fruit to be Added.
     */
    public void addItem(Item f){
        ORDER.add(f);
    }
    /**
     * Size method for fruitList.
     * @return size of fruitList.
     */
    public int getTotal(){
        return ORDER.size();
    }
    public String toString(){
        String str = "";
        for(int i =0; i<ORDER.size();i++){
            str += ORDER.get(i).toString() + "\n";
        }
        return str;
    }

}
