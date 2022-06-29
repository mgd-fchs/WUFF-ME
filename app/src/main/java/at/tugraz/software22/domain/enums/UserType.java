package at.tugraz.software22.domain.enums;

import android.content.Context;

import at.tugraz.software22.R;


public enum UserType {
    NON(0), OWNER(1), SEARCHER(2), BOTH(3);

    private int type = 0;


    UserType(int i) {
        type = i;
    }

    public String getTypeTranslation(Context c) {
        switch (UserType.values()[type]) {
            case NON:
                return c.getString(R.string.non_type);
            case OWNER:
                return c.getString(R.string.owner_type);
            case SEARCHER:
                return c.getString(R.string.searcher_type);
            case BOTH:
                return c.getString(R.string.both_type);
            default:
                return "";

        }
    }


}

