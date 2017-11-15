package com.example.student1.todolist;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int left = 0, top = 0, right = 0, bottom = 0;

    private SpacesItemDecoration(){}

    public static Builder newBuilder(){
        return new SpacesItemDecoration().new Builder();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.set(left, top, right, bottom);
    }

    public class Builder{
        private Builder(){}

        public Builder setSameSpaces(int space){
            SpacesItemDecoration.this.left = space;
            SpacesItemDecoration.this.top = space;
            SpacesItemDecoration.this.right = space;
            SpacesItemDecoration.this.bottom = space;
            return this;
        }

        public Builder setLeft(int left){
            SpacesItemDecoration.this.left = left;
            return this;
        }

        public Builder setTop(int top){
            SpacesItemDecoration.this.top = top;
            return this;
        }

        public Builder setRight(int right){
            SpacesItemDecoration.this.right = right;
            return this;
        }

        public Builder setBottom(int bottom){
            SpacesItemDecoration.this.bottom = bottom;
            return this;
        }

        public SpacesItemDecoration build(){
            return SpacesItemDecoration.this;
        }
    }
}
