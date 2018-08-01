package com.liyi.grid.model;


public class SizeInfo {
    private int childRow;
    private int childColumn;
    private float childWidth;
    private float childHeight;
    private float parentWidth;
    private float parentHeight;

    public SizeInfo() {
        super();
    }

    public SizeInfo(int childRow, int childColumn, float childWidth, float childHeight, float parentWidth, float parentHeight) {
        this.childRow = childRow;
        this.childColumn = childColumn;
        this.childWidth = childWidth;
        this.childHeight = childHeight;
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
    }

    public int getChildRow() {
        return childRow;
    }

    public void setChildRow(int childRow) {
        this.childRow = childRow;
    }

    public int getChildColumn() {
        return childColumn;
    }

    public void setChildColumn(int childColumn) {
        this.childColumn = childColumn;
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
