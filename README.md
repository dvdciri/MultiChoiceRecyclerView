[ ![Download](https://api.bintray.com/packages/dvd-ciri/maven/MultiChoiceRecyclerView/images/download.svg) ](https://bintray.com/dvd-ciri/maven/MultiChoiceRecyclerView/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/cz.jirutka.rsql/rsql-parser)


# Multichoice RecylerView

##Preview
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example1.png" width="250">
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example2.png" width="250">
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example.png" width="250">

##Description
This library has been created to help the integration of a multi-choice selection to the RecyclerView

##Implementation
The integration with Gradle is very easy, you just need the jcenter repository and the library:

```java
    repositories {
        jcenter()
    }
    ...
    
    dependencies {
        compile 'com.davidecirillo.multichoicerecyclerview:multichoicerecyclerview:1.1.1'
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
        
        ...
    }
```
```java
    MyAdapter myAdapter = new MyAdapter(mList, getApplicationContext());
    mMultiChoiceRecyclerView.setAdapter(myAdapter);
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



##Advanced
- Set a MultiChoiceSelectionListener in order to have a callback whether an action is performed on the recyclerView
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

- Activate and customise the multi choice toolbar provided by the library (only if using setSupportActionBar with Toolbar)
```java
    mMultiChoiceRecyclerView.setMultiChoiceToolbar(this,
                toolbar,
                getString(R.string.app_name),
                "item selected");
```
<img src="https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example_toolbar.png" width="300">

###Other Methods
- public int getAllItemCount()
- public int getSelectedItemCount()
- public Collection<Integer> getSelectedItemList()
- public boolean select(int position)
- public boolean selectAll()
- public boolean deselectAll()
- public void setRecyclerRowNumber(int rowNumber)
- public void setRecyclerColumnNumber(int columnNumber)
- public void setMultiChoiceToolbar(AppCompatActivity appCompatActivity,
                                      Toolbar toolbar,
                                      String defaultToolbarTitle,
                                      String selectionToolbarTitle,
                                      int selectedPrimaryColor,
                                      int selectedPrimaryColorDark)


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

