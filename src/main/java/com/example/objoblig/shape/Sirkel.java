package com.example.objoblig.shape;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

//dette er en klasse for sirkel
public class Sirkel extends Circle implements Form  {

    private Color farge;
    private double radius;
    private double x,y;
    public Sirkel(double ey, double ex, double radius, Color farge) {
        super(ey, ex, radius , farge);
        this.farge = farge;
        this.x = ex;
        this.y = ey;
        this.radius = radius;
    }
    public Sirkel(){}
    @Override
    public  void tegn(Pane g) {

         this.setRadius(radius);
         this.setCenterX(x);
         this.setCenterY(y);
         this.setFill(farge);
         this.setStroke(Color.BLACK);
        g.getChildren().add(this);
    }

    public double getAreal() {
        return Math.PI * (getStrokeWidth() / 2) * (getStrokeWidth() / 2);
    }
    @Override
    public String getForm() {
        return "sirkel";
    }

    @Override
    public void setFarge(Color farge) {
        this.farge = farge;
    }

    @Override
   public void endreStørrelse(double dx, double dy){
//        this.width += dx; // Assuming dx controls the width change
//        this.height += dy; // Assuming dy controls the height change
//
//        // Ensure both the width and height remain positive to avoid issues
//        if (width> 0 && height > 0) {
//            this.setWidth(width);
//            this.setHeight(height);
//        }
            this.radius += dx;
            if(radius > 0){
                this.setRadius(radius);
            }

    }

    @Override
    public void flytt(double dx, double dy){
        this.x += dx;
        this.y += dy;
        this.setCenterX(x); // Update the X coordinate of the circle's center.
        this.setCenterY(y); // Update the Y coordinate of the circle's center.

    }

    @Override
    public Color getFarge(){
        return farge;
    }
    @Override
    public boolean isCursorOverShape(double x, double y) {
        // Calculate the distance between the cursor position (x, y) and the center of the circle.
        double centerX = this.getCenterX(); // Replace with the method that gets the X-coordinate of the circle's center.
        double centerY = this.getCenterY(); // Replace with the method that gets the Y-coordinate of the circle's center.
        double radius = this.getRadius(); // Replace with the method that gets the circle's radius.

        // Check if the cursor is within the circle's bounds based on the distance from the center.
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }
}
