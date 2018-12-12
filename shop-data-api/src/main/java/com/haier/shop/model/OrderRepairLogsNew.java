package com.haier.shop.model;

import java.io.Serializable;
/**
 * <p>Table: <strong>OrderRepairLogs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>orderRepairId</td><td>{@link Integer}</td><td>orderRepairId</td><td>int</td><td>退款ID</td></tr>
 *   <tr><td>operator</td><td>{@link String}</td><td>operator</td><td>varchar</td><td>操作人</td></tr>
 *   <tr><td>operate</td><td>{@link String}</td><td>operate</td><td>varchar</td><td>操作名称</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 * </table>
 *
 * @author 张波
 *
 * @date 2013-4-28
 * @email yudi@sina.com
 */
public class OrderRepairLogsNew implements Serializable{
        /** Comment for <code>serialVersionUID</code> */
        private static final long serialVersionUID = -1053580590579134169L;
        private Integer           id;

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer value) {
            this.id = value;
        }

        private Integer siteId;

        public Integer getSiteId() {
            return this.siteId;
        }

        public void setSiteId(Integer value) {
            this.siteId = value;
        }

        private Integer addTime = 0;

        public Integer getAddTime() {
            return this.addTime;
        }

        public void setAddTime(Integer value) {
            this.addTime = value;
        }

        private Integer orderRepairId = 0;

        /**
         * 获取 退款ID。
         */
        public Integer getOrderRepairId() {
            return this.orderRepairId;
        }

        /**
         * 设置 退款ID。
         *
         * @param value 属性值
         */
        public void setOrderRepairId(Integer value) {
            this.orderRepairId = value;
        }

        private String operator;

        /**
         * 获取 操作人。
         */
        public String getOperator() {
            return this.operator;
        }

        /**
         * 设置 操作人。
         *
         * @param value 属性值
         */
        public void setOperator(String value) {
            this.operator = value;
        }

        private String operate;

        /**
         * 获取 操作名称。
         */
        public String getOperate() {
            return this.operate;
        }

        /**
         * 设置 操作名称。
         *
         * @param value 属性值
         */
        public void setOperate(String value) {
            this.operate = value;
        }

        private String remark;

        /**
         * 获取 备注。
         */
        public String getRemark() {
            return this.remark;
        }

        /**
         * 设置 备注。
         *
         * @param value 属性值
         */
        public void setRemark(String value) {
            this.remark = value;
        }

}
