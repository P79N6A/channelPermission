package com.haier.svc.api.controller.util.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/15 10:51
 * @modificed by:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelTitle {
  //标题名称
  String cellValue() default "";
  //单元格占几行
  int rowNum() default 1;
  //单元格占几列
  int columnNum() default 1;
  //单元格行数起始位置，从1开始
  int rowStartIndex() default 1;
  //单元格列数起始位置，从1开始
  int columnStartIndex();
  //是否是子标题
  boolean realTitle() default true;


}
