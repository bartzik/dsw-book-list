package com.dsw_pin.book_list.dtos;

public class ReviewRecordDto {
    private String comment;
    private String userName;

    public ReviewRecordDto(String comment, String userName) {
        this.comment = comment;
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getters e setters
}

