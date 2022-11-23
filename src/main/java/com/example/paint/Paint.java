package com.example.paint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Paint extends Application {

    public static TabPane tabpane;

    public static Pane canvasStack;

    public static Stage newStage;

    public static menuBar paintMenu;

    public static buttonBar newButton;

    @Override

    public void start(Stage primaryStage) {

        newStage = primaryStage;

        paintMenu = new menuBar();

        newButton = new buttonBar();

        /* ----------Canvas/Workspace---------- */
        addCanvas canvas = new addCanvas();

        //Creating undo, redo, rotate and clear buttons
        Button undo = new Button("Undo");
        undo.setTooltip(new Tooltip("Undo"));

        Button redo = new Button("Redo");
        redo.setTooltip(new Tooltip("Redo"));

        Button clear = new Button("Clear");
        clear.setTooltip(new Tooltip("Clear Canvas"));

        Button[] menuArgs = {undo, redo, clear}; //Arguments for the for loop

        for (Button btn : menuArgs) {
            btn.setMinWidth(90);
            btn.setCursor(Cursor.HAND);
            btn.setTextFill(javafx.scene.paint.Paint.valueOf("#4b3832"));
            btn.setStyle("-fx-background-color: #fff4e6;");   //Sets the background and text colors for the buttons
        }

        undo.setOnAction(e -> {canvas.undo();});

        redo.setOnAction(e -> {canvas.redo();});

        clear.setOnAction(e -> {canvas.clearCanvas();});


        /* ----------Add MenuBar to HBox---------- */

        HBox hBtns = new HBox(8);          //Aligns the buttons horizontally with padding and color specification
        hBtns.getChildren().addAll(menuBar.mb, undo, redo, clear);
        hBtns.setPadding(new Insets(8));
        hBtns.setStyle("-fx-background-color: #4b3832");
        hBtns.setPrefWidth(10);

        /* ----------STAGE & SCENE---------- */
        BorderPane pane = new BorderPane();

        /* ----------PANE---------- */
        canvasStack = new Pane();
        canvasStack.getChildren().add(canvas);

        /* ----------TAB PANE---------- */
        tabpane = new TabPane();
        tabpane.getTabs().add(new tabClass());


        /* ----------SCROLL PANE---------- */
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(canvasStack);  //Embeds canvas in scroll pane
        //scroll.setPannable(true); //Pannable


        pane.setLeft(newButton.vBtns);   //Position of the elements in the pane
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
