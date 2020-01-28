package kr.co.core.tools.webview;

import lombok.Data;

@Data
public class RecentData {
    String url;

    RecentData(String url) {
        this.url = url;
    }
}
