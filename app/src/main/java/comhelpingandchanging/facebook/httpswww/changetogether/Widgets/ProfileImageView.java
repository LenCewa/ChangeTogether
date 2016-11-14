package comhelpingandchanging.facebook.httpswww.changetogether.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Yannick on 14.11.2016.
 */

public class ProfileImageView extends ImageView {


    public ProfileImageView(Context context) {
        super(context);
    }

    public ProfileImageView(Context context, AttributeSet attrs){
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        setMeasuredDimension(width, height);
    }
}
