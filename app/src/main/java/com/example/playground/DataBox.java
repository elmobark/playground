package com.example.playground;



import java.util.HashMap;

public class DataBox{
    private final static HashMap<String,Object> map = new HashMap<>();
    public static void InsertData(String key,Object obj){
        map.put(key,obj);
    }
    public static Object GetData(String key){
        return map.get(key);
    }

}
