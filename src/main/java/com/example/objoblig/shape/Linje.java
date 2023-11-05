package com.example.objoblig.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Linje extends Line implements Form {
    double startX, startY, endX, endY;
    private Color farge;

    Color color;
    public Linje() {
    }
    public Linje(double startX, double startY, double endX, double endY, Color color) {
        super(startX, startY, endX, endY);
        // implementerre en konstruktør for linje
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color =color;
    }

    @Override
    public void tegn(Pane g) {
        // implemetere tegn metoden for linje

        this.setStartX(startX);
        this.setStartY(startY);
        this.setEndX(endX);
        this.setEndY(endY);
        this.setStroke(farge);
        g.getChildren().addAll(this);
    }
    @Override
    public Color getFarge(){
        return farge;
    }

    @Override
    public void setFarge(Color farge) {
        this.farge = farge;
    }

    @Override
    public void flytt(double dx, double dy) {
        // Update the line's start and end coordinates based on the new position
        startX += dx;
        startY += dy;
        endX += dx;
        endY += dy;

        // Set the new coordinates
        this.setStartX(startX);
        this.setStartY(startY);
        this.setEndX(endX);
        this.setEndY(endY);
    }
    @Override
   public  void endreStørrelse(double dx, double dy){
        // Modify the endpoint coordinates to "resize" the line
        endX += dx;
        endY += dy;

        // Set the new endpoint coordinates
        this.setEndX(endX);
        this.setEndY(endY);
    }
    @Override
    public boolean isCursorOverShape(double x, double y) {
        return this.getBoundsInParent().contains(x, y);
    }


    @Override
    public double getAreal() {
        // linje har ikke en areal så returnerer 0
        return 0;
    }

    @Override
    public String getForm() {
        return "Linje";
    }
}

