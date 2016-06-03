[ ![Download](https://api.bintray.com/packages/dvd-ciri/maven/MultiChoiceRecyclerView/images/download.svg) ](https://bintray.com/dvd-ciri/maven/MultiChoiceRecyclerView/_latestVersion)

# Multichoice RecylerView

##Preview
![Example](https://raw.githubusercontent.com/dvdciri/MultiChoiceRecyclerView/master/example.png)

##Description
This library has been created to help the integration of a multi-choice selection to the RecyclerView

##Implementation
The integration with Gradle is very easy, you just need the jcenter repository and the library:

```
    repositories {
        jcenter()
    }
    ...
    
    dependencies {
        compile 'com.davidecirillo.multichoicerecyclerview:multichoicerecyclerview:1.0.1'
    }
```


##Set Up
- Add the MultiChoiceRecyclerView to your xml file
```
    <com.davidecirillo.multichoicesample.MultiChoiceRecyclerView
        android:id="@+id/multiChoiceRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```


- Instanciate you object and connect the view
```
    MultiChoiceRecyclerView mMultiChoiceRecyclerView = (MultiChoiceRecyclerView) findViewById(R.id.multiChoiceRecyclerView);
```


- Extend you adapter to the MultiChoiceAdapter and add it to the RecyclerView as per normal usage
```
    public class MyAdapter extends MultiChoiceAdapter<MyViewHolder> {
    
        public MyAdapter(ArrayList<String> stringList, Context context) {
            this.mList = stringList;
            this.mContext = context;
        }
        
        ...
    }
```
```
    MyAdapter myAdapter = new MyAdapter(mList, getApplicationContext());
    mMultiChoiceRecyclerView.setAdapter(myAdapter);
```

##Customize
You can customize the activation or deactivation just overriding the setActive(View view, boolean state) method of the MultiChoiceAdapter
```
    @Override
    public void setActive(View view, boolean state) {
        if(state){
            //do your changes
        }else{
            //reset the changes
        }
    }
```

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

