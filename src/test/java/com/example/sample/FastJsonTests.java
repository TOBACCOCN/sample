package com.example.sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FastJsonTests {

    private static Logger logger = LoggerFactory.getLogger(FastJsonTests.class);

    @Test
    public void test() {
        JSONObject jsonObject = JSON.parseObject("{\"name\":\"zyh\"}");
        logger.info(">>>>> jsonObject: {}", jsonObject);
        JSONArray jsonArray = JSON.parseArray("[{\"name\":\"zyh\"}, {\"name\":\"zys\"}]");
        logger.info(">>>>> jsonArray: {}", jsonArray);

        Zhang zhang = JSON.parseObject("{\"name\":\"zyh\"}", Zhang.class);
        logger.info(">>>>> zhang: {}", zhang);
        List<Zhang> zhangs = JSON.parseArray("[{\"name\":\"zyh\"}, {\"name\":\"zys\"}]", Zhang.class);
        logger.info(">>>>> zhangs: {}", zhangs);

        logger.info(JSON.toJSONString(zhang));
        logger.info(JSON.toJSONString(zhangs));

        logger.info(jsonObject.toJSONString());
        logger.info(jsonArray.toJSONString());
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
