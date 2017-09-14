# AutoGridView
AutoGridView主要使用ViewGroup实现，可以如QQ空间中的照片那种九宫格方式排布（注：此处的九宫格排列不仅仅是可以按照QQ控件那种九宫格排布，只要宫格的个数
达到可以开平方的要求（1x1,2x2,3x3,4x4...），都会按照正方形排列），同时AutoGridView也可以实现普通的宫格排布方式。此外AutoGridView还可以控制显示的
行数和列数，这个在 **`点击展开更多`** 和 **`点击收起`** 中还是非常有用的。AutoGridView可以设定宫格的高，也可以采用默认的高，默认的高与宫格的宽相等。AutoGridView
还采用了Adapter模式，使用方式就如同ListView和Adapter一样简单，可以 **`自定义item`** ,更加灵活！对与item，也使用了简单的缓存复用，减少性能消耗...
 
## 联系方式
> 电子邮箱：albertlii@163.com
  
## 推荐 
- [FlowView](https://github.com/albert-lii/FlowView) 功能齐全的流布局
- [SUtils](https://github.com/albert-lii/SUtils) 轻量的常用的工具类库

## 演示
先来看看效果把！ 
![演示](https://github.com/albert-lii/AutoGridView/blob/master/screenshot/auto_gridview.gif)  
录制的gif不是很流畅，小伙伴们可以在自己的机器上运行一下试试效果！  
> **博客详情链接：http://blog.csdn.net/liyi1009365545/article/details/77964179**

## 添加依赖
```Java
Step 1:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

Step 2:

    dependencies {
            compile 'com.github.albert-lii:AutoGridView:1.0.0'
    }
```

## 使用方法
### XML
```Java
    <com.liyi.autogrid.AutoGridView
       android:id="@+id/gridLayout"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content" />
```
### 代码实现
```Java
// 必须继承BaseGridAdapter类，使用方法同ListView和Adapter，自定义item，更加灵活！
public class GridAdapter extends BaseGridAdapter {
    private ArrayList<Integer> mList;
    private boolean isNineGrid;

    public void setData(ArrayList<Integer> list, boolean isNineGrid) {
        this.mList = list;
        this.isNineGrid = isNineGrid;
    }
    
    // 如果想要Item的样式不一样，必须在此处区分不同样式的Item，类似于ListView的BaseAdpater的     
    // getViewType()方法（我此处是区分九宫格模式时当只有一个Picture和多个Picture时的样式）
    @Override
    public int getViewType(int position) {
       ...
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ...
        int type = getViewType(position);
        if (convertView == null) {
            if (type == 0) {
               ...
            } else {
               ...
            }
        } else {
            if (type == 0) {
               ...
            } else {
               ...
            }
        }
        if (type == 0) {
           ...
        } else {
           ...
        }
        return convertView;
    }

    private class ItemHolder {
        ...
    }
}


 // item的点击事件
 gridLayout.setOnItemClickListener(new GridLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(GridActivity.this, "我是" + position + "号", 
                Toast.LENGTH_SHORT).show();
            }
        });


   // 直接设置Adapter即可（第一次可以需要setAdapter(),后续更新数据可以直接使用notifyChanged()方法）
   gridLayout.setAdapter(mAdapter);
   
   
   // 更新数据
   mAdapter.setData(mList, false);
   // 更新数据（与setAdapter(mAdapter)不同的是，notifyChanged()方法实现了Item的简单复用）
   gridLayout.notifyChanged();
```

## 赞赏
如果你感觉 `AutoGridView` 帮助到了你，可以点右上角 "Star" 支持一下 谢谢！ ^_^

## LICENSE
Copyright 2017 liyi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
