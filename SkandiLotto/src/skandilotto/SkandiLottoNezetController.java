
package skandilotto;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SkandiLottoNezetController implements Initializable {
  
    
       DB db = new DB(); 
    
    
    //<editor-fold defaultstate="collapsed" desc="Osztályváltozók">
    private final int MAX = 45;
    private final int MIN = 1;
    
    private int genNum1;
    private int genNum2;
    private int genNum3;
    private int genNum4;
    private int genNum5;
    private int genNum6;
    private int genNum7;
    
    private int setNum1;
    private int setNum2;
    private int setNum3;
    private int setNum4;
    private int setNum5;
    private int setNum6;
    private int setNum7;
//</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="FXML példányosítás">
    
    
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private TextField input3;
    @FXML
    private TextField input4;
    @FXML
    private TextField input5;
    @FXML
    private TextField input6;
    @FXML
    private TextField input7;
    @FXML
    private Pane basePane;
    @FXML
    private Pane alertPane;
    @FXML
    private Pane tablePane;
    @FXML
    private Label alertLabel;
    @FXML
    private Label resultLabel;
    
    @FXML
    private TableView genNumTable;
//</editor-fold>
    
    
    @FXML
    private void alertButtonAction(ActionEvent event){
        alertPane.setVisible(false);
        basePane.setDisable(false);
        basePane.setOpacity(1);
    }
    @FXML
    private void visszaMain(ActionEvent event){
        alertPane.setVisible(false);
        basePane.setDisable(false);
        basePane.setVisible(true);
        tablePane.setVisible(false);
        basePane.setOpacity(1);
    }
    @FXML
    private void tableSwitch(ActionEvent event){
        basePane.setDisable(true);
        basePane.setVisible(false);
        tablePane.setVisible(true);
    }
    
    @FXML
    private void sorsolas(ActionEvent event) {
        genNum1 = 0;
        genNum2 = 0;
        genNum3 = 0;
        genNum4 = 0;
        genNum5 = 0;
        genNum6 = 0;
        genNum7 = 0;
        genNum1 = randomGen();
        genNum2 = randomGen();
        genNum3 = randomGen();
        genNum4 = randomGen();
        genNum5 = randomGen();
        genNum6 = randomGen();
        genNum7 = randomGen();
        
        
        label1.setText("" +genNum1);
        label2.setText("" +genNum2);
        label3.setText("" +genNum3);
        label4.setText("" +genNum4);
        label5.setText("" +genNum5);
        label6.setText("" +genNum6);
        label7.setText("" +genNum7);
        
        
        calculate();
        
        db.addNumbers("INSERT INTO NUMBERS VALUES (?, ?, ?, ?, ?, ?, ?)"
                , genNum1, genNum2, genNum3, genNum4, genNum5, genNum6, genNum7);
        System.out.println("Húzott számok:");
        db.showNumbersMeta("select * from numbers");
        System.out.println("");
        db.showAllNumbers("select * from numbers");
        //db.addDataTable("create table setnumbers(num1 varchar(20), num2 varchar(20), num3 varchar(20), num4 varchar(20), num5 varchar(20), num6 varchar(20), num7 varchar(20))", "setnumbers");
        //db.deleteTable("DELETE FROM NUMBERS");
        
    }
    
    
    
    private void calculate(){
    
        try{
        setNum1 = Integer.parseInt(input1.getText());
        setNum2 = Integer.parseInt(input2.getText());
        setNum3 = Integer.parseInt(input3.getText());
        setNum4 = Integer.parseInt(input4.getText());
        setNum5 = Integer.parseInt(input5.getText());
        setNum6 = Integer.parseInt(input6.getText());
        setNum7 = Integer.parseInt(input7.getText());
        }catch(Exception e){
            alert("Nem jó számot adtál meg!");
            return;
        }
        
        
        // A számok 1 és 45 között vannak-e? If-el
//        if(setNum1 > MAX || setNum1 < MIN || setNum2 > MAX || setNum2 < MIN || setNum3 > MAX || setNum3 < MIN || setNum4 > MAX || setNum4 < MIN || 
//                setNum5 > MAX || setNum5 < MIN || setNum6 > MAX || setNum6 < MIN || setNum7 > MAX || setNum7 < MIN){
//            alert("A számnak 1 és 45 között kell lennie!");
//            return;
//        }
        
        Set <Integer> selectedNumbers = new HashSet<>();
                selectedNumbers.add(setNum1);
                selectedNumbers.add(setNum2);
                selectedNumbers.add(setNum3);
                selectedNumbers.add(setNum4);
                selectedNumbers.add(setNum5);
                selectedNumbers.add(setNum6);
                selectedNumbers.add(setNum7);
                
        if(selectedNumbers.size() < 7){
        alert("Különbözö számokat kell megadnod!");
        }
        
        
        
        ArrayList<Integer> userNumbers = new ArrayList<>(selectedNumbers);
        
        for(int i=0; i<userNumbers.size(); i++)
            if(MAX < userNumbers.get(i) || MIN > userNumbers.get(i))
                alert("A számoknak 1 és 45 között kell lennie!");
        
        
        
        
        db.addNumbers("insert into setnumbers values (?, ?, ?, ?, ?, ?, ?)"
                , setNum1, setNum2, setNum3, setNum4, setNum5, setNum6, setNum7);
        
        System.out.println("Tippelt számok");
        db.showNumbersMeta("select * from setnumbers");
        System.out.println("");
        db.showAllNumbers("select * from setnumbers");
        //db.deleteTable("DELETE FROM setnumbers");

        
        int result =0;
        
        for (int i = 0; i < userNumbers.size(); i++){
            if(userNumbers.get(i) == genNum1 || userNumbers.get(i) == genNum2 || userNumbers.get(i) == genNum3 || userNumbers.get(i) == genNum4 || 
                    userNumbers.get(i) == genNum5 || userNumbers.get(i) == genNum6 || userNumbers.get(i) == genNum7) 
                result++;
        }
        
        
        switch(result){
            case 0: resultLabel.setText("Nincs találat!");
            break;
            case 1: resultLabel.setText("Egy találatod van!");
            break;
            case 2: resultLabel.setText("Két találatod van!");
            break;
            case 3: resultLabel.setText("Három találatod van!");
            break;
            case 4: resultLabel.setText("Négy találatod van!");
            break;
            case 5: resultLabel.setText("Öt találatod van!");
            break;
            case 6: resultLabel.setText("Hat találatod van!");
            break;
            case 7: resultLabel.setText("Telitalálat, gazdag vagy!");
            break;
        }
    }
    
    
    private int randomGen(){
        
        int random = (int) (Math.random() * MAX -MIN) + 1;
        
        if(random == genNum1 || random == genNum2 || random == genNum3 || random == genNum4 || random == genNum5 || 
                random == genNum6 || random == genNum7){
            return randomGen();}
    return random;
    }
    
    private void alert(String alertText){
        
        alertPane.setVisible(true);
        alertLabel.setText(alertText);
        basePane.setDisable(true);
        basePane.setOpacity(0.3);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      db.showAllNumbers("select * from numbers");
    
    }    
    
}
