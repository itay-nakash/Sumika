package com.example.itay.sumika;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by itay on 8/11/17.
 */

public class Board {

    final int numOfColumnsX, numOfRowsY;
    final int spaceHorizontal = 4; //space between buttons- for width calculations.
    int buttonSize;//px;
    int buttonFontSize;//px
    GridView boardGrid;
    GridViewAdapter gridViewAdapter;
    GameScreen gameScreen;
    boolean onePlayer;
    int compLevel, validSpace, timeForMove;
    Context context;
    private Cell[][] cells;

    public Board(int numOfColumnsX,
                 int numOfRowsY,
                 GridView gridView,
                 Context context,
                 int compLevel,
                 boolean onePlayer,
                 GameScreen gameScreen,
                 int validSpace,
                 int timeForMove) {
        this.numOfColumnsX = numOfColumnsX;
        this.numOfRowsY = numOfRowsY;
        this.context = context;
        this.compLevel=compLevel;
        this.validSpace=validSpace;
        this.timeForMove=timeForMove;
        this.onePlayer=onePlayer;
        this.boardGrid = gridView;
     //   this.cells = new Cell[numOfColumnsX][numOfRowsY];
        boardGrid.setNumColumns(numOfColumnsX);
        this.gameScreen=gameScreen;
    }

    public void createBoard() {
        editGeneralButtonSizeInPx();
        boardGrid = initializeGridViewWidth(boardGrid, spaceHorizontal, numOfRowsY, buttonSize);
        this.gridViewAdapter = new GridViewAdapter(numOfColumnsX,
                numOfRowsY,
                context,
                buttonSize,
                buttonFontSize,
                gameScreen,
                onePlayer,
                compLevel,
                validSpace,
                timeForMove);
        boardGrid.setAdapter(gridViewAdapter);
        cells = gridViewAdapter.getCells();
    }

    public int[][] cellsToInt(){
        int[][] cellsInInt=new int[numOfColumnsX][numOfRowsY];
        for (int y = 0; y < numOfRowsY; y++) {
            for (int x = 0; x < numOfColumnsX; x++) {
                cellsInInt[x][y] = 0;
            }
        }
        return cellsInInt;
    }

    public static int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    public void editGeneralButtonSizeInPx() {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        buttonSize = screenWidth / (numOfRowsY+1);
        buttonFontSize= buttonSize /2 ;
    }

    public void drawTheMove(Point p, int numOfClick, Player lastPlayerPlayed) {
        Button btn=cells[p.x][p.y].getButton();
        btn.setText(Integer.toString(numOfClick));
        btn.setBackgroundResource(lastPlayerPlayed.getImageId());
        cells[p.x][p.y].setButton(btn);
        gameScreen.board.gridViewAdapter.refresh(cells,p);
        gameScreen.board.boardGrid.invalidateViews();
    }

    private GridView initializeGridViewWidth(GridView boardGrid, int spaceHorizontal, int numOfColumns, int buttonSize) {
        int neWidth = numOfColumns * (spaceHorizontal);
        int neWidthPx = convertDpToPixels(neWidth, context) + buttonSize * numOfColumns;
        ViewGroup.LayoutParams layoutParams = boardGrid.getLayoutParams();
        layoutParams.width = neWidthPx; //this is in pixels
        boardGrid.setLayoutParams(layoutParams);
        boardGrid.setColumnWidth(buttonSize);
        return boardGrid;
    }

    //a function to operate
    public void addCell(int column,int row,Cell cell){
        cells[column][row]=cell;
    }

    public void addButtonToCell(Button btn,int posx,int posy){
        this.cells[posx][posy].setButton(btn);
    }
    public void setCells(Cell[][] cells){
        this.cells=cells;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
