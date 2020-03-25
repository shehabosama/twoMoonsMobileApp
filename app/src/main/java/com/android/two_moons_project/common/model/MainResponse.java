package com.android.two_moons_project.common.model;

import com.google.gson.annotations.SerializedName;

public class MainResponse {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;

}
