package com.liyi.autogrid.model;


import com.liyi.autogrid.AutoGridView;
import com.liyi.autogrid.GridDefine;

public class GridLogic {

    /**
     * 计算 item 和 parent 的宽高
     *
     * @param bean
     * @return
     */
    public GridResultBean calculateSize(GridParamBean bean) {
        if (bean != null) {
            float childWidth, childHeight;
            float parentWidth, parentHeight;
            int childRows, childColumns;
            int count = bean.getItemCount();
            int column = bean.getGridColumn();
            float totalHspace = (column - 1) * bean.getGridHspace();
            float maxWidth = bean.getMaxWidth();
            childWidth = (maxWidth - totalHspace) / column;
            childHeight = bean.getGridHeight() != AutoGridView.DEF_GRID_HEIGHT ? bean.getGridHeight() : childWidth;
            // 判断当前网格是九宫格模式还是普通模式
            if (bean.getGridType() == GridDefine.GRID_NINE) {
                // 只有一个 itemView 时
                if (count == 1) {
                    float wp = bean.getGridOneWper();
                    float hp = bean.getGridOneHper();
                    childRows = 1;
                    childColumns = 1;
                    if (wp <= 0) {
                        childWidth = 0;
                    } else {
                        if (wp > 1) {
                            wp = 1;
                        }
                        childWidth = wp * maxWidth;
                    }
                    if (hp <= 0) {
                        childHeight = 0;
                    } else {
                        childHeight = hp * maxWidth;
                    }
                    parentWidth = childWidth;
                    parentHeight = childHeight;
                    return new GridResultBean(childRows, childColumns, childWidth, childHeight, parentWidth, parentHeight);
                } else {
                    // 如果 itemView 的个数大于可以显示的最大个数
                    if (bean.getGridRow() * bean.getGridColumn() < count) {
                        childRows = bean.getGridRow();
                        childColumns = bean.getGridColumn();
                    } else {
                        // 判断 itemView 的个数是否可以开平方（即判断是否可以将 itemView 拼成正方形放置）
                        int temp = (int) Math.sqrt(count);
                        if (temp * temp == count) {
                            // 如果 itemView 的个数不满一行，则不做正方行放置，否则做正方形放置
                            if (count <= column) {
                                childRows = 1;
                                childColumns = count;
                            } else {
                                childRows = temp;
                                childColumns = temp;
                            }
                        } else {
                            childRows = count / column + (count % column == 0 ? 0 : 1);
                            childColumns = bean.getGridColumn();
                        }
                    }
                }
            }
            // 当前模式为普通模式时
            else {
                if (bean.getGridRow() * bean.getGridColumn() < count) {
                    childRows = bean.getGridRow();
                    childColumns = bean.getGridColumn();
                } else {
                    childRows = count / column + (count % column == 0 ? 0 : 1);
                    childColumns = column;
                }
            }
            parentWidth = childColumns * childWidth + (childColumns - 1) * bean.getGridHspace();
            parentHeight = childRows * childHeight + (childRows - 1) * bean.getGridVspqce();
            // 当只有一个 child 的时候，控件的总宽度与 child 的宽度一致
            if (count == 1) {
                parentWidth = childWidth;
            }
            return new GridResultBean(childRows, childColumns, childWidth, childHeight, parentWidth, parentHeight);
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
    public int[] findPosition(GridResultBean bean, int index) {
        if (bean == null) {
            return null;
        }
        int[] position = new int[2];
        position[0] = index / bean.getChildColumns();
        position[1] = index - position[0] * bean.getChildColumns();
        return position;
    }
}
