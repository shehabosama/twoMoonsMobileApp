package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddProductResponse {
    @SerializedName("added_file")
    @Expose
    private List<String> Added = null;
    @SerializedName("not_added")
    @Expose
    private List<String> notAdded = null;

    public List<String> getAddedFile() {
        return Added;
    }

    public void setAddedFile(List<String> addedFile) {
        this.Added = addedFile;
    }

    public List<String> getNotAdded() {
        return notAdded;
    }

    public void setNotAdded(List<String> notAdded) {
        this.notAdded = notAdded;
    }
}
