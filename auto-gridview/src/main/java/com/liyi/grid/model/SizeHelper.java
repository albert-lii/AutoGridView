package com.liyi.grid.model;


import com.liyi.grid.AutoGridMode;
import com.liyi.grid.AutoGridView;

/**
 * 网格数据计算辅助类
 */
public class SizeHelper {

    /**
     * 计算 item 和 parent 的宽高
     *
     * @param param
     * @return
     */
    public SizeInfo calculateSize(SizeParam param) {
        if (param != null) {
            float childWidth, childHeight;
            float parentWidth, parentHeight;
            int childRow, childColumn;

            int parentRow = param.getRow();
            int parentColumn = param.getColumn();
            float parentMaxWidth = param.getMaxWidth();
            int totalCount = param.getItemCount();
            float totalHorizontalSpace = (parentColumn - 1) * param.getHorizontalSpace();
            childWidth = (parentMaxWidth - totalHorizontalSpace) / parentColumn;
            childHeight = param.getItemHeight() != AutoGridView.DEF_ITEM_HEIGHT ? param.getItemHeight() : childWidth;
            // 判断当前网格是九宫格模式还是普通模式
            // 当前模式为九宫格模式时
            if (param.getMode() == AutoGridMode.GRID_NINE) {
                // 只有一个 item 时
                if (totalCount == 1) {
                    float widthPer = param.getSingleWidthPer();
                    float heightPer = param.getSingleHeightPer();
                    childRow = 1;
                    childColumn = 1;
                    if (widthPer <= 0) {
                        childWidth = 0;
                    } else {
                        if (widthPer > 1) {
                            widthPer = 1;
                        }
                        childWidth = widthPer * parentMaxWidth;
                    }
                    if (heightPer <= 0) {
                        childHeight = 0;
                    } else {
                        childHeight = heightPer * parentMaxWidth;
                    }
                    parentWidth = childWidth;
                    parentHeight = childHeight;
                    return new SizeInfo(childRow, childColumn, childWidth, childHeight, parentWidth, parentHeight);
                } else {
                    // 如果 item 的个数大于可以显示的最大个数
                    if (parentRow * parentColumn < totalCount) {
                        childRow = parentRow;
                        childColumn = parentColumn;
                    } else {
                        // 判断 item 的个数是否可以开平方（即判断是否可以将 itemView 拼成正方形放置）
                        int temp = (int) Math.sqrt(totalCount);
                        if (temp * temp == totalCount) {
                            // 如果 item 的个数不满一行，则不做正方形放置，否则做正方形放置
                            if (totalCount <= parentColumn) {
                                childRow = 1;
                                childColumn = totalCount;
                            } else {
                                childRow = temp;
                                childColumn = temp;
                            }
                        } else {
                            childRow = totalCount / parentColumn + (totalCount % parentColumn == 0 ? 0 : 1);
                            childColumn = param.getColumn();
                        }
                    }
                }
            }
            // 当前模式为普通网格模式时
            else {
                if (parentRow * parentColumn < totalCount) {
                    childRow = parentRow;
                    childColumn = parentColumn;
                } else {
                    childRow = totalCount / parentColumn + (totalCount % parentColumn == 0 ? 0 : 1);
                    childColumn = parentColumn;
                }
            }
            parentWidth = childColumn * childWidth + (childColumn - 1) * param.getHorizontalSpace();
            parentHeight = childRow * childHeight + (childRow - 1) * param.getVerticalSpace();
            // 当只有一个 child 的时候，parent 的总宽度与 item 的宽度一致
            if (totalCount == 1) {
                parentWidth = childWidth;
            }
            return new SizeInfo(childRow, childColumn, childWidth, childHeight, parentWidth, parentHeight);
        }
        return null;
    }

    /**
     * 找到 item 在网格中的位置
     *
     * @param bean
     * @param index
     * @return
     */
    public int[] findPosition(SizeInfo bean, int index) {
        if (bean == null) {
            return null;
        }
        int[] position = new int[2];
        position[0] = index / bean.getChildColumn();
        position[1] = index - position[0] * bean.getChildColumn();
        return position;
    }
}
