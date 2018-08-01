package com.liyi.grid.model;


public class SizeParam {
    private int mode;
    private int row;
    private int column;
    private int maxWidth;
    private int itemCount;
    private float itemWidth;
    private float itemHeight;
    private float horizontalSpace;
    private float verticalSpace;
    private float singleWidthPer;
    private float singleHeightPer;

    public SizeParam() {
    }

    public SizeParam(int mode, int row, int column, int maxWidth, int itemCount,
                     float itemWidth, float itemHeight, float horizontalSpace, float verticalSpace,
                     float singleWidthPer, float singleHeightPer) {
        this.mode = mode;
        this.row = row;
        this.column = column;
        this.maxWidth = maxWidth;
        this.itemCount = itemCount;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.singleWidthPer = singleWidthPer;
        this.singleHeightPer = singleHeightPer;
    }

    public int getMode() {
        return mode;
    }

    public SizeParam setMode(int mode) {
        this.mode = mode;
        return this;
    }

    public int getRow() {
        return row;
    }

    public SizeParam setRow(int row) {
        this.row = row;
        return this;
    }

    public int getColumn() {
        return column;
    }

    public SizeParam setColumn(int column) {
        this.column = column;
        return this;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public SizeParam setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public int getItemCount() {
        return itemCount;
    }

    public SizeParam setItemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public float getItemWidth() {
        return itemWidth;
    }

    public SizeParam setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
        return this;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    public SizeParam setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
        return this;
    }

    public float getHorizontalSpace() {
        return horizontalSpace;
    }

    public SizeParam setHorizontalSpace(float horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
        return this;
    }

    public float getVerticalSpace() {
        return verticalSpace;
    }

    public SizeParam setVerticalSpace(float verticalSpace) {
        this.verticalSpace = verticalSpace;
        return this;
    }

    public float getSingleWidthPer() {
        return singleWidthPer;
    }

    public SizeParam setSingleWidthPer(float singleWidthPer) {
        this.singleWidthPer = singleWidthPer;
        return this;
    }

    public float getSingleHeightPer() {
        return singleHeightPer;
    }

    public SizeParam setSingleHeightPer(float singleHeightPer) {
        this.singleHeightPer = singleHeightPer;
        return this;
    }
}
