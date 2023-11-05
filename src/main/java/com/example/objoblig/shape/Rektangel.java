package com.example.objoblig.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//dette er en klasse for Rektangel
public class Rektangel extends Rectangle implements Form {
    private double height = 50.0;
    private double width = 100.0;
    private Color farge;
   private  double x, y;
    public Rektangel(double x, double y, Color farge) {
        this.farge = farge;
        this.x = x;
        this.y = y;
    }

    @Override
    public  void tegn(Pane g) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setFill(farge);
        this.setStroke(Color.BLACK);
        g.getChildren().add(this);
    }
    @Override
    public Color getFarge(){
        return farge;
    }

    public double getAreal() {
        return getWidth() * getHeight();
    }

    public String getForm() {
        return "rektangel";
    }

    @Override
    public void setFarge(Color farge) {
        this.farge = farge;
    }

    @Override
    public void endreStørrelse(double dx, double dy){
        this.width += dx; // Assuming dx controls the width change
        this.height += dy; // Assuming dy controls the height change

        // Ensure both the width and height remain positive to avoid issues
        if (width> 0 && height > 0) {
            this.setWidth(width);
            this.setHeight(height);
        }
    }
    @Override
    public void flytt(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.setX(x);
        this.setY(y);
    }

    @Override
    public boolean isCursorOverShape(double x, double y) {
        double leftX = this.getX(); // Replace with the method that gets the X-coordinate of the rectangle's left edge.
        double rightX = leftX + this.getWidth(); // Replace with the method that gets the rectangle's width.
        double topY = this.getY(); // Replace with the method that gets the Y-coordinate of the rectangle's top edge.
        double bottomY = topY + this.getHeight(); // Replace with the method that gets the rectangle's height.

        // Check if the cursor is within the rectangle's bounds.
        return x >= leftX && x <= rightX && y >= topY && y <= bottomY;
    }
}