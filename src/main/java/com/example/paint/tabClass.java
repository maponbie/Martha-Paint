package com.example.paint;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class tabClass extends Tab {

    private static ScrollPane tabScroll;

    private static addCanvas tabCanvas;

    private static Pane tabStack;

    private static String CanvasN ="Canvas ";

    private static int proNum = 1;

    /** Default tabClass constructor for tab class. */
    public tabClass() {

        this.tabScroll = new ScrollPane();
        this.tabStack = new Pane();
        this.tabCanvas = new addCanvas();

        tabStack.getChildren().add(tabCanvas);

        tabScroll.setContent(tabStack); //Canvas in ScrollPane

        this.setContent(tabScroll); //Embeds scroll pane in the tab pane

        this.setText(CanvasN + proNum );
        proNum ++;

    }

    /** getTabStack() returns the pane that the tab is embedded in
     * @return tabStack
     * */
    public static Pane getTabStack() {
        return tabStack;
    }

    /** getTabCanvas() returns a new tab canvas when called
     * @return tabCanvas
     * */
    public static addCanvas getTabCanvas() {
        return tabCanvas;
    }

}
