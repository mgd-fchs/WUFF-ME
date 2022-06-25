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
        User tutorialUser = new User("");
        userList.add(tutorialUser);
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

        userViewModel.getNextInterestingUserLiveData().observe(getViewLifecycleOwner(), user -> {
            userList.add(user);
        });

        View view = inflater.inflate(R.layout.fragment_swiping, container, false);
        interestingUserSwipeDeck = (SwipeDeck) view.findViewById(R.id.users_swipe_deck);
        final SwipeItemAdapter adapter = new SwipeItemAdapter(userList, getContext(), userViewModel, getViewLifecycleOwner());
        interestingUserSwipeDeck.setAdapter(adapter);

        interestingUserSwipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Snackbar.make(getView(),getString(R.string.swipe_left_snackbar), Snackbar.LENGTH_LONG).show();
                userViewModel.loadNextInterestingUser();
            }

            @Override
            public void cardSwipedRight(int position) {
                Snackbar.make(getView(),getString(R.string.swipe_right_snackbar), Snackbar.LENGTH_LONG).show();
                userViewModel.loadNextInterestingUser();

            }

            @Override
            public void cardsDepleted() {
                Snackbar.make(getView(),getString(R.string.swipe_depleted_snackbar), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void cardActionDown() {
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                Log.i("TAG", "CARDS MOVED UP");
            }
        });

        userViewModel.loadNextInterestingUser();
        return view;
    }
}
