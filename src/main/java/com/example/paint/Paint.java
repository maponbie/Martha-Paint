package com.example.paint;

//Importations

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.layout.StackPane;



public class Paint extends Application {

    public static ToggleButton drawBtn, lineBtn, rectBtn, ellipseBtn, circleBtn, eraserBtn, squareBtn, dashBtn;

    public static  Slider scale;

    public static Label line_color, fill_color, line_width;

    public static FileChooser fileO;

    public static File saveFile;

    public static TabPane tabpane;
    
    @Override

    public void start(Stage primaryStage) {

        scale = new Slider(1, 50, 3); //scale tool
        scale.setShowTickLabels(true);
        scale.setShowTickMarks(true);
        scale.setStyle("-fx-background-color: #FFFFFF;");

        /* ----------Canvas/Workspace---------- */
        addCanvas canvas = new addCanvas();


        /* ----------Image Formats---------- */
        fileO = new FileChooser();
        fileO.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png", "*.png"),
                new FileChooser.ExtensionFilter("jpg", "*.jpg"),
                new FileChooser.ExtensionFilter("bmp", "*.bmp") 
                
        );

        /* ----------Shapes---------- */

         drawBtn = new ToggleButton("Pencil"); //Pencil tool
         lineBtn = new ToggleButton("Line"); //Line tool
         squareBtn = new ToggleButton("Square"); //Square tool
         dashBtn = new ToggleButton("Dash Line"); //Dash Line tool
         rectBtn = new ToggleButton("Rectangle"); //Rectangle tool
         ellipseBtn = new ToggleButton("Ellipse"); //Ellipse tool
         circleBtn = new ToggleButton("Circle"); //Circle tool
         eraserBtn = new ToggleButton("Eraser"); //Eraser tool

        ToggleButton[] toolsArr = {drawBtn, lineBtn, rectBtn, ellipseBtn, circleBtn, squareBtn, dashBtn, eraserBtn};

        ToggleGroup tools = new ToggleGroup();

        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }


        //Labels for color buttons and scale
        line_color = new Label("Line Color");
        fill_color = new Label("Fill Color");
        line_width = new Label("3.0");

        line_color.setTextFill(Color.web("#FFFFFF"));
        fill_color.setTextFill(Color.web("#FFFFFF"));
        line_width.setTextFill(Color.web("#FFFFFF"));


        VBox vBtns = new VBox(10);          //Aligns the buttons vertically with padding and color specification
        vBtns.getChildren().addAll(drawBtn, lineBtn, dashBtn, squareBtn, rectBtn, ellipseBtn, circleBtn, eraserBtn, line_color, canvas.getCP(), fill_color, canvas.getCF(), line_width, scale);
        vBtns.setPadding(new Insets(5));
        vBtns.setStyle("-fx-background-color: #4b3832");
        vBtns.setPrefWidth(100);

        //Creating undo and redo buttons
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");


        Button[] menuArgs = {undo, redo}; //Arguments for the for loop

        for (Button btn : menuArgs) {
            btn.setMinWidth(90);
            btn.setCursor(Cursor.HAND);
            btn.setTextFill(javafx.scene.paint.Paint.valueOf("#4b3832"));
            btn.setStyle("-fx-background-color: #fff4e6;");   //Sets the background and text colors for the buttons
        }

        // create a menu
        Menu f = new Menu(" _File ");    //An underscore (_) assesses the alt function Alt+F
        Menu m = new Menu(" _Help ");   //An underscore (_) assesses the alt function Alt+H

        // create menu items for File
        MenuItem f1 = new MenuItem(" Open ");
        MenuItem f2 = new MenuItem(" Save ");
        MenuItem f3 = new MenuItem(" Save As ");
        MenuItem f4 = new MenuItem(" New Tab ");


        // add menu items to menu
        f.getItems().add(f1);
        f.getItems().add(f2);
        f.getItems().add(f3);
        f.getItems().add(f4);

        // create menu items for Help
        MenuItem m1 = new MenuItem("  Help ");
        MenuItem m2 = new MenuItem("  About ");

        //About Menu Item that opens a new window which displays information about the program

        m2.setOnAction(event -> {

            Label secondLabel = new Label("Martha's Pain(t) Version 1.5.0 -  09/16/2022\n Features includes Scroll bars with an extra pannable feature that let users move around the image\n" +
                    "* Line tool\n" +
                    "* Draw tool \n" +
                    "* Saves images\n" +
                    "* Opens images\n" +
                    "* Canvas that resizes to fit the image being opened \n" +
                    "* Scale Tool which allows users to control the width of the line drawn\n" +
                    "* Color chooser \n" +
                    "* Help menu items with help and about options");

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);

            Scene secondScene = new Scene(secondaryLayout, 550, 200);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("About");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);

            newWindow.show();
        });


        // add menu items to menu
        m.getItems().add(m1);
        m.getItems().add(m2);

        // create a menuBar
        MenuBar mb = new MenuBar();
        mb.setStyle("-fx-base: #be9b7b;"); //the base color of the entire menu system.
        //mb.setStyle("-fx-background-color: #be9b7b;");


        // add menu to menuBar
        mb.getMenus().add(f);
        mb.getMenus().add(m);


        /* ----------Add MenuBar to HBox---------- */

        HBox hBtns = new HBox(8);          //Aligns the buttons horizontally with padding and color specification
        hBtns.getChildren().addAll(mb, undo, redo);
        hBtns.setPadding(new Insets(8));
        hBtns.setStyle("-fx-background-color: #4b3832");
        hBtns.setPrefWidth(10);



        /*------- Save & Open ------*/
        // Open
        f1.setOnAction((e)->{
            fileO.setTitle("Open File");
            File file = fileO.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    addCanvas.getGc().drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });

        //Save Function

        f2.setOnAction((e)->{

            try {
                WritableImage writableImage = new WritableImage(1080, 790);
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", saveFile);
            } catch (IOException ex) {
                System.out.println("Error!");
            }

        });

        // Save As Function
        f3.setOnAction((e)->{
            fileO.setTitle("SaveAs");

            File file = fileO.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(1080, 790);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }

                saveFile = file;

            }

        });

        // New Function
        f4.setOnAction((e)->{
            tabClass  newT = new tabClass();
            tabpane.getTabs().add(newT);
            tabpane.getSelectionModel().select(newT);
                });

        /* ----------KeyCode Combination---------- */
        f1.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        f2.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        f3.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        f4.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));


        /* ----------STAGE & SCENE---------- */
        BorderPane pane = new BorderPane();

        /* ----------TAB PANE---------- */
        tabpane = new TabPane();
        tabpane.getTabs().add(new tabClass());


        /* ----------SCROLL PANE---------- */
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(canvas);  //Embeds canvas in scroll pane
        //scroll.setPannable(true); //Pannable


        pane.setLeft(vBtns);   //Position of the elements in the pane
        pane.setCenter(tabpane); //Embeds tab pane in the border pane
        pane.setTop(hBtns);

        Scene scene = new Scene(pane, 1200, 800);

        primaryStage.setTitle("Martha's Paint");   //Application Name

        /* ----------Smart Alert  Dialog Box---------- */
        primaryStage.setOnCloseRequest(evt -> {
            // allow user to decide between yes and no
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you close without saving?", ButtonType.YES, ButtonType.NO);

            // clicking X also means no
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

            if (ButtonType.NO.equals(result)) {
                // consume event i.e. ignore close request
                evt.consume();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
