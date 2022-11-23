package com.example.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class menuBar {

    public static FileChooser fileO;

    public static File saveFile;
    public static MenuBar mb;

    /** Default menu constructor for menu items. */
    public menuBar() {

        /* ----------Image Formats---------- */
        fileO = new FileChooser();
        fileO.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png", "*.png"),
                new FileChooser.ExtensionFilter("jpg", "*.jpg"),
                new FileChooser.ExtensionFilter("bmp", "*.bmp")

        );

        // create a menu
        Menu f = new Menu(" _File ");    //An underscore (_) assesses the alt function Alt+F
        Menu m = new Menu(" _Help ");   //An underscore (_) assesses the alt function Alt+H
        Menu r = new Menu(" Rotate ");   //An underscore (_) assesses the alt function Alt+R
        Menu flip = new Menu(" Flip ");   //An underscore (_) assesses the alt function Alt+P


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

        /** About Menu Item that opens a new window which displays information about the program */

        m2.setOnAction(event -> {

            Label secondLabel = new Label("Martha's Pain(t) Version 2.0.0 -  09/23/2022\n Features includes Scroll bars with an extra pannable feature that let users move around the image\n" +
                    "* Tabs\n" +
                    "* Multiple image formats\n" +
                    "* Eraser\n" +
                    "* Line tool\n" +
                    "* Pencil tool \n" +
                    "* Square, Rectangle, Ellipse, Dashed Line, and Circle tools\n" +
                    "* Saves images\n" +
                    "* Opens images\n" +
                    "* Keyboard Shortcut \n" +
                    "* Smart/Aware for Save \n" +
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
            newWindow.setX(Paint.newStage.getX() + 200);
            newWindow.setY(Paint.newStage.getY() + 100);

            newWindow.show();
        });


        // add menu items to menu
        m.getItems().add(m1);
        m.getItems().add(m2);

        // create menu items for Rotate
        MenuItem r1 = new MenuItem(" Right 90");
        MenuItem r2 = new MenuItem(" Left 90");
        MenuItem r3 = new MenuItem(" Right 180");
        MenuItem r4 = new MenuItem(" Left 180");
        MenuItem r5 = new MenuItem(" Right 270");
        MenuItem r6 = new MenuItem(" Left 270");

        // add rotate menu items to menu
        r.getItems().add(r1);
        r.getItems().add(r2);
        r.getItems().add(r3);
        r.getItems().add(r4);
        r.getItems().add(r5);
        r.getItems().add(r6);

        r1.setOnAction(event -> {tabClass.getTabStack().setRotate(tabClass.getTabStack().getRotate() + 90);});
        r2.setOnAction(event -> {tabClass.getTabStack().setRotate(tabClass.getTabStack().getRotate() - 90);});
        r3.setOnAction(event -> {tabClass.getTabStack().setRotate(tabClass.getTabStack().getRotate() + 180);});
        r4.setOnAction(event -> {tabClass.getTabStack().setRotate(tabClass.getTabStack().getRotate() - 180);});
        r5.setOnAction(event -> {tabClass.getTabStack().setRotate(tabClass.getTabStack().getRotate() + 270);});
        r6.setOnAction(event -> {tabClass.getTabStack().setRotate(tabClass.getTabStack().getRotate() - 270);});

        // create menu items for Flip
        MenuItem flipH = new MenuItem(" Horizontal");
        MenuItem flipV = new MenuItem(" Vertical");

        // add Flip menu items to menu
        flip.getItems().add(flipH);
        flip.getItems().add(flipV);

        flipH.setOnAction(event -> {tabClass.getTabCanvas().setScaleX(-tabClass.getTabCanvas().getScaleX());});
        flipV.setOnAction(event -> {tabClass.getTabCanvas().setScaleY(-tabClass.getTabCanvas().getScaleY());});

        // create a menuBar
        mb = new MenuBar();
        mb.setStyle("-fx-base: #be9b7b;"); //the base color of the entire menu system.
        //mb.setStyle("-fx-background-color: #be9b7b;");


        // add menu to menuBar
        mb.getMenus().add(f);
        mb.getMenus().add(m);
        mb.getMenus().add(r);
        mb.getMenus().add(flip);

        /*------- Save & Open ------*/

        /** Opens file when f1 is pressed with the setOnAction event */
        f1.setOnAction((e)->{
            fileO.setTitle("Open File");
            File file = fileO.showOpenDialog(Paint.newStage);
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


        /** Saves file when f2 is pressed with the setOnAction event */
        f2.setOnAction((e)->{

            try {
                WritableImage writableImage = new WritableImage(1080, 790);
                tabClass.getTabCanvas().snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", saveFile);
            } catch (IOException ex) {
                System.out.println("Error!");
            }

        });

        /** Saves file as when f3 is pressed with the setOnAction event */
        f3.setOnAction((e)->{
            fileO.setTitle("SaveAs");

            File file = fileO.showSaveDialog(Paint.newStage);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(1080, 790);
                    tabClass.getTabCanvas().snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }

                saveFile = file;

            }

        });

        // New Function
        /** Creates a new tab when f4 is pressed with the setOnAction event */
        f4.setOnAction((e)->{
            tabClass  newT = new tabClass();
            Paint.tabpane.getTabs().add(newT);
            Paint.tabpane.getSelectionModel().select(newT);
        });

        /* ----------KeyCode Combination---------- */
        f1.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        f2.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        f3.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        f4.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));


    }


}
