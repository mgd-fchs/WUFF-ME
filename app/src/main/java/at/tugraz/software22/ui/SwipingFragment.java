package at.tugraz.software22.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayList;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipingFragment extends Fragment {

    private UserViewModel userViewModel;
    private SwipeDeck interestingUserSwipeDeck;
    private ArrayList<User> userList = new ArrayList<User>();

    public SwipingFragment() {
        User user1 = new User("Test1");
        User user2 = new User("Test2");
        User user3 = new User("Test3");

        // TODO: Remove!
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
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

        // TODO: Fetch first user object and add to userList!

        View view = inflater.inflate(R.layout.fragment_swiping, container, false);
        interestingUserSwipeDeck = (SwipeDeck) view.findViewById(R.id.users_swipe_deck);
        final SwipeItemAdapter adapter = new SwipeItemAdapter(userList, getContext());
        interestingUserSwipeDeck.setAdapter(adapter);

        interestingUserSwipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                Snackbar.make(getView(),"User Swiped Left", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Snackbar.make(getView(),"User Swiped Right", Snackbar.LENGTH_LONG).show();

                // TODO: Fetch new user object here!
                User user4 = new User("Test4");
                userList.add(user4);

                // TODO: Match!

            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Snackbar.make(getView(),"You swiped all interesting users!", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });
//        userViewModel.getNextInterestingUserLiveData().observe(getViewLifecycleOwner(), user -> {
//
//            interestingUserName.setText(user.getUsername());
//            if (!user.getPicturePaths().isEmpty()) {
//                View itemView = inflater.inflate(R.layout.swipe_item, container, false);
//
//                userViewModel.getPictureService().downloadPicture(user.getPicturePaths().get(0)).observe(getViewLifecycleOwner(), picture -> {
//                    Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
//                    interestingUserPicture.setImageBitmap(Bitmap.createScaledBitmap(bmp, interestingUserPicture.getWidth(), interestingUserPicture.getHeight(), false));
//                });
//                interestingUserPicture.setVisibility(View.VISIBLE);
//            } else {
//                interestingUserPicture.setImageResource(R.drawable.default_image);
//                interestingUserPicture.setVisibility(View.VISIBLE);
//            }
//        });

//        userViewModel.loadNextInterestingUser();
        return view;
    }
}
