package com.example.flowerapp.Entity;

public class Feedback {
    private String img;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public Feedback(String img, String content){
        this.img = img;
        this.content = content;
    }
    public Feedback(){}
}
