package moldovan.vlad.utshare.Profile;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import moldovan.vlad.utshare.R;
import moldovan.vlad.utshare.Utils.FirebaseMethod;
import moldovan.vlad.utshare.Utils.UniversalImageLoader;
import moldovan.vlad.utshare.dialogs.ConfirmPasswordDialog;
import moldovan.vlad.utshare.models.User;
import moldovan.vlad.utshare.models.UserAccountSettings;
import moldovan.vlad.utshare.models.UserSettings;

/**
 * Created by vladu on 11/19/2017.
 */

public class EditProfileFragment extends Fragment implements
        ConfirmPasswordDialog.OnConfirmPasswordListener {


    @Override
    public void onConfirmPassword(String password) {
        Log.d(TAG, "onConfirmPassword: got the password");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        // Prompt the user to re-provide their sign-in credentials
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User re-authenticated.");
                            //check to see if the email is not already in the database
                            mAuth.fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        try {
                                            if (task.getResult().getProviders().size() == 1) {
                                                Log.d(TAG, "onComplete: that email is already in use");
                                                Toast.makeText(getActivity(), "That email is already used by \n another user.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d(TAG, "onComplete: that email is available");
                                                // updating the email in the database
                                                mAuth.getCurrentUser().updateEmail(mEmail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d(TAG, "User email address updated.");
                                                                    Toast.makeText(getActivity(), "You email has been changed.", Toast.LENGTH_SHORT).show();
                                                                    mFirebaseMethod.updateEmail(mEmail.getText().toString());
                                                                }
                                                            }
                                                        });
                                            }
                                        } catch (NullPointerException e) {
                                            Log.e(TAG, "onComplete: NullPointerException:" + e.getMessage());
                                        }
                                    }
                                }
                            });
                        } else {
                            Log.d(TAG, "onComplete: re-authentication failed");
                        }
                    }
                });
    }

    private static final String TAG = "EditProfileFragment";

    //EditProfileFragment Widgets

    private EditText mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;

    //variables
    private UserSettings mUserSettings;
    private User mUser;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethod mFirebaseMethod;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mDisplayName = (EditText) view.findViewById(R.id.display_name);
        mUsername = (EditText) view.findViewById(R.id.username);
        mWebsite = (EditText) view.findViewById(R.id.website);
        mDescription = (EditText) view.findViewById(R.id.description);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPhoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        mChangeProfilePhoto = (TextView) view.findViewById(R.id.changeProfilePhoto);
        mFirebaseMethod = new FirebaseMethod(getActivity());

        //  setProfileImage();
        setupFirebaseAuth();

        //back arrow for navigating to "ProfileActivity"
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow2);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        ImageView checkmark = (ImageView) view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to save changes.");
                saveProfileSettings();
            }
        });

        return view;
    }

    /**
     * Retrieves the data contained in the widget and saves it in the database
     */

    private void saveProfileSettings() {
        final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();
        final String website = mWebsite.getText().toString();
        final String description = mDescription.getText().toString();
        final String email = mEmail.getText().toString();
        final long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());


        //case1: in the user changed their username

        if (!mUserSettings.getUser().getUsername().equals(username)) {
            checkIfUsernameExists(username);
        }
        //case2: in the user changed their email
        if (!mUserSettings.getUser().getEmail().equals(email)) {
            //1)reauthenticate
            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
            dialog.setTargetFragment(EditProfileFragment.this, 1);
        }



        if (!mUserSettings.getSettings().getDisplay_name().equals(displayName)){
            //update the display name
            mFirebaseMethod.updateUserAccountSettings(displayName,null,null,0);
        }
        if (!mUserSettings.getSettings().getWebsite().equals(website)){
            //update the website
            mFirebaseMethod.updateUserAccountSettings(null,website,null,0);
        }
        if (!mUserSettings.getSettings().getDescription().equals(description)){
            //update the description
            mFirebaseMethod.updateUserAccountSettings(null,null,description,0);
        }
        if (!mUserSettings.getSettings().getProfile_photo().equals(phoneNumber)){
            mFirebaseMethod.updateUserAccountSettings(null,null,null,phoneNumber);
            //update the displayName
        }
    }


    /**
     * Check if the @param username already exists in the database
     *
     * @param username
     */

    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists:Checking if " + username + "already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    //add the username
                    mFirebaseMethod.updateUsername(username);
                    Toast.makeText(getActivity(), "Your username has been changed.", Toast.LENGTH_SHORT).show();
                }
                for (DataSnapshot singeSnaphot : dataSnapshot.getChildren()) {
                    if (singeSnaphot.exists()) {
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH" + singeSnaphot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "The username is already used.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void setProfileWidgets(UserSettings userSettings) {
//        Log.d(TAG,"setProfileWidgets:setting widgets with data from the firebase database: "+userSettings.toString());
//        Log.d(TAG,"setProfileWidgets:setting widgets with data from the firebase database: "+userSettings.getSettings().getUsername());

        mUserSettings = userSettings;
        //User user =userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(userSettings.getUser().getEmail());
        mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));

    }
 /*
    ------------------------------------------------ firebase -----------------------------
     */

    /*
    *Setup the firebase authentication object
     */

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebaseAuth");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

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
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //retrieve user info from the database
                setProfileWidgets(mFirebaseMethod.getUserSettings(dataSnapshot));

                //retrieve user images for the user info
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
