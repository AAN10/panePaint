package com.example.objoblig.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


public class Oval extends Ellipse implements Form {
    private double height = 50.0;
    private double width = 100.0;
    private double x,y;
    private double radius;
    private Color farge;

    public Oval(double dx, double dy, double radius, Color farge){
        this.x = dx;
        this.y = dy;
        this.radius = radius;
        this.farge = farge;
    }


    @Override
    public void tegn(Pane g) {
        setRadiusX(height); // Set the horizontal radius
        setRadiusY(width); // Set the vertical radius
        setCenterX(x); // Set the X-coordinate of the ellipse's center
        setCenterY(y); // Set the Y-coordinate of the ellipse's center
        setFill(farge);
        setStroke(Color.BLACK);
        g.getChildren().add(this);
    }

    @Override
    public double getAreal() {
        double area = Math.PI * (width / 2) * (height / 2);
        return area;
    }

    @Override
    public String getForm() {
        return "Oval";
    }

    @Override
    public void setFarge(Color farge) {
        this.farge = farge;
    }

    @Override
    public Color getFarge() {
        return farge;
    }

    @Override
    public void flytt(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.setCenterX(x); // Update the X coordinate of the circle's center.
        this.setCenterY(y); // Update the Y coordinate of the circle's center.
    }

    @Override
    public void endreStørrelse(double dx, double dy) {
        this.height += dx;
        this.width += dy;
        // Ensure that the new radii are non-negative
        if (this.height > 0 && this.width > 0) {
            setRadiusX(height);
            setRadiusY(width);
        }
    }

    @Override
    public boolean isCursorOverShape(double x, double y) {
        return this.getBoundsInParent().contains(x, y);
    }
}
