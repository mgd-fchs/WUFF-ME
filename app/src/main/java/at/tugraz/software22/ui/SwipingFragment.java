package at.tugraz.software22.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import at.tugraz.software22.R;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipingFragment extends Fragment {

    private static final String ARG_USERVIEWMODEL = "userViewModel";

    private UserViewModel userViewModel;

    public SwipingFragment() {
    }

    public static SwipingFragment newInstance(UserViewModel userViewModel) {
        SwipingFragment fragment = new SwipingFragment();
        fragment.userViewModel = userViewModel;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swiping, container, false);
        TextView interestingUserName = view.findViewById(R.id.textViewNameOfInterestingUser);
        ImageView interestingUserPicture = view.findViewById(R.id.imageViewInterestingUser);
        userViewModel.getNextInterestingUserLiveData().observe(getViewLifecycleOwner(), user -> {
            interestingUserName.setText(user.getUsername());
            if (!user.getPicturePaths().isEmpty()) {
                userViewModel.getPictureService().downloadPicture(user.getPicturePaths().get(0)).observe(getViewLifecycleOwner(), picture -> {
                    Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                    interestingUserPicture.setImageBitmap(Bitmap.createScaledBitmap(bmp, interestingUserPicture.getWidth(), interestingUserPicture.getHeight(), false));
                });
            }
        });

        userViewModel.loadNextInterestingUser();
        return view;
    }
}
