package com.example.student1.todolist.decorators;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;


public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int left = 0, top = 0, right = 0, bottom = 0;
    private SpacesItemDecoration(){}

    public static Builder newBuilder(Context context){
        return new SpacesItemDecoration().new Builder(context);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.set(left, top, right, bottom);
    }

    public class Builder{
        private Context context;
        private Builder(Context context){
            this.context = context;
        }

        public Builder setSameSpacesInDp(float space){
            int spaceInPx = convertDpToPixel(space);
            SpacesItemDecoration.this.left = spaceInPx;
            SpacesItemDecoration.this.top = spaceInPx;
            SpacesItemDecoration.this.right = spaceInPx;
            SpacesItemDecoration.this.bottom = spaceInPx;
            return this;
        }

        public Builder setLeftInDp(float left){
            SpacesItemDecoration.this.left = convertDpToPixel(left);
            return this;
        }

        public Builder setTopInDp(float top){
            SpacesItemDecoration.this.top = convertDpToPixel(top);
            return this;
        }

        public Builder setRightInDp(float right){
            SpacesItemDecoration.this.right = convertDpToPixel(right);
            return this;
        }

        public Builder setBottomInDp(float bottom){
            SpacesItemDecoration.this.bottom = convertDpToPixel(bottom);
            return this;
        }

        public SpacesItemDecoration build(){
            return SpacesItemDecoration.this;
        }

        private int convertDpToPixel(float dp){
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return (int)(dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        }
    }
}
