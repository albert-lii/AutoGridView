package com.liyi.grid.model;


public class AutoGridParamBean {
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

    public AutoGridParamBean() {
    }

    public AutoGridParamBean(int gridType, int gridRow, int gridColumn, float gridWidth, float gridHeight,
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

    public AutoGridParamBean setGridType(int gridType) {
        this.gridType = gridType;
        return this;
    }

    public int getGridRow() {
        return gridRow;
    }

    public AutoGridParamBean setGridRow(int gridRow) {
        this.gridRow = gridRow;
        return this;
    }

    public int getGridColumn() {
        return gridColumn;
    }

    public AutoGridParamBean setGridColumn(int gridColumn) {
        this.gridColumn = gridColumn;
        return this;
    }

    public float getGridWidth() {
        return gridWidth;
    }

    public AutoGridParamBean setGridWidth(float gridWidth) {
        this.gridWidth = gridWidth;
        return this;
    }

    public float getGridHeight() {
        return gridHeight;
    }

    public AutoGridParamBean setGridHeight(float gridHeight) {
        this.gridHeight = gridHeight;
        return this;
    }

    public float getGridHspace() {
        return gridHspace;
    }

    public AutoGridParamBean setGridHspace(float gridHspace) {
        this.gridHspace = gridHspace;
        return this;
    }

    public float getGridVspqce() {
        return gridVspqce;
    }

    public AutoGridParamBean setGridVspqce(float gridVspqce) {
        this.gridVspqce = gridVspqce;
        return this;
    }

    public float getGridOneWper() {
        return gridOneWper;
    }

    public AutoGridParamBean setGridOneWper(float gridOneWper) {
        this.gridOneWper = gridOneWper;
        return this;
    }

    public float getGridOneHper() {
        return gridOneHper;
    }

    public AutoGridParamBean setGridOneHper(float gridOneHper) {
        this.gridOneHper = gridOneHper;
        return this;
    }

    public int getItemCount() {
        return itemCount;
    }

    public AutoGridParamBean setItemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public AutoGridParamBean setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }
}
