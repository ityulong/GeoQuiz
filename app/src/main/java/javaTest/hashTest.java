package javaTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenyulong on 2017/6/29.
 */

public class hashTest {
    public static void main(String[] args) {
        Map<String, String> datas = new HashMap<>();
        for (int i = 0; i <20 ; i++) {
            datas.put("key" + i, "value" + i);
        }
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            System.out.println("key:" + entry.getKey() + ",value :" + entry.getValue());
        }
    }
}
