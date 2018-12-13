package com.haier.svc.api.controller.util.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/15 14:11
 * @modificed by:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelName {
  String fileName() default "表格";

}
