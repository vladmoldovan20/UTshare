package moldovan.vlad.utshare.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladu on 11/10/2017.
 */

/**
 * Class that stores fragments for tabs
 */

public class SectionPagerAdaper extends FragmentPagerAdapter{
    private static final String TAG= "SectionPagerAdapter";

    private final List<Fragment> mFragmentList= new ArrayList<>();

    public SectionPagerAdaper(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);

    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
