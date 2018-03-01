package com.liyi.grid.model;


public class GridParamBean {
    private int gridType;
    private int gridRow;
    private int gridColumn;
    private float gridWidth;
    private float gridHeight;
    private float gridHspace;
    private float gridVspqce;
    private float gridOneWper;
    private float gridOneHper;
    private int itemCount;
    private int maxWidth;

    public GridParamBean() {
    }

    public GridParamBean(int gridType, int gridRow, int gridColumn, float gridWidth, float gridHeight,
                         float gridHspace, float gridVspqce, float gridOneWper, float gridOneHper, int itemCount, int maxWidth) {
        this.gridType = gridType;
        this.gridRow = gridRow;
        this.gridColumn = gridColumn;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.gridHspace = gridHspace;
        this.gridVspqce = gridVspqce;
        this.gridOneWper = gridOneWper;
        this.gridOneHper = gridOneHper;
        this.itemCount = itemCount;
        this.maxWidth = maxWidth;
    }

    public int getGridType() {
        return gridType;
    }

    public GridParamBean setGridType(int gridType) {
        this.gridType = gridType;
        return this;
    }

    public int getGridRow() {
        return gridRow;
    }

    public GridParamBean setGridRow(int gridRow) {
        this.gridRow = gridRow;
        return this;
    }

    public int getGridColumn() {
        return gridColumn;
    }

    public GridParamBean setGridColumn(int gridColumn) {
        this.gridColumn = gridColumn;
        return this;
    }

    public float getGridWidth() {
        return gridWidth;
    }

    public GridParamBean setGridWidth(float gridWidth) {
        this.gridWidth = gridWidth;
        return this;
    }

    public float getGridHeight() {
        return gridHeight;
    }

    public GridParamBean setGridHeight(float gridHeight) {
        this.gridHeight = gridHeight;
        return this;
    }

    public float getGridHspace() {
        return gridHspace;
    }

    public GridParamBean setGridHspace(float gridHspace) {
        this.gridHspace = gridHspace;
        return this;
    }

    public float getGridVspqce() {
        return gridVspqce;
    }

    public GridParamBean setGridVspqce(float gridVspqce) {
        this.gridVspqce = gridVspqce;
        return this;
    }

    public float getGridOneWper() {
        return gridOneWper;
    }

    public GridParamBean setGridOneWper(float gridOneWper) {
        this.gridOneWper = gridOneWper;
        return this;
    }

    public float getGridOneHper() {
        return gridOneHper;
    }

    public GridParamBean setGridOneHper(float gridOneHper) {
        this.gridOneHper = gridOneHper;
        return this;
    }

    public int getItemCount() {
        return itemCount;
    }

    public GridParamBean setItemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public GridParamBean setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }
}
