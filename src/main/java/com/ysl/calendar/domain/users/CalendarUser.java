package com.ysl.calendar.domain.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarUser {

    String id;
    String eamil;
    String name;
    String nickname;
    String password;
    String image;
    String comment;
    String social;

    public CalendarUser() {
    }

    public CalendarUser(String id, String eamil, String name, String nickname, String password, String image, String comment, String social) {
        this.id = id;
        this.eamil = eamil;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.image = image;
        this.comment = comment;
        this.social = social;
    }
}
