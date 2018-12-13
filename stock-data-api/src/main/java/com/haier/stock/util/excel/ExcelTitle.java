package com.haier.stock.util.excel;

import java.lang.annotation.*;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.util.excel
 * @Date: Created in 2018/5/23 16:40
 * @Modified By:
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTitle {
  String titleName() default "";

  int importIndex() default -1;
}
