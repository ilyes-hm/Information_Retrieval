/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riw;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author ilyes
 */
public class Main extends Application {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Button btn = new Button();
        btn.setText("Créer L'index de votre Corpus");
        
        btn.setTranslateX(0);
        btn.setTranslateY(0);
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 900, 700);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                root.getChildren().clear();
                
                TextArea tr1 = new TextArea();
                TextArea tr2 = new TextArea();
                TextArea tr3 = new TextArea();
                
                tr1.setTranslateX(-230);
                tr1.setTranslateY(-40);
                tr1.setMaxSize(430, 550);

                tr2.setTranslateX(+230);
                tr2.setTranslateY(-40);
                tr2.setMaxSize(430, 550);
                
                tr3.setText("Entrer une requete");
                tr3.setTranslateX(0);
                tr3.setTranslateY(+285);
                tr3.setMaxSize(550, 2);

                Button sub = new Button("Requète"); 
                sub.setTranslateX(0);
                sub.setTranslateY(+325);
                
                sub.setOnAction(new EventHandler<ActionEvent>() {
                    
                    public void handle(ActionEvent event) {
                    Index i=new Index();
                    String nom = tr3.getText();
                    File repertoire = new File("/home/ilyes/Riw/Sortie/");
                    Stage secondeStage = new Stage();
                    StackPane root = new StackPane();
                    Scene scene = new Scene(root, 300, 200);
                    TextArea res = new TextArea();
                    Label l = new Label("Résultat");
                    res.setTranslateX(0);
                    res.setTranslateY(0);
                    res.setMaxSize(270, 300);
                    l.setTranslateX(0);
                    l.setTranslateY(-90);
                    l.setStyle("-fx-font-weight: bold");
                    l.setTextFill(Color.web("#0076a3"));
                
                    try {
                            
                            i.distance(nom,repertoire,root);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    root.getChildren().add(l);
                
                    secondeStage.setTitle("Résultat de la requète");
                    secondeStage.setScene(scene);
                    secondeStage.show();
                    }
                });
                
                Label l1 = new Label("Index Brute");
                Label l2 = new Label("Liste de Postage");
                Label l3 = new Label("Index Créer avec succée");
                
                l1.setStyle("-fx-font-weight: bold");
                l1.setTextFill(Color.web("#0076a3"));
                l1.setTranslateX(-390);
                l1.setTranslateY(-335);
                
                l2.setStyle("-fx-font-weight: bold");
                l2.setTextFill(Color.web("#0076a3"));
                l2.setTranslateX(+83);
                l2.setTranslateY(-335);
                
                l3.setStyle("-fx-font-weight: bold");
                l3.setTextFill(Color.web("#ff0000"));
                l3.setTranslateX(0);
                l3.setTranslateY(+250);
                
                
                root.getChildren().add(l1);
                root.getChildren().add(tr1);
                root.getChildren().add(tr2);
                root.getChildren().add(tr3);
                root.getChildren().add(l2);
                root.getChildren().add(l3);
                root.getChildren().add(sub);
                File repertoire = new File("/home/ilyes/Riw/Corpus/");
                File mot_vide = new File("/home/ilyes/Riw/stopwords_fr.txt");
                File r_sortie = new File("/home/ilyes/Riw/Sortie/");
                Index i=new Index();
                        try {
                            i.creation_index(mot_vide,repertoire,r_sortie,tr1,tr2);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
        primaryStage.setTitle("Création d'un index de moteur de recherche");
        primaryStage.setScene(scene);
        primaryStage.show();        
            
    }
    public static void main(String[] args) {
       launch(args);
    }
}
