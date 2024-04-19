package com.example.sample.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.example.sample.base.OrderModel;
import com.example.sample.base.UserModel;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.time.Clock;
import java.util.*;

@Slf4j
public class EasyExcelUtil {

    // private static Logger logger = LoggerFactory.getLogger(EasyExcelUtil.class);

    @SuppressWarnings("unchecked")
    public static void writeExcel(OutputStream outputStream, Map<String, List<? extends BaseRowModel>> map) throws IOException {
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        // SimpleStream.forEach(map, (index, entry) -> {
        //     Type type = ((ParameterizedType) entry.getValue().getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        //     Sheet sheet = new Sheet(index, 0, (Class) type);
        //     sheet.setSheetName((String) entry.getKey());
        //     writer.write((List) entry.getValue(), sheet);
        // });
        //
        // int i = 0;
        // for (Map.Entry<String, List<? extends BaseRowModel>> entry : map.entrySet()) {
        //     String key = entry.getKey();
        //     List<? extends BaseRowModel> value = entry.getValue();
        //     Sheet sheet = new Sheet(++i, 0, (Class<? extends BaseRowModel>) ((ParameterizedType) entry.getValue().getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        //     sheet.setSheetName(key);
        //     writer.write(value, sheet);
        // }

        final int[] x = {0};
        map.forEach((key, value) -> {
            int a = ++x[0];
            log.info("{}", ((ParameterizedType) value.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            Sheet sheet = new Sheet(a, 0, (Class<? extends BaseRowModel>) ((ParameterizedType) value.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            sheet.setSheetName(key);
            writer.write(value, sheet);
        });

        writer.finish();
        outputStream.close();
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new FileOutputStream(new File("D:/users_orders.xlsx"));
        Map<String, List<? extends BaseRowModel>> map = new HashMap<>();
        List<UserModel> users = new ArrayList<UserModel>(){};
        users.add(new UserModel("zhangsan", 18, "beijing"));
        users.add(new UserModel("lisi", 19, "shanghai"));
        users.add(new UserModel("wangwu", 20, "guangzhou"));
        map.put("user", users);
        List<OrderModel> orders = new ArrayList<OrderModel>(){};
        orders.add(new OrderModel(UUID.randomUUID().toString().replaceAll("-", ""), 66.06, Clock.systemUTC().instant().toString(), Clock.systemUTC().instant().toString()));
        orders.add(new OrderModel(UUID.randomUUID().toString().replaceAll("-", ""), 88.88, Clock.systemUTC().instant().toString(), Clock.systemUTC().instant().toString()));
        orders.add(new OrderModel(UUID.randomUUID().toString().replaceAll("-", ""), 50.49, Clock.systemUTC().instant().toString(), Clock.systemUTC().instant().toString()));
        orders.add(new OrderModel(UUID.randomUUID().toString().replaceAll("-", ""), 90.90, Clock.systemUTC().instant().toString(), Clock.systemUTC().instant().toString()));
        map.put("order", orders);
        writeExcel(outputStream, map);
        log.info(">>>>> WRITE EXCEL SUCCESS");
    }

}
