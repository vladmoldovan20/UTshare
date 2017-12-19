package moldovan.vlad.utshare.Profile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import moldovan.vlad.utshare.Login.LoginActivity;
import moldovan.vlad.utshare.R;

/**
 * Created by vladu on 11/19/2017.
 */

public class SignOutFragment extends Fragment {
    private static final String TAG = "SignOutFragment";

    //firebase

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private TextView tvSignout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signout, container,false);
        tvSignout=(TextView) view.findViewById(R.id.tvConfirmSignout);
        mProgressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        Button btnConfirmSignout=(Button) view.findViewById(R.id.btnConfirmSignOut);

        mProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();

            btnConfirmSignout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"onClick: attempting to sign out");
                    mProgressBar.setVisibility(View.VISIBLE);

                    mAuth.signOut();
                    getActivity().finish();
                }
            });

        return view;
    }

     /*
    ------------------------------------------------ firebase -----------------------------
     */

    /*
    *Setup the firebase authentication object
     */

    private void setupFirebaseAuth(){
        Log.d(TAG,"setupFirebaseAuth: setting up firebaseAuth");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    Log.d(TAG, "onAuthSatateChanged: navigating to login activity");

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

     /*
    ------------------------------------------------ firebase --------------------------------
     */
}
