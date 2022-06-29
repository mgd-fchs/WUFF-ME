package at.tugraz.software22.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class SwipingFragment extends Fragment {

    private UserViewModel userViewModel;
    private SwipeDeck interestingUserSwipeDeck;
    private final ArrayList<User> userList = new ArrayList<>();
    private User currentUser;

    public SwipingFragment() {
        User tutorialUser = new User("");
        userList.add(tutorialUser);
    }

    public static SwipingFragment newInstance(User user) {
        SwipingFragment fragment = new SwipingFragment();
        fragment.currentUser = user;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getNextInterestingUserLiveData().observe(getViewLifecycleOwner(), user -> userList.add(user));

        View view = inflater.inflate(R.layout.fragment_swiping, container, false);
        interestingUserSwipeDeck = view.findViewById(R.id.users_swipe_deck);
        final SwipeItemAdapter adapter = new SwipeItemAdapter(userList, getContext(), userViewModel, getViewLifecycleOwner());
        interestingUserSwipeDeck.setAdapter(adapter);

        interestingUserSwipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Snackbar.make(getView(),getString(R.string.swipe_left_snackbar), Snackbar.LENGTH_LONG).show();
                currentUser.addLeftSwipedProfile(userList.get(position).getId());
                userViewModel.updateUser(currentUser);
                userViewModel.loadNextInterestingUser(currentUser);
            }

            @Override
            public void cardSwipedRight(int position) {
                Snackbar.make(getView(),getString(R.string.swipe_right_snackbar), Snackbar.LENGTH_LONG).show();
                currentUser.addRightSwipedProfile(userList.get(position).getId());
                userViewModel.updateUser(currentUser);
                userViewModel.loadNextInterestingUser(currentUser);
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

        userViewModel.loadNextInterestingUser(currentUser);
        return view;
    }
}
