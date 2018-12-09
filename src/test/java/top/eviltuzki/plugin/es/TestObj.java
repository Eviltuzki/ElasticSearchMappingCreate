package top.eviltuzki.plugin.es;

import top.eviltuzki.plugin.es.annotation.Text;

class TestObj {
    private Double price;
    @Text(analyzer = "whitespace")
    private String title;
}
