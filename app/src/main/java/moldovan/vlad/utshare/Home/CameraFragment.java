package moldovan.vlad.utshare.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moldovan.vlad.utshare.R;

/**
 * Created by vladu on 11/10/2017.
 */

public class CameraFragment extends Fragment{

    private static final String TAG = "Camera Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_camera, container,false);

        return view;
    }
}
