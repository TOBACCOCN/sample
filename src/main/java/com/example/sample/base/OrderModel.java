package com.example.sample.base;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class OrderModel extends BaseRowModel {

    @ExcelProperty(value = "订单号", index = 0)
    private String id;

    @ExcelProperty(value = "订单金额", index = 1)
    private Double orderAmount;

    @ExcelProperty(value = "订单创建时间", index = 2)
    private String createTime;

    @ExcelProperty(value = "订单支付时间", index = 3)
    private String payTime;

}
