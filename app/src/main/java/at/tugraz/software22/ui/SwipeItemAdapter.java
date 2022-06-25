package at.tugraz.software22.ui;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class SwipeItemAdapter extends BaseAdapter {
    private ArrayList<User> interestingUsers;
    private Context context;
    private UserViewModel userViewModel;
    private LifecycleOwner lifecycleOwner;

    public SwipeItemAdapter(ArrayList<User> interestingUsers, Context context, UserViewModel userViewModel, LifecycleOwner lifecycleOwner) {
        this.interestingUsers = interestingUsers;
        this.context = context;
        this.userViewModel = userViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public int getCount() {
        return interestingUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return interestingUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            Log.i("Inflator", "inflate");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_item, parent, false);
        }

        TextView interestingUserName = view.findViewById(R.id.textViewNameOfInterestingUser);
        ImageView interestingUserPicture = view.findViewById(R.id.imageViewInterestingUser);

        User user = interestingUsers.get(position);
        interestingUserName.setText(user.getUsername());
        Log.i("JOO", user.getUsername());
        if(user.getType() == UserType.NON)
            interestingUserPicture.setImageResource(R.drawable.tutorial);
        else
        {
            userViewModel.getPictureLiveData().removeObservers(lifecycleOwner);
            userViewModel.getPictureLiveData().observe(lifecycleOwner, picture -> {
                Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                if(bmp == null)
                    interestingUserPicture.setImageResource(R.drawable.default_image);
                else
                    interestingUserPicture.setImageBitmap(bmp);

            });

            if(user.getPicturePaths().isEmpty())
                interestingUserPicture.setImageResource(R.drawable.default_image);
            else
            {
                userViewModel.loadPicture(user.getPicturePaths().get(0));
            }
        }

        interestingUserPicture.setVisibility(View.VISIBLE);
        return view;
    }
}
