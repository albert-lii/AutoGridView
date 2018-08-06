package com.liyi.grid.adapter;

import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 对 BaseAutoGridAdapter 封装后的快捷适配器
 */
public abstract class QuickAutoGridAdapter<T, VH extends BaseAutoGridHolder> extends BaseAutoGridAdapter<VH> {
    private static final int LAYOUT_NOT_FOUND = -404;

    private LayoutInflater mLayoutInflater;
    private SparseIntArray mLayoutTypes;
    protected List<T> mData;

    public QuickAutoGridAdapter() {

    }

    public void setData(List<T> list) {
        this.mData = list;
    }

    public List<T> getData() {
        return mData;
    }

    public void updateData(List<T> list) {
        mData = list;
        notifyDataSetChanged();
    }

    protected void addItemType(int type, @LayoutRes int layoutId) {
        if (mLayoutTypes == null) {
            mLayoutTypes = new SparseIntArray();
        }
        mLayoutTypes.put(type, layoutId);
    }

    @Override
    public int getItemViewType(int position) {
        return onHandleViewType(position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent.getContext());
        View convertView = getItemView(getLayoutId(viewType), parent);
        VH holder = createBaseViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onHandleViewHolder(holder, position, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public abstract int onHandleViewType(int position);

    public abstract void onHandleViewHolder(VH holder, int position, T item);

    protected View getItemView(int layoutResId, ViewGroup parent) {
        if (layoutResId != LAYOUT_NOT_FOUND) {
            return mLayoutInflater.inflate(layoutResId, parent, false);
        }
        return null;
    }

    private int getLayoutId(int vieType) {
        return mLayoutTypes.get(vieType, LAYOUT_NOT_FOUND);
    }

    protected VH createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        VH k;
        // 泛型擦除会导致 z 为 null
        if (z == null) {
            k = (VH) new BaseAutoGridHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (VH) new BaseAutoGridHolder(view);
    }

    @SuppressWarnings("unchecked")
    private VH createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseAutoGridHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && BaseAutoGridHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }
}
