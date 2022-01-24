package View;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Clicking {

    private Pane pane;
    private ImageView imageView;
    private int indexRow;
    private int indexColumn;


    private final String tableBoard[][] = {
            {" ", "X", " ", "X", " ", "X", " ", "X"},
            {"X", " ", "X", " ", "X", " ", "X", " "},
            {" ", "X", " ", "X", " ", "X", " ", "X"},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {"O", " ", "O", " ", "O", " ", "O", " "},
            {" ", "O", " ", "O", " ", "O", " ", "O"},
            {"O", " ", "O", " ", "O", " ", "O", " "}};

    private ImageView tableBlack[][] = {
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}};

    private ImageView tableWhite[][] = {
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}};



    Clicking() {
    }

    public Pane getPane() {

        return pane;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getIndexRow() {
        return indexRow;
    }

    public int getIndexColumn() {
        return indexColumn;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setIndexRow(int indexRow) {
        this.indexRow = indexRow;
    }

    public void setIndexColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }

    public ImageView[][] getTableBlack() {
        return tableBlack;
    }

    public void setTableBlack(ImageView[][] tableBlack) {
        this.tableBlack = tableBlack;
    }

    public ImageView[][] getTableWhite() {
        return tableWhite;
    }

    public void setTableWhite(ImageView[][] tableWhite) {
        this.tableWhite = tableWhite;
    }

    public String[][] getTableBoard() {
        return tableBoard;
    }

}
