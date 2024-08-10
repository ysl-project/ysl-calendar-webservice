package com.ysl.calendar.domain.categories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarCategory {

    String id;
    String name;
    String color;
    String create_user;

    public CalendarCategory() {
    }

    public CalendarCategory(String name, String color, String create_user) {
//        this.id = id;
        this.name = name;
        this.color = color;
        this.create_user = create_user;
    }

    @Override
    public String toString() {
        return "CalendarCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", create_user='" + create_user + '\'' +
                '}';
    }
}
