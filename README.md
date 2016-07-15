[ ![Download](https://api.bintray.com/packages/dvd-ciri/maven/MultiChoiceRecyclerView/images/download.svg) ](https://bintray.com/dvd-ciri/maven/MultiChoiceRecyclerView/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MultiChoiceRecyclerView-green.svg?style=true)](https://android-arsenal.com/details/1/3755)

Develop: [![CircleCI](https://circleci.com/gh/dvdciri/MultiChoiceRecyclerView/tree/develop.svg?style=shield)](https://circleci.com/gh/dvdciri/MultiChoiceRecyclerView/tree/develop)

Mester: [![CircleCI](https://circleci.com/gh/dvdciri/MultiChoiceRecyclerView/tree/master.svg?style=shield)](https://circleci.com/gh/dvdciri/MultiChoiceRecyclerView/tree/master)

# Multichoice RecylerView

##Sample
<a href='https://play.google.com/store/apps/details?id=com.davidecirillo.multichoicerecyclerview&hl=en_GB&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img width='150' alt='Get it on Google Play' src='https://play.google.com/intl/en_gb/badges/images/generic/en_badge_web_generic.png'/></a>

Donwload the sample all on the Google Play Store

##Preview
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example0.png" width="210">
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example1.png" width="210">
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example2.png" width="210">
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example4.png" width="210">


##Description
This libray make life easy when you have to deal with a multi choice selection on recycler view.

##Implementation
The integration with Gradle is really quick, you just need the jcenter repository and the library dependecy:

```java
    repositories {
        jcenter()
    }
    ...
    
    dependencies {
        compile 'com.davidecirillo.multichoicerecyclerview:multichoicerecyclerview:1.1.5'
    }
```


##Set Up
- Add the MultiChoiceRecyclerView to your xml file
```java
    <com.davidecirillo.multichoicesample.MultiChoiceRecyclerView
        android:id="@+id/multiChoiceRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```


- Instanciate you object and connect the view
```java
    MultiChoiceRecyclerView mMultiChoiceRecyclerView = (MultiChoiceRecyclerView) findViewById(R.id.multiChoiceRecyclerView);
```


- Extend you adapter to the MultiChoiceAdapter and add it to the RecyclerView as per normal usage
```java
    public class MyAdapter extends MultiChoiceAdapter<MyViewHolder> {
    
        public MyAdapter(ArrayList<String> stringList, Context context) {
            this.mList = stringList;
            this.mContext = context;
        }
        
         @Override
         public void onBindViewHolder(MySampleToolbarViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            
            ...
         }
    }
```
**N.B.**
- Do not forget to call **super.onBindViewHolder(holder, position);** when binging the view holder
- Do not call View.OnClickListener on the "holder.itemView", override **defaultItemViewClickListener(...)** method and provide the implementation that you want

```java
    MyAdapter mySampleToolbarAdapter = new MyAdapter(mList, getApplicationContext());
    mMultiChoiceRecyclerView.setAdapter(mySampleToolbarAdapter);
```

- You can customize the activation or deactivation just overriding the setActive(View view, boolean state) method of the MultiChoiceAdapter
```java
    @Override
    public void setActive(View view, boolean state) {
        if(state){
            //do your changes
        }else{
            //reset the changes
        }
    }
```
You could then use method such as **View.findViewById(int id)** to customize your layout as you need*



##Fetures
- **Multi Choice Toolbar**
Activate and customise the multi choice toolbar provided by the library (only if using setSupportActionBar with Toolbar)
```java
    mMultiChoiceRecyclerView.setMultiChoiceToolbar(this,
                toolbar,
                getString(R.string.app_name),
                "item selected");
                
    //you can set the selected color of the toolbar and status bar as well
    mMultiChoiceRecyclerView.setMultiChoiceToolbar(this,
                toolbar,
                getString(R.string.app_name),
                "item selected",
                R.color.colorPrimaryMulti, R.color.colorPrimaryDarkMulti);
```
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example_toolbar.png" width="300">


- **Single Click Mode**
Use always single click mode (by default, to activate the multi selection mode you need a long click on the first item)
```java
    /*Setting single click mode true, the user will be able to select the first item just with a single click*/
    mMultiChoiceRecyclerView.setSingleClickMode(true);
```


- **Event Callbacks**
Use the MultiChoiceSelectionListener in order to have a callback whether an action is performed on the recyclerView
```java
    mMultiChoiceRecyclerView.setMultiChoiceSelectionListener(new MultiChoiceSelectionListener() {
            @Override
            public void OnItemSelected(int selectedPosition, int itemSelectedCount, int allItemCount) {
                
            }

            @Override
            public void OnItemDeselected(int deselectedPosition, int itemSelectedCount, int allItemCount) {

            }

            @Override
            public void OnSelectAll(int itemSelectedCount, int allItemCount) {

            }

            @Override
            public void OnDeselectAll(int itemSelectedCount, int allItemCount) {

            }
    });
```


###Other Methods
- public int getAllItemCount()
- public int getSelectedItemCount()
- public Collection<Integer> **getSelectedItemList()**
- public boolean select(int position)
- public boolean **selectAll()**
- public boolean deselectAll()
- public void setRecyclerRowNumber(int rowNumber)
- public void **setRecyclerColumnNumber(int columnNumber)**
- public void **setMultiChoiceToolbar(AppCompatActivity appCompatActivity,
                                      Toolbar toolbar,
                                      String defaultToolbarTitle,
                                      String selectionToolbarTitle,
                                      int selectedPrimaryColor,
                                      int selectedPrimaryColorDark)**
- public void **setSingleClickMode(boolean set)**


##License
```
    Copyright (c) 2014 Davide Cirillo
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    Come on, don't tell me you read that.
```

