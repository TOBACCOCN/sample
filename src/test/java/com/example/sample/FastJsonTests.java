package com.example.sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class FastJsonTests {

    // private static Logger logger = LoggerFactory.getLogger(FastJsonTests.class);

    @Test
    public void test() {
        JSONObject jsonObject = JSON.parseObject("{\"name\":\"zyh\"}");
        log.info(">>>>> jsonObject: {}", jsonObject);
        JSONArray jsonArray = JSON.parseArray("[{\"name\":\"zyh\"}, {\"name\":\"zys\"}]");
        log.info(">>>>> jsonArray: {}", jsonArray);

        Zhang zhang = JSON.parseObject("{\"name\":\"zyh\"}", Zhang.class);
        log.info(">>>>> zhang: {}", zhang);
        List<Zhang> zhangs = JSON.parseArray("[{\"name\":\"zyh\"}, {\"name\":\"zys\"}]", Zhang.class);
        log.info(">>>>> zhangs: {}", zhangs);

        log.info(">>>>> object-jsonString: {}", JSON.toJSONString(zhang));
        log.info(">>>>> list-jsonString: {}", JSON.toJSONString(zhangs));

    }

    static class Zhang {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Zhang [name=" + name + "]";
        }

    }

}
