# AutoGridView  

![jcentersvg] ![releasesvg] ![apisvg] [![license][licensesvg]][license]   

## 关于
AutoGridView主要使用ViewGroup实现，可以如QQ空间中的照片那种九宫格方式排布（注：此处的九宫格排列不仅仅是可以按照QQ控件那种九宫格排布，只要宫格的个数
达到可以开平方的要求（1x1,2x2,3x3,4x4...），都会按照正方形排列），同时AutoGridView也可以实现普通的宫格排布方式。此外AutoGridView还可以控制显示的
行数和列数，这个在 **`点击展开更多`** 和 **`点击收起`** 中还是非常有用的。AutoGridView可以设定宫格的高，也可以采用默认的高，默认的高与宫格的宽相等。AutoGridView
还采用了Adapter模式，使用方式就如同ListView和Adapter一样简单，可以 **`自定义item`** ,更加灵活！对与item，也使用了简单的缓存复用，减少性能消耗...
  
## 推荐 
- [SUtils][SUtils] 轻量的常用的工具类库
- [FlowView][FlowView] 功能齐全的流布局

## 演示
先来看看效果把！  

![演示][demogif]

录制的gif不是很流畅，小伙伴们可以在自己的机器上运行一下试试效果！  
> **博客详情链接：http://blog.csdn.net/liyi1009365545/article/details/78135582**

## 添加依赖
```java
    dependencies {
         compile 'com.liyi:auto-gridview:1.0.1'
    }
```

## 自定义属性
- grid_mode（网格图的模式）  
  - nine（类似QQ空间、微信朋友圈的九宫格类型网格图）
  - normal（依次显示网格的普通类型网格图）  
 
- grid_row（网格的行数）
- grid_column（网格的列数）
- grid_height（网格的高度）
- grid_hspace（网格间的横向间距）
- grid_vspace（网格间的纵向间距）
- grid_onewper（九宫格模式下，单个 item 时，item 的宽占 parent 的可用总宽的比例（范围0-1））
- grid_onehper（九宫格模式下，单个 item 时，item 的高与 viewGroup 的可用总宽的比（范围0-1））

## 使用方法
### XML
```Java
    <com.liyi.view.AutoGridView
       android:id="@+id/gridLayout"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content" />
```
### 代码实现
```java
1、直接使用BaseAdapter适配器（注：就像ListView与BaseAdapter那样配合使用）
2、设置适配器：gridLayout.setAdapter(mAdapter);

例：
// 直接设置Adapter即可（第一次可以需要setAdapter(),后续更新数据可以直接使用AutoGridView的notifyChanged()方法）
gridLayout.setAdapter(mAdapter);
      
// 更新数据 
mAdapter.setData(mList, false);
// 更新数据（与setAdapter(mAdapter)不同的是，notifyChanged()方法实现了Item的简单复用）
gridLayout.notifyChanged();
   
// item的点击事件
gridLayout.setOnItemClickListener(new GridLayout.OnItemClickListener() {
           @Override
           public void onItemClick(int position, View view) {
               Toast.makeText(GridActivity.this, "我是" + position + "号", 
               Toast.LENGTH_SHORT).show();
           }
       });
```

## 赞赏
如果你感觉 `AutoGridView` 帮助到了你，可以点右上角 "Star" 支持一下 谢谢！:blush:

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


[jcentersvg]: https://img.shields.io/badge/Jcenter-1.0.6-brightgreen.svg
[releasesvg]: https://img.shields.io/badge/release-v1.0.6-0f80c1.svg
[apisvg]: https://img.shields.io/badge/API-9+-brightgreen.svg
[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-0f80c1.svg
[license]:http://www.apache.org/licenses/LICENSE-2.0
[statussvg]:https://img.shields.io/librariesio/github/phoenixframework/phoenix.svg  

[SUtils]:https://github.com/albert-lii/SUtils
[FlowView]:https://github.com/albert-lii/FlowView
[demogif]:https://github.com/albert-lii/AutoGridView/blob/master/screenshot/auto_gridview.gif
