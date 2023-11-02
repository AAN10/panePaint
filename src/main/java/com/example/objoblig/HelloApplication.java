package com.example.objoblig;

import com.example.objoblig.shape.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.objoblig.shape.Form;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application {

    private Form[] former = new Form[500];
    private int antFormer = 0;
    private Color defaultFarge = Color.WHITE;
    private Pane canvasPane = new Pane();
    ColorPicker fargeVelger = new ColorPicker();
    private BorderPane root;


    // disse to prev-variabler tar vare på x- og y koordinatane til datamusa
    private double forDrattX;
    private double forDrattY;

    private MouseEvent dragged;
    //figuren som blir dratt(null hvis ingen)
    private Form figurBlirDratt = null;
    private double startY;
    private double startX;
    private double endX;
    private double endY;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //canvasPane = new Pane();
        canvasPane = lagCanvas();
        StackPane canvasHolder = new StackPane(canvasPane);
        canvasHolder.setStyle("-fx-border-width: 2px; -fx-border-color: #444");
        BorderPane root = new BorderPane(canvasHolder);
        root.setStyle("-fx-border-width: 1px; -fx-border-color: black");
        root.setLeft(verktøylinje(canvasPane));
        root.setRight(sidePanel(canvasPane));
        stage.setTitle("PaintProgram");
        Scene scene = new Scene(root, 1500, 1200);
        stage.setScene(scene);
        stage.show();


    }


    //denne metoden lager en canvas og legger til muselyttere for å implementere dra og slipp
    private Pane lagCanvas() {
        canvasPane.setOnMousePressed(this::musTrykket);
        canvasPane.setOnMouseReleased(this::musSluppet);
        canvasPane.setOnMouseDragged(this::musDragget);
        //canvasPane.setOnMouseEntered(this::nyStørrelse);
        canvasPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::FargeBytter);
        return canvasPane;
    }


    // Her lages en verktøylinjen som ligger på høyre sidean av programmet.
    // Radioknappene settes inn i en togglegroup for å hindrer at mer enn et figur  blir valgt.
    private VBox verktøylinje(Pane canvasPane) {
        VBox verktøylinje = new VBox(10);
        verktøylinje.setStyle("-fx-padding: 30px; -fx-background-color: #d3d3d3");
        ToggleGroup toggleFigurer = new ToggleGroup();
        RadioButton btnSirkel = new RadioButton("Sirkel");
        RadioButton btnRektangel = new RadioButton("Rektangel");
        RadioButton btnLinje = new RadioButton("Linje");
        RadioButton btnTekst = new RadioButton("Tekst");
        RadioButton btnOval = new RadioButton("Oval");
        TextField tfTekst = new TextField();
        tfTekst.setPromptText("Hva vil du skrive?");
        verktøylinje.getChildren().addAll(
                btnSirkel,
                btnRektangel,
                btnOval,
                btnLinje,
                btnTekst,
                tfTekst,
                fargeVelger
        );


        //dette setter knappene i en toggle group og legger til en lytter som lar brukeren lage en figur
        //btnSirkel.setCursor(Cursor.HAND);
        //btnRektangel.setCursor(Cursor.HAND);
        btnSirkel.setToggleGroup(toggleFigurer);
        btnRektangel.setToggleGroup(toggleFigurer);
        btnLinje.setToggleGroup(toggleFigurer);
        btnTekst.setToggleGroup(toggleFigurer);
        btnOval.setToggleGroup(toggleFigurer);

        toggleFigurer.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            Linje linje = new Linje();

            canvasPane.setOnMouseClicked(event -> {
                System.out.println("Mouse clicked on canvas.");
                defaultFarge = fargeVelger.getValue();


                if (btnSirkel.isSelected() && event.isControlDown()) {
                    leggTilFigur(new Sirkel(event.getX(), event.getY(), 50.0, fargeVelger.getValue()));
                    System.out.println("Sirkel");
                }

                if (btnTekst.isSelected() && event.isControlDown()) {
                    String textContent = tfTekst.getText();
                    if (!textContent.isEmpty()) {
                        Tekst textShape = new Tekst(textContent, event.getX(), event.getY(), fargeVelger.getValue());
                        leggTilFigur(textShape);
                        System.out.println("Tekst");
                    }
                }

                if (btnRektangel.isSelected() && event.isControlDown()) {
                    leggTilFigur(new Rektangel(event.getX(), event.getY(), fargeVelger.getValue()));
                    System.out.println("Rectangle");
                }
                if(btnOval.isSelected() && event.isControlDown()){
                    leggTilFigur(new Oval(event.getX(), event.getY(), 50, fargeVelger.getValue()));
                    System.out.println("Oval");
                }


            });
            canvasPane.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                if (btnLinje.isSelected() && e.isControlDown()) {
                    linje.setStartX(e.getX());
                    linje.setStartY(e.getY());
                }
            });
            canvasPane.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                if (btnLinje.isSelected() && e.isControlDown()) {
                    endX = e.getX();
                    endY = e.getY();
                    linje.setEndX(endX);
                    linje.setEndY(endY);
                    leggTilFigur(new Linje(linje.getStartX(), linje.getStartY(), linje.getEndX(), linje.getEndY(), fargeVelger.getValue()));

                }
            });

        }));


        return verktøylinje;

    }


    //sidepanelet som viser informasjon om hva slag figur, positon og areal
    private VBox sidePanel(Pane canvasPane) {
        VBox sidePanel = new VBox(10);
        sidePanel.setStyle("-fx-padding: 30px; -fx-background-color: #d3d3d3");
        Label typeLabel = new Label("Type figur: ");
        Label arealLabel = new Label("Areal: ");
        Label XYPos = new Label("XYPos: ");
        Label fargeLabel = new Label("farge: ");


        canvasPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (figurBlirDratt != null) {
                typeLabel.setText("Type figur: " + figurBlirDratt.getForm());
                arealLabel.setText(String.format("Areal: %.2f", (figurBlirDratt.getAreal() / 1000)) + " cm");
                XYPos.setText("XYPos: " + e.getX() + ", " + e.getY());
                fargeLabel.setText("farge: " + figurBlirDratt.getFarge().toString());
            }
        });

        sidePanel.getChildren().addAll(typeLabel, arealLabel, XYPos, fargeLabel);
        return sidePanel;
    }


    //tegner alle figurene på nytt når brukeren har laget en ny figur
    //eller når brukeren har flyttet på en figur
    private void tegnCanvas() {
        canvasPane.getChildren().clear(); // Clear the previous shapes
        for (int i = 0; i < antFormer; i++) {
            Form f = former[i];
            f.tegn(canvasPane);
        }
    }

    //med denne metoden skal man kunne bytte farge etter å ha laget den
    private void FargeBytter(MouseEvent e) {
        List<Form> formList = Arrays.stream(former).toList();
        formList.stream()
                .filter(this::isFigurNotNull)
                .filter(f -> f.isCursorOverShape((int) e.getX(), (int) e.getY()))
                .findFirst()
                .ifPresent(f -> f.setFarge(fargeVelger.getValue()));
        tegnCanvas();


    }


    private boolean isFigurNotNull(Form form) {
        return form != null;
    }

    //denne metoden lar brukeren trykke på figuren og flytte den
    private void musTrykket(MouseEvent evt) {
        int x = (int) evt.getX();
        int y = (int) evt.getY();

        if (evt.isShiftDown()) {
            // Shift+LeftClick: Move shape to the front
            if (evt.isPrimaryButtonDown()) {
                for (int i = antFormer - 1; i >= 0; i--) {
                    Form f = former[i];
                    if (f.isCursorOverShape(x, y)) {
                        if (antFormer - 1 - i >= 0) {
                            System.arraycopy(former, i + 1, former, i, antFormer - 1 - i);
                            former[antFormer - 1] = f;
                            tegnCanvas();
                            evt.consume();
                        }
                        return;
                    }
                }
            }
            // Shift+RightClick: Move shape to the back
            else if (evt.isSecondaryButtonDown()) {
                for (int i = 0; i < antFormer; i++) {
                    Form f = former[i];
                    if (f.isCursorOverShape(x, y)) {
                        if (i > 0) {
                            System.arraycopy(former, 0, former, 1, i);
                            former[0] = f;
                            tegnCanvas();
                            evt.consume();
                        }
                        return;
                    }
                }
            }
        }

        // No shift key pressed, handle normal selection and dragging
        for (int i = antFormer - 1; i >= 0; i--) {
            Form f = former[i];
            if (f.isCursorOverShape(x, y)) {
                figurBlirDratt = f;
                forDrattY = y;
                forDrattX = x;
                evt.consume();
                return;
            }
        }
    }

    private MouseEvent getMouseEvent(MouseEvent e) {
        return e;
    }

    //metoden som lar brukeren flytte på figurene og endre størrelsen på dem
    private void musDragget(MouseEvent evt) {
        if (evt.isPrimaryButtonDown()) {
            double x = evt.getX();
            double y = evt.getY();
            System.out.println("Mouse Pressed: x=" + x + ", y=" + y);
            List<Form> formList = Arrays.stream(former).toList();

            formList.stream()
                    .filter(f -> f != null)
                    .filter(f -> f.isCursorOverShape(evt.getX(), evt.getY()))
                    .findFirst()
                    .ifPresent(f -> {
                        if (evt.isAltDown()) {

                            f.endreStørrelse(x - forDrattX, y - forDrattY);


                        } else {

                            f.flytt(x - forDrattX, y - forDrattY);

                        }
                    });
            forDrattY = y;
            forDrattX = x;
            tegnCanvas(); // Redraw the canvas to show the shapes in their new positions.
        }
    }

    //denne metoden lar brukeren slippe figuren og ta figurBlirDratt går tilbake til null
    private void musSluppet(MouseEvent evt) {
        figurBlirDratt = null;
    }


    //metoden som lar brukeren legge til en figur
    private void leggTilFigur(Form form) {
        form.setFarge(defaultFarge);
        former[antFormer] = form;
        antFormer++;
        tegnCanvas();
    }


    // denne figuren hjelper med å endre størrlsen på figuren
    public void nyStørrelse(MouseEvent scrollEvent) {
        double x = scrollEvent.getX();
        double y = scrollEvent.getY();
        if (figurBlirDratt != null) {
            double deltaX = x - forDrattX;
            double deltaY = y - forDrattY;
            figurBlirDratt.endreStørrelse(deltaX, deltaY);
            forDrattX = x;
            forDrattY = y;
            tegnCanvas(); // tegner canvas på nytt for å vise figuren i ny størrelse
        }

    }

}