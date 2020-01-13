package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse
{
    //IMAGE UPLOAD RESPONSE

    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
