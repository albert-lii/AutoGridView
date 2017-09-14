package com.liyi.autogrid.model;


public class GridResultBean {
    private int childRows;
    private int childColumns;
    private float childWidth;
    private float childHeight;
    private float parentWidth;
    private float parentHeight;

    public GridResultBean() {
        super();
    }

    public GridResultBean(int childRow, int childColumn, float childWidth, float childHeight, float parentWidth, float parentHeight) {
        this.childRows = childRow;
        this.childColumns = childColumn;
        this.childWidth = childWidth;
        this.childHeight = childHeight;
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
    }

    public int getChildRows() {
        return childRows;
    }

    public void setChildRows(int childRows) {
        this.childRows = childRows;
    }

    public int getChildColumns() {
        return childColumns;
    }

    public void setChildColumns(int childColumns) {
        this.childColumns = childColumns;
    }

    public float getChildWidth() {
        return childWidth;
    }

    public void setChildWidth(float childWidth) {
        this.childWidth = childWidth;
    }

    public float getChildHeight() {
        return childHeight;
    }

    public void setChildHeight(float childHeight) {
        this.childHeight = childHeight;
    }

    public float getParentWidth() {
        return parentWidth;
    }

    public void setParentWidth(float parentWidth) {
        this.parentWidth = parentWidth;
    }

    public float getParentHeight() {
        return parentHeight;
    }

    public void setParentHeight(float parentHeight) {
        this.parentHeight = parentHeight;
    }
}
