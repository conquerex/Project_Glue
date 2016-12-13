package com.hm.project_glue.util.write.photo;

/**
 * Created by HM on 2016-12-14.
 */

import android.graphics.Bitmap;

public class GalleryItem
{
    private String id;
    private String data;
    private Bitmap bitmap;
    private String path;
    private boolean checkedState;
    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return path;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    public boolean getCheckedState()
    {
        return checkedState;
    }
    public void setCheckedState(boolean checkedState)
    {
        this.checkedState = checkedState;
    }
}
