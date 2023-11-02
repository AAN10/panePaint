package com.example.objoblig.shape;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.w3c.dom.Node;

public interface Form {

    void tegn(Pane g);

    double getAreal();

    String getForm();

   //  int getVenstre();

 //   int getTopp();
    void setFarge(Color farge);
    Color getFarge();

    void flytt(double dx, double dy);

    public void endreStørrelse(double dx, double dy);

   public boolean isCursorOverShape(double x, double y);
   //{
//        if (x >= venstre && x < venstre + bredde && y >= topp && y < topp + høyde)
//            return true;
//        else
//            return false;
//    }



   //void omform();


}


