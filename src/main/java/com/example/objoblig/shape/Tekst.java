package com.example.objoblig.shape;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tekst extends Text implements Form {
    String textField;
    double x;
    double y;
    private Color farge;

    public Tekst(String textfield, double x, double y, Color farge) {
        this.textField=textfield;
        this.farge = farge;
        this.x = x;
        this.y = y;
    }

    @Override
    public  void tegn(Pane g) {
        this.setText(textField);
        this.setFill(farge);
        this.setStroke(farge);
        this.setFont(Font.font(20));
        this.setY(y);
        this.setX(x);
        g.getChildren().add(this);
    }
    @Override
    public void endreStørrelse(double dx, double dy){

    }

    @Override
    public double getAreal() {
        return 0;
    }

    @Override
    public String getForm() {
        return "Tekst";
    }

    @Override
    public void setFarge(Color farge) {
        this.farge = farge;
    }

    @Override
    public void flytt(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.setX(dx);
        this.setY(dy);

    }
    @Override
    public Color getFarge(){
        return farge;
    }
    @Override
    public boolean isCursorOverShape(double x, double y) {
        return this.getBoundsInParent().contains(x, y);
    }
}
