package at.tugraz.software22.ui;
import android.util.Log;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;

public class SwipeItemAdapter extends BaseAdapter {
    private ArrayList<User> interestingUsers;
    private Context context;

    public SwipeItemAdapter(ArrayList<User> interestingUsers, Context context) {
        this.interestingUsers = interestingUsers;
        this.context = context;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_item, parent, false);
        }

        TextView interestingUserName = view.findViewById(R.id.textViewNameOfInterestingUser);
        ImageView interestingUserPicture = view.findViewById(R.id.imageViewInterestingUser);
        interestingUserName.setText(interestingUsers.get(position).getUsername());
        interestingUserPicture.setImageResource(R.drawable.default_image);
        interestingUserPicture.setVisibility(View.VISIBLE);
        return view;
    }
}
