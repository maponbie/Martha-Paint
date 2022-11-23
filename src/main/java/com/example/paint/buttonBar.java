package com.example.paint;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Objects;

public class buttonBar {

    public static ToggleButton drawBtn, lineBtn, rectBtn, ellipseBtn, circleBtn, eraserBtn, squareBtn, dashBtn, roundedBtn, polygonBtn;

    public static Slider scale;

    public static Label line_color, fill_color, line_width;

    public static ColorPicker colorChooser;

    public static ColorPicker colorFill;

    public static VBox vBtns;

    /** Default button constructor fot toggle buttons. */
    public buttonBar() {

        colorChooser = new ColorPicker(Color.BLACK); //Color chooser
        colorFill = new ColorPicker(Color.TRANSPARENT); //Fill


        scale = new Slider(1, 50, 3); //scale tool
        scale.setShowTickLabels(true);
        scale.setShowTickMarks(true);
       // scale.setValue(1);
        scale.setStyle("-fx-background-color: #FFFFFF;");

        /* ----------Shapes---------- */

        drawBtn = new ToggleButton("Pencil"); //Pencil tool
        drawBtn.setTooltip(new Tooltip("A draw tool"));


        ImageView image = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/pencil.png"))));
        image.setFitHeight(15);
        image.setFitWidth(15);
        image.setPreserveRatio(true);
        drawBtn.setGraphic(image);
        drawBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        lineBtn = new ToggleButton("Line"); //Line tool
        lineBtn.setTooltip(new Tooltip("Draw a Line"));

        ImageView line = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/line.png"))));
        line.setFitHeight(15);
        line.setFitWidth(15);
        line.setPreserveRatio(true);
        lineBtn.setGraphic(line);
        lineBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        squareBtn = new ToggleButton("Square"); //Square tool
        squareBtn.setTooltip(new Tooltip("Square Shape"));


        ImageView squar = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/square.png"))));
        squar.setFitHeight(15);
        squar.setFitWidth(15);
        squar.setPreserveRatio(true);
        squareBtn.setGraphic(squar);
        squareBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        dashBtn = new ToggleButton("Dash Line"); //Dash Line tool
        dashBtn.setTooltip(new Tooltip("Draw a Dash Line"));

        ImageView das = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/dashline.png"))));
        das.setFitHeight(15);
        das.setFitWidth(15);
        das.setPreserveRatio(true);
        dashBtn.setGraphic(das);
        dashBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        rectBtn = new ToggleButton("Rectangle"); //Rectangle tool
        rectBtn.setTooltip(new Tooltip("Rectangle Shape"));

        ImageView rect = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/rect.png"))));
        rect.setFitHeight(15);
        rect.setFitWidth(15);
        rect.setPreserveRatio(true);
        rectBtn.setGraphic(rect);
        rectBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        ellipseBtn = new ToggleButton("Ellipse"); //Ellipse tool
        ellipseBtn.setTooltip(new Tooltip("Ellipse Shape"));

        ImageView ellipse = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/ellipse.png"))));
        ellipse.setFitHeight(15);
        ellipse.setFitWidth(15);
        ellipse.setPreserveRatio(true);
        ellipseBtn.setGraphic(ellipse);
        ellipseBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);



        circleBtn = new ToggleButton("Circle"); //Circle tool
        circleBtn.setTooltip(new Tooltip("Circle Shape"));

        ImageView circle = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/circle.png"))));
        circle.setFitHeight(15);
        circle.setFitWidth(15);
        circle.setPreserveRatio(true);
        circleBtn.setGraphic(circle);
        circleBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        roundedBtn = new ToggleButton("Round Rect"); //Rounded Rectangle tool
        roundedBtn.setTooltip(new Tooltip("Round Rectangle"));

        ImageView roundedRect = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/roundedrect.png"))));
        roundedRect.setFitHeight(15);
        roundedRect.setFitWidth(15);
        roundedRect.setPreserveRatio(true);
        roundedBtn.setGraphic(roundedRect);
        roundedBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);



        polygonBtn = new ToggleButton("Polygon"); //Polygon tool
        polygonBtn.setTooltip(new Tooltip("Polygon Shape"));

        ImageView polyG = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/polygon.png"))));
        polyG.setFitHeight(15);
        polyG.setFitWidth(15);
        polyG.setPreserveRatio(true);
        polygonBtn.setGraphic(polyG);
        polygonBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        eraserBtn = new ToggleButton("Eraser"); //Eraser tool
        eraserBtn.setTooltip(new Tooltip("Eraser Tool"));

        ImageView erase = new ImageView(new Image(Objects.requireNonNull(Paint.class.getResourceAsStream("icons/eraser.png"))));
        erase.setFitHeight(15);
        erase.setFitWidth(15);
        erase.setPreserveRatio(true);
        eraserBtn.setGraphic(erase);
        eraserBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        ToggleButton[] toolsArr = {drawBtn, lineBtn, rectBtn, ellipseBtn, circleBtn, squareBtn, dashBtn, polygonBtn, eraserBtn, roundedBtn};

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


        vBtns = new VBox(10);          //Aligns the buttons vertically with padding and color specification
        vBtns.getChildren().addAll(drawBtn, lineBtn, dashBtn, squareBtn, rectBtn, ellipseBtn, circleBtn, roundedBtn, polygonBtn, eraserBtn, line_color, colorChooser, fill_color, colorFill, line_width, scale);
        vBtns.setPadding(new Insets(5));
        vBtns.setStyle("-fx-background-color: #4b3832");
        vBtns.setPrefWidth(100);


    }

}
