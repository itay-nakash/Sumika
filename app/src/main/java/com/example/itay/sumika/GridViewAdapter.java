package com.example.itay.sumika;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


public class GridViewAdapter extends BaseAdapter {

    boolean gotZero=false;
    Point pForGetView;
    final String colorP1 = "blue";
    String colorP2 = "pink";
    Cell[][] cells;
    int[][] cellsInInt;
    int numOfRowsY, numOfColumns;
    int validSpace, timeForMove;
    Context context;
    GameManager gm;
    Point pForgetView;
    private int btnSize, btnFontSize;

    GridViewAdapter(int numOfColumnsX,
                    int numOfRowsY,
                    Context context,
                    int btnSize,
                    int btnFontSize,
                    GameScreen gameScreen,
                    boolean onePlayer,
                    int compLevel,
                    int validSpace,
                    int timeForMove) {
        this.cells = new Cell[numOfColumnsX][numOfRowsY];
        this.context = context;
        this.numOfColumns = numOfColumnsX;
        this.numOfRowsY = numOfRowsY;
        this.btnFontSize = btnFontSize;
        this.btnSize = btnSize;
        this.validSpace=validSpace;
        this.timeForMove=timeForMove;
        this.cellsInInt=gameScreen.board.cellsToInt();
        for (int y = 0; y < numOfRowsY; y++) {
            for (int x = 0; x < numOfColumnsX; x++) {
                Cell c = new Cell(x, y);
                cells[x][y] = c;
            }
        }
        //TODO one player is always false here
        gm = new GameManager(colorP1, colorP2, cellsInInt, gameScreen,onePlayer,compLevel,validSpace,timeForMove);
    }

    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public int getCount() {
        int length = numOfColumns * numOfRowsY;
        return length;
    }

    @Override
    public Object getItem(int position) {
        int x, y;
        x = (position + 1) % numOfColumns;
        y = (position + 1) / numOfColumns;
        return cells[x][y];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //this getView get called 4 times for position 0, what i suspect that mess up my buttons for the comp
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View row = convertView;
        ViewHolder holder;
        //checks if the gridview is new- to inflate only once.
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_item, viewGroup, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }
        //else- using the old holder.
        else {
            //using the Tag to save the findViewById
            holder = (ViewHolder) row.getTag();
        }
            holder.btnSqure = changeBtnSize(holder.btnSqure);
            final Button btn = holder.btnSqure;//final to call in inner class.
            pForgetView = intPositionToPoint(position);
            if(!gotZero||position!=0) {
                gm.gameScreen.board.addButtonToCell(btn, pForgetView.x, pForgetView.y);
                holder.btnSqure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (gm.getUserClicks) {
                            pForGetView = intPositionToPoint(position);
                            gm.game(pForGetView);
                        }
                    }
                });
                gotZero=true;
            }
        return row;
    }

    private Point intPositionToPoint(int position) {
        Point p = new Point();
        p.x = (position) % numOfColumns;
        p.y = (position) / numOfColumns;
        return p;
    }

    public Button changeBtnSize(Button btn) {
        ViewGroup.LayoutParams params = btn.getLayoutParams();
        //Button new width in px
        params.width = btnSize;
        params.height = btnSize;
        btn.setLayoutParams(params);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnFontSize);
        return btn;
    }

    //using class holder for improving preformens. findViewById is very expensive.
    class ViewHolder {
        Button btnSqure;

        ViewHolder(View v) {
            btnSqure = v.findViewById(R.id.singleItemButton);
        }
    }
    public void refresh(Cell[][] cells,Point position)
    {
        this.cells[position.x][position.y]=cells[position.x][position.y];
        notifyDataSetChanged();
    }
}
