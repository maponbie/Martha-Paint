package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import java.util.Optional;
import java.util.Stack;

public class addCanvas extends Canvas {

    private static GraphicsContext gc;

    public static Stack<Shape> undoHistory;

    public static Stack<Shape> redoHistory;

    private Line line;
    private Line dash;

    private Rectangle rect;
    private Rectangle roundedRect;
    private Rectangle square;
    private Circle circ;

    private Ellipse elps;

    private Polygon poly;

    /** Default addCanvas constructor for canvas. */

    public addCanvas() {
        this.setWidth(1080);
        this.setHeight(790);
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineWidth(1);
        this.line = new Line();
        this.dash = new Line();

        this.rect = new Rectangle();
        this.circ = new Circle();
        this.elps = new Ellipse();

        this.square = new Rectangle();
        this.roundedRect = new Rectangle();

        //this.poly = new Polygon();


        undoHistory = new Stack();
        redoHistory = new Stack();

        /**
         * Called when the user presses the mouse on ANY one of the canvasses.
         */

        this.setOnMousePressed(e->{       //Event Handler-Defines the function to be called when a mouse button has been pressed on this Node.
          initDraw(gc, buttonBar.colorChooser, buttonBar.colorFill, buttonBar.scale);

            if(buttonBar.drawBtn.isSelected()) {
                gc.beginPath();
                gc.lineTo(e.getX(), e.getY());
            }

            else if(buttonBar.eraserBtn.isSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }

            else if(buttonBar.lineBtn.isSelected()) {
                line.setStartX(e.getX());
                line.setStartY(e.getY());
            }

            else if(buttonBar.dashBtn.isSelected()) {
                dash.setStartX(e.getX());
                dash.setStartY(e.getY());
            }

            else if(buttonBar.rectBtn.isSelected()) {
                rect.setX(e.getX());
                rect.setY(e.getY());
            }

            else if(buttonBar.roundedBtn.isSelected()) {
                roundedRect.setX(e.getX());
                roundedRect.setY(e.getY());
            }

            else if(buttonBar.squareBtn.isSelected()) {      //SQUARE
                square.setX(e.getX());
                square.setY(e.getY());
            }

            else if(buttonBar.circleBtn.isSelected()) {
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
            }
            else if(buttonBar.ellipseBtn.isSelected()) {
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
            }

            /*
            else if(Paint.polygonBtn.isSelected()) {
                gc.setStroke(colorChooser.getValue());
                gc.setFill(colorFill.getValue());
                poly.getPoints().addAll(e.getX(), e.getY());
            }

             */

        });

        /**
         * Called when the user drags the mouse on any of the canvasses.
         */
        this.setOnMouseDragged(e->{       //Event Handler-Defines the function to be called when a mouse button has been dragged on this Node.
            if(buttonBar.drawBtn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }

            else if(buttonBar.eraserBtn.isSelected()){
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
        });

        /**
         * Called when the user releases the mouse on any of the canvasses.
         */
        this.setOnMouseReleased(e->{      //Event Handler-Defines the function to be called when a mouse button has been released.
            if(buttonBar.drawBtn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
            }

            else if(buttonBar.eraserBtn.isSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }

            else if(buttonBar.lineBtn.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

                undoHistory.push(new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));

            }

            else if(buttonBar.dashBtn.isSelected()) {
                dash.setEndX(e.getX());
                dash.setEndY(e.getY());

                gc.setLineDashes(5);
                gc.setLineDashOffset(5.0);
                gc.strokeLine(dash.getStartX(), dash.getStartY(), dash.getEndX(), dash.getEndY());

                undoHistory.push(new Line(dash.getStartX(), dash.getStartY(), dash.getEndX(), dash.getEndY()));
            }

            else if(buttonBar.rectBtn.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                if(rect.getX() > e.getX()) {
                  rect.setX(e.getX());
               }

                if(rect.getY() > e.getY()) {
                  rect.setY(e.getY());
                }

                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

                undoHistory.push(new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
            }

            else if(buttonBar.roundedBtn.isSelected()) {
                roundedRect.setWidth(Math.abs((e.getX() - roundedRect.getX())));
                roundedRect.setHeight(Math.abs((e.getY() - roundedRect.getY())));
                if(roundedRect.getX() > e.getX()) {
                    roundedRect.setX(e.getX());
                }

                if(roundedRect.getY() > e.getY()) {
                    roundedRect.setY(e.getY());
                }

                roundedRect.setArcWidth(30.0);
                roundedRect.setArcHeight(20.0);

                gc.fillRect(roundedRect.getX(), roundedRect.getY(), roundedRect.getWidth(), roundedRect.getHeight());
                gc.strokeRoundRect(roundedRect.getX(), roundedRect.getY(), roundedRect.getWidth(), roundedRect.getHeight(),roundedRect.getArcWidth(),roundedRect.getArcHeight());

                undoHistory.push(new Rectangle(roundedRect.getX(), roundedRect.getY(), roundedRect.getWidth(), roundedRect.getHeight())); //,roundedRect.getArcWidth(),roundedRect.getArcHeight()
            }

            else if(buttonBar.squareBtn.isSelected()) {                  //SQUARE
                square.setX(Math.min(e.getX() ,square.getX()));
                square.setY(Math.min(e.getY() ,square.getY()));

                square.setWidth(Math.abs((e.getX() - square.getX())));
                square.setHeight(Math.abs((e.getX() - square.getX())));

                gc.fillRect(square.getX(), square.getY(), square.getWidth(), square.getHeight());
                gc.strokeRect(square.getX(), square.getY(), square.getWidth(), square.getHeight());

                undoHistory.push(new Rectangle(square.getX(), square.getY(), square.getWidth(), square.getHeight()));
            }

            else if(buttonBar.circleBtn.isSelected()) {
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);

                if(circ.getCenterX() > e.getX()) {
                    circ.setCenterX(e.getX());
                }
                if(circ.getCenterY() > e.getY()) {
                    circ.setCenterY(e.getY());
                }

                gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());

                undoHistory.push(new Circle(circ.getCenterX(), circ.getCenterY(), circ.getRadius()));

            }
            else if(buttonBar.ellipseBtn.isSelected()) {
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));

                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }

                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());

                undoHistory.push(new Ellipse(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY()));
            }

            /*
            else if(Paint.polygonBtn.isSelected()) {
                poly.getPoints().set(poly.getPoints().size()-2, e.getX());
                poly.getPoints().set(poly.getPoints().size()-1, e.getY());

                gc.strokePolygon(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillPolygon(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());

            }

             */

            redoHistory.clear();
            Shape lastUndo = undoHistory.lastElement();
            lastUndo.setFill(gc.getFill());
            lastUndo.setStroke(gc.getStroke());
            lastUndo.setStrokeWidth(gc.getLineWidth());

        });

        // Scaling Tool
        buttonBar.scale.valueProperty().addListener(e->{
            double width = buttonBar.scale.getValue();
            buttonBar.line_width.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });

    }

    /** getGc() returns a canvas when called
     * @return canvas
     * */
    public static GraphicsContext getGc() {
        return gc;
    }


    /**
     * When initDraw (gc, buttonBar.colorChooser, buttonBar.colorFill, buttonBar.scale); is called, it returns the stroke, fill, and line width for the shapes
     * @param gc canvas
     * @param colorChooser color for the line stroke
     * @param colorFill fill color
     * @param scale stroke width
     */
    public static void initDraw(GraphicsContext gc, ColorPicker colorChooser, ColorPicker colorFill, Slider scale){
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorFill.getValue());
        gc.setLineWidth(scale.getValue());

    }

    /** clearCanvas() alerts users when they are about to delete the elements on a canvas */
    /*------- Clear Canvas ------*/
    public void clearCanvas() {
        Alert clear = new Alert(Alert.AlertType.CONFIRMATION);
        clear.setTitle("Confirmation");
        clear.setHeaderText("Want to Clear Canvas?");
        clear.setContentText("Are you sure?");

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        clear.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = clear.showAndWait();
        if(result.get() == yes) {gc.clearRect(0, 0, this.getWidth(), this.getHeight());}
        else {
            result.get();
        }

    }

    /*------- Undo & Redo ------*/
    /** undo() reverts the canvas to its original form with Stack of Shape undoHistory */

    public static void undo() {
            if (!undoHistory.empty()) {
                gc.clearRect(0, 0, 1080, 790);
                Shape removedShape = undoHistory.lastElement();
                if (removedShape.getClass() == Line.class) {
                    Line tempLine = (Line) removedShape;
                    tempLine.setFill(gc.getFill());
                    tempLine.setStroke(gc.getStroke());
                    tempLine.setStrokeWidth(gc.getLineWidth());
                    redoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));

                } else if (removedShape.getClass() == Rectangle.class) {
                    Rectangle tempRect = (Rectangle) removedShape;
                    tempRect.setFill(gc.getFill());
                    tempRect.setStroke(gc.getStroke());
                    tempRect.setStrokeWidth(gc.getLineWidth());
                    redoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                } else if (removedShape.getClass() == Circle.class) {
                    Circle tempCirc = (Circle) removedShape;
                    tempCirc.setStrokeWidth(gc.getLineWidth());
                    tempCirc.setFill(gc.getFill());
                    tempCirc.setStroke(gc.getStroke());
                    redoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                } else if (removedShape.getClass() == Ellipse.class) {
                    Ellipse tempElps = (Ellipse) removedShape;
                    tempElps.setFill(gc.getFill());
                    tempElps.setStroke(gc.getStroke());
                    tempElps.setStrokeWidth(gc.getLineWidth());
                    redoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                }
                Shape lastRedo = redoHistory.lastElement();
                lastRedo.setFill(removedShape.getFill());
                lastRedo.setStroke(removedShape.getStroke());
                lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
                undoHistory.pop();

                for (int i = 0; i < undoHistory.size(); i++) {
                    Shape shape = undoHistory.elementAt(i);
                    if (shape.getClass() == Line.class) {
                        Line temp = (Line) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
                    } else if (shape.getClass() == Rectangle.class) {
                        Rectangle temp = (Rectangle) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                        gc.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                    } else if (shape.getClass() == Circle.class) {
                        Circle temp = (Circle) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                        gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                    } else if (shape.getClass() == Ellipse.class) {
                        Ellipse temp = (Ellipse) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                        gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                    }
                }
            } else {
                System.out.println("there is no action to undo");
            }

    }


    /** redo() brings back the edits on a canvas with Stack of Shape redoHistory */
    public static void redo() {

            if (!redoHistory.empty()) {
                Shape shape = redoHistory.lastElement();
                gc.setLineWidth(shape.getStrokeWidth());
                gc.setStroke(shape.getStroke());
                gc.setFill(shape.getFill());

                redoHistory.pop();
                if (shape.getClass() == Line.class) {
                    Line tempLine = (Line) shape;
                    gc.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());
                    undoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
                } else if (shape.getClass() == Rectangle.class) {
                    Rectangle tempRect = (Rectangle) shape;
                    gc.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                    gc.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());

                    undoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                } else if (shape.getClass() == Circle.class) {
                    Circle tempCirc = (Circle) shape;
                    gc.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                    gc.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());

                    undoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                } else if (shape.getClass() == Ellipse.class) {
                    Ellipse tempElps = (Ellipse) shape;
                    gc.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                    gc.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());

                    undoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                }
                Shape lastUndo = undoHistory.lastElement();
                lastUndo.setFill(gc.getFill());
                lastUndo.setStroke(gc.getStroke());
                lastUndo.setStrokeWidth(gc.getLineWidth());
            } else {
                System.out.println("there is no action to redo");
            }


    }

}
