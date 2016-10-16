package com.davidecirillo.multichoicesample;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

import com.davidecirillo.multichoicesample.SampleCustomView.SampleCustomActivity;
import com.davidecirillo.multichoicesample.sampleImageGallery.GalleryActivity;
import com.davidecirillo.multichoicesample.sampleToolbar.SampleToolbarActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Override
    public int setActivityIdentifier() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.sample_custom_view)
    public void customView(){

        startActivity(new Intent(this, SampleCustomActivity.class));
    }

    @OnClick(R.id.sample_toolbar)
    public void toolbar(){

        startActivity(new Intent(this, SampleToolbarActivity.class));
    }

    @OnClick(R.id.sample_gallery)
    public void gallery(){

        startActivity(new Intent(this, GalleryActivity.class));
    }

    @OnClick(R.id.github_button)
    public void go_github(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/dvdciri/MultiChoiceRecyclerView"));
        startActivity(browserIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.contact_me){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, "dvd.ciri@gmail.com");

            startActivity(Intent.createChooser(intent, "Send Email"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
