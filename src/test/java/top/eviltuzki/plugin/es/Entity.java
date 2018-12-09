package top.eviltuzki.plugin.es;

import top.eviltuzki.plugin.es.annotation.*;

import java.util.*;

public class Entity {
    private ArrayList<String> datas;
    @Keyword(fields = {
            @Field(name = "text",isKeyword = false)
    })
    private String value;
    @DateFormat(format = "yyyy-MM-dd")
    private Date gmtModify;
    private Double price;
    @Text(analyzer = "standard",fields =  {
            @Field(name = "keyword",isKeyword = true),
            @Field(name = "pinyin",isKeyword = false,analyzer = "whitespace")
    })
    private String title;
    @MappingIgnore
    private Map<String,Object> objectMap;
    private Queue<String> queue;
    private TreeSet<String> strings;
    int[] arrint;
    byte[] arrbyte;
    double[] arrdou;
    boolean[] arrbool;
    float[] arrfloat;
    short[] arrshort;
    long[] arrlong;
    Integer[] arrInt;
    TestObj entity;
    TestObj[] entities;
    @Nested
    List<TestObj> entitieList;

    public Entity() {
    }
}
