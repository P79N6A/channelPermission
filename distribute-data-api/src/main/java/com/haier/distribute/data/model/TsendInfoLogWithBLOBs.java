package  com.haier.distribute.data.model;

public class TsendInfoLogWithBLOBs extends TsendInfoLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.sendInfo
     *
     * @mbggenerated
     */
    private String sendinfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.responseInfo
     *
     * @mbggenerated
     */
    private String responseinfo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.sendInfo
     *
     * @return the value of t_send_info_log.sendInfo
     *
     * @mbggenerated
     */
    public String getSendinfo() {
        return sendinfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.sendInfo
     *
     * @param sendinfo the value for t_send_info_log.sendInfo
     *
     * @mbggenerated
     */
    public void setSendinfo(String sendinfo) {
        this.sendinfo = sendinfo == null ? null : sendinfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.responseInfo
     *
     * @return the value of t_send_info_log.responseInfo
     *
     * @mbggenerated
     */
    public String getResponseinfo() {
        return responseinfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.responseInfo
     *
     * @param responseinfo the value for t_send_info_log.responseInfo
     *
     * @mbggenerated
     */
    public void setResponseinfo(String responseinfo) {
        this.responseinfo = responseinfo == null ? null : responseinfo.trim();
    }
}