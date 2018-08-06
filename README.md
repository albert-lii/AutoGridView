# AutoGridView  

![releasesvg] ![apisvg] [![license][licensesvg]][license]   

## 关于
AutoGridView使用ViewGroup实现，可以如QQ空间与微信朋友圈中的照片那种九宫格方式排布（注：此处的九宫格排列不仅仅是可以按照QQ控件那种九宫格排布，只要宫格的个数达到可以开平方的要求（1x1,2x2,3x3,4x4...），都会按照正方形排列），同时AutoGridView也可以实现普通的宫格排布方式。此外AutoGridView还可以控制显示的行数和列数，这个在需要实现 **`点击展开更多`** 和 **`点击收起`** 中还是非常有用的。AutoGridView可以设定宫格的高，也可以采用默认的高，默认的高与宫格的宽相等。
  
AutoGridView还采用了Adapter模式，使用方式就如同ListView和Adapter搭配一样简单，可以 **`自定义item`** ,更加灵活！对于item，也使用了简单的缓存复用，减少性能消耗...
  
## 推荐 
- [ImageViewer][ImageViewer] 图片预览器，仿朋友圈和今日头条图片预览效果

## 演示
先来看看效果把！  

![演示][demogif]

## 添加依赖
- 使用Gradle
```java
   // 注：如果添加依赖成功，则此句不必添加，此句作用仅为当项目在被审核时，紧急需要使用时添加
   allprojects {
       repositories {
           ...
           // 如果添加依赖时，报找不到项目时（项目正在审核），可以添加此句maven地址，如果找到项目，可不必添加
           maven { url "https://dl.bintray.com/albertlii/android-maven/" }
       }
    }
    
    dependencies {
         compile 'com.liyi.view:auto-gridview:1.1.0'
    }
```
- 使用Maven
```java
   <dependency>
      <groupId>com.liyi.view</groupId>
      <artifactId>auto-gridview</artifactId>
      <version>1.1.0</version>
      <type>pom</type>
   </dependency>
```

## 自定义属性
- agv_mode（网格图的模式，默认为 nine）  
  - nine（类似QQ空间、微信朋友圈的九宫格类型网格图）
  - normal（依次显示网格的普通类型网格图）  
 
- agv_row（item 的行数，默认为 3）
- agv_column（item 的列数，默认 为3）
- agv_item_height（item 的高度，默认与宫格图的宽相等）
- agv_horizontal_space（网格间的横向间距，默认为 10px）
- agv_vertical_space（网格间的纵向间距，默认为 10px）
- agv_nines_widthper（九宫格模式下，单个 item 时，item 的宽占 parent 的可用总宽的比例（范围0-1），默认为自适应）
- agv_nines_heightper（九宫格模式下，单个 item 时，item 的高与 parent 的可用总宽的比（范围0-1），默认为自适应）

## 使用方法
### XML 中添加 AutoGridView
```Java
<com.liyi.grid.AutoGridView
    android:id="@+id/autoGridVi"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```
### 代码中使用 AutoGridView
#### 1、三种适配器
- [`SimpleAutoGridAdapter`][SimpleAutoGridAdapter]：简用适配器，可以直接使用，用于常规需求[demo中使用示例][SimpleAutoGridActivity]
- [`QuickAutoGridAdapter`][QuickAutoGridAdapter]：快捷适配器，继承自`BaseAutoGridAdapter`，方便快速开发[demo中使用示例][QuickAutoGridActivity]
- [`BaseAutoGridAdapter`][BaseAutoGridAdapter]：基础适配器，使用方法同`ListView与BaseAdapter` [demo中使用示例][BaseAutoGridActivity]

#### 2、简单使用
```java

例：（注：此处使用的 SimpleAutoGridAdapter）
mAdapter = new SimpleAutoGridAdapter<Integer, BaseAutoGridHolder>(mImageList);
mAdapter.setImageLoader(new SimpleAutoGridAdapter.ImageLoader<Integer>() {
    @Override
    public void onLoadImage(int position, Integer item, ImageView imageView) {
        imageView.setImageResource(item);
    }
});
autoGridVi.setAdapter(mAdapter);
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


[releasesvg]:https://img.shields.io/badge/version-1.1.0-brightgreen.svg
[apisvg]: https://img.shields.io/badge/sdk-9+-brightgreen.svg
[licensesvg]: https://img.shields.io/badge/license-Apache--2.0-blue.svg
[license]:http://www.apache.org/licenses/LICENSE-2.0
[statussvg]:https://img.shields.io/librariesio/github/phoenixframework/phoenix.svg  

[SimpleAutoGridAdapter]:https://github.com/albert-lii/AutoGridView/blob/master/auto-gridview/src/main/java/com/liyi/grid/adapter/SimpleAutoGridAdapter.java
[QuickAutoGridAdapter]:https://github.com/albert-lii/AutoGridView/blob/master/auto-gridview/src/main/java/com/liyi/grid/adapter/QuickAutoGridAdapter.java
[BaseAutoGridAdapter]:https://github.com/albert-lii/AutoGridView/blob/master/auto-gridview/src/main/java/com/liyi/grid/adapter/BaseAutoGridAdapter.java

[SimpleAutoGridActivity]:https://github.com/albert-lii/AutoGridView/blob/master/app/src/main/java/com/liyi/example/SimpleAutoGridActivity.java
[QuickAutoGridActivity]:https://github.com/albert-lii/AutoGridView/blob/master/app/src/main/java/com/liyi/example/QuickAutoGridActivity.java
[BaseAutoGridActivity]:https://github.com/albert-lii/AutoGridView/blob/master/app/src/main/java/com/liyi/example/BaseAutoGridActivity.java


[ImageViewer]:https://github.com/albert-lii/ImageViewer
[demogif]:https://github.com/albert-lii/AutoGridView/blob/master/screenshot/demo.gif
