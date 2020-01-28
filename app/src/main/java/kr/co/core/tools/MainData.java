package kr.co.core.tools;

import android.app.Activity;

import lombok.Data;

@Data
class MainData {
    String title;
    Class<?> act;

    MainData(String title, Class<?> act) {
        this.title = title;
        this.act = act;
    }
}
