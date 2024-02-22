package com.example.final_project.Domain;

public class ListsDomain {
    private String title;
    private String subtitle;
    private Boolean isChecked;

    public ListsDomain(String title, String subtitle, Boolean isChecked) {
        this.title = title;
        this.subtitle = subtitle;
        this.isChecked = isChecked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
