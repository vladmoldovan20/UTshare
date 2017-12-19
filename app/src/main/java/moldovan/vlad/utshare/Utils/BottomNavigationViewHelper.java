package moldovan.vlad.utshare.Utils;



import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import moldovan.vlad.utshare.Home.HomeActivity;
import moldovan.vlad.utshare.Likes.LikesActivity;
import moldovan.vlad.utshare.Profile.ProfileActivity;
import moldovan.vlad.utshare.R;
import moldovan.vlad.utshare.Search.SearchActivity;
import moldovan.vlad.utshare.Share.ShareActivity;


/**
 * Created by vladu on 11/7/2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG ="BottomNavigationViewHelper";
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG,"setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }
        public static void enableNavigation(final Context context, BottomNavigationViewEx view){
            view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.ic_house:
                            Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM=0
                            context.startActivity(intent1);
                            break;
                        case R.id.ic_search:
                            Intent intent2 = new Intent(context, SearchActivity.class);//ACTIVITY_NUM=1
                            context.startActivity(intent2);
                            break;
                        case R.id.ic_circle:
                            Intent intent3 = new Intent(context, ShareActivity.class );//ACTIVITY_NUM=2
                            context.startActivity(intent3);
                            break;
                        case R.id.ic_alert:
                            Intent intent4 = new Intent(context, LikesActivity.class);//ACTIVITY_NUM=3
                            context.startActivity(intent4);
                            break;
                        case R.id.ic_android:
                            Intent intent5=new Intent(context, ProfileActivity.class);//ACTIVITY_NUM=4
                            context.startActivity(intent5);
                            break;
                    }

                    return false;
                }
            });
        }
}
