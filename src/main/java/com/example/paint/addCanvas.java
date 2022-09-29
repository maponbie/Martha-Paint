package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class addCanvas extends Canvas {

    private static GraphicsContext gc;

    private Line line;

    private Rectangle rect;

    private Circle circ;

    private Ellipse elps;

    private static ColorPicker colorChooser, colorFill;

    public addCanvas() {
        this.setWidth(1080);
        this.setHeight(790);
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineWidth(1);
        this.line = new Line();

        this.rect = new Rectangle();
        this.circ = new Circle();
        this.elps = new Ellipse();

         colorChooser = new ColorPicker(Color.BLACK); //Color chooser
         colorFill = new ColorPicker(Color.TRANSPARENT); //Fill

        
        this.setOnMousePressed(e->{       //Event Handler-Defines the function to be called when a mouse button has been pressed on this Node.
            if(Paint.drawBtn.isSelected()) {
                gc.setStroke(colorChooser.getValue());
                gc.beginPath();
                gc.lineTo(e.getX(), e.getY());
            }

            else if(Paint.eraserBtn.isSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }

            else if(Paint.lineBtn.isSelected()) {
                gc.setStroke(colorChooser.getValue());
                line.setStartX(e.getX());
                line.setStartY(e.getY());
            }

            else if(Paint.rectBtn.isSelected()) {
                gc.setStroke(colorChooser.getValue());
                gc.setFill(colorFill.getValue());
                rect.setX(e.getX());
                rect.setY(e.getY());
            }
            else if(Paint.circleBtn.isSelected()) {
                gc.setStroke(colorChooser.getValue());
                gc.setFill(colorFill.getValue());
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
            }
            else if(Paint.ellipseBtn.isSelected()) {
                gc.setStroke(colorChooser.getValue());
                gc.setFill(colorFill.getValue());
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
            }
        });

        this.setOnMouseDragged(e->{       //Event Handler-Defines the function to be called when a mouse button has been dragged on this Node.
            if(Paint.drawBtn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }

            else if(Paint.eraserBtn.isSelected()){
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
        });

        this.setOnMouseReleased(e->{      //Event Handler-Defines the function to be called when a mouse button has been released.
            if(Paint.drawBtn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
            }

            else if(Paint.eraserBtn.isSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }

            else if(Paint.lineBtn.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

            }

            else if(Paint.rectBtn.isSelected()) {
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


            }
            else if(Paint.circleBtn.isSelected()) {
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);

                if(circ.getCenterX() > e.getX()) {
                    circ.setCenterX(e.getX());
                }
                if(circ.getCenterY() > e.getY()) {
                    circ.setCenterY(e.getY());
                }

                gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());

            }
            else if(Paint.ellipseBtn.isSelected()) {
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

            }

        });

        //Color Chooser
        colorChooser.setOnAction(e->{      //The action handler associated with the Color Chooser
            gc.setStroke(colorChooser.getValue());
        });
        colorFill.setOnAction(e->{        //The action handler associated with the Color Fill
            gc.setFill(colorFill.getValue());
        });

        // Scaling Tool
        Paint.scale.valueProperty().addListener(e->{
            double width = Paint.scale.getValue();
            Paint.line_width.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });

    }

    public static GraphicsContext getGc() {
        return gc;
    }

    public static ColorPicker getCP() {
        return colorChooser;
    }

    public static ColorPicker getCF() {
        return colorFill;
    }

}
