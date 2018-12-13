package com.haier.stock.util.excel;

import java.lang.annotation.*;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.util
 * @Date: Created in 2018/5/23 16:34
 * @Modified By:
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

  String filename() default "数据";

}
