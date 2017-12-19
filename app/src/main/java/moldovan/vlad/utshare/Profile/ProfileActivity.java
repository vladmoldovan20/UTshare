package moldovan.vlad.utshare.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import moldovan.vlad.utshare.R;
import moldovan.vlad.utshare.Utils.BottomNavigationViewHelper;
import moldovan.vlad.utshare.Utils.GridImageAdapter;
import moldovan.vlad.utshare.Utils.UniversalImageLoader;

/**
 * Created by vladu on 11/8/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM=4;
    private static final int NUM_GRID_COLUMNS=3;

    private Context mContext = ProfileActivity.this;
    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");

        init();


//        setupAcitivityWidgets();
//        setupBottomNavigationView();
//        setupToolbar();
//        setProfileImage();
//        tempGridSetup();
    }

    private void init(){
        Log.d(TAG,"init:inflating"+getString(R.string.profile_fragment));

        ProfileFragment fragment=new ProfileFragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }
//
//    private void tempGridSetup(){
//        ArrayList<String> imgURLs=new ArrayList<>();
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//        imgURLs.add("https://www.w3schools.com/w3css/img_fjords.jpg");
//
//        setupImageGrid(imgURLs);
//    }
//    //video 15
//    private void setupImageGrid(ArrayList<String> imgURLs){
//        GridView gridView=(GridView) findViewById(R.id.gridView);
//        int gridWidth= getResources().getDisplayMetrics().widthPixels;
//        int imageWidth=gridWidth/NUM_GRID_COLUMNS;
//        gridView.setColumnWidth(imageWidth);
//
//        GridImageAdapter adapter= new GridImageAdapter(mContext, R.layout.layout_grid_imageview,"",imgURLs);
//        gridView.setAdapter(adapter);
//
//    }
//
//    private void setProfileImage(){
//        Log.d(TAG,"setProfileImage:setting profile image");
//        String imgURL = "https://theinfoblog.com/wp-content/uploads/2017/07/ac-lloyd-2.jpg";
//        UniversalImageLoader.setImage(imgURL,profilePhoto,mProgressBar,"");
//    }
//
//    private void setupAcitivityWidgets(){
//        mProgressBar=(ProgressBar) findViewById(R.id.profileProgressBar);
//        mProgressBar.setVisibility(View.GONE);
//        profilePhoto=(ImageView) findViewById(R.id.profile_photo);
//
//    }
//    /**
//     * Responsible for setting up the profile toolbar
//     */
//    private void setupToolbar(){
//        Toolbar toolbar=(Toolbar) findViewById(R.id.profileToolBar);
//        setSupportActionBar(toolbar);
//
//        ImageView profileMenu=(ImageView) findViewById(R.id.profileMenu);
//        profileMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: navigation to account settings");
//                Intent intent=new Intent(mContext,AccountSettingsActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }
//    /**
//     * BottomNavigationView setup
//     */
//    private void setupBottomNavigationView(){
//        Log.d(TAG,"setupBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//    }
//
//
   }

