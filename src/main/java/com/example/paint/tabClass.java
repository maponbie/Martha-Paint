package com.example.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.shape.Line;

public class tabClass extends Tab {

    private static ScrollPane tabScroll;

    private static addCanvas tabCanvas;

    private static String CanvasN ="Canvas ";

    private static int proNum = 1;

    public tabClass() {

        this.tabScroll = new ScrollPane();
        this.tabCanvas = new addCanvas();

        tabScroll.setContent(tabCanvas); //Canvas in ScrollPane

        this.setContent(tabScroll); //Embeds scroll pane in the tab pane
        this.setText(CanvasN + proNum );
        proNum ++;
    }

}
