package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceEntity implements Serializable {

    //网单号
    private String     wdh;

    //客户编码，传默认值00002001
    private String     khbm;

    //发票抬头
    private String     khmc;

    //纳税人识别号
    private String     khsh;

    //注册地址和电话
    private String     khdz;

    //开户银行
    private String     khkhyhzh;

    //备注，传网单号即可
    private String     bz;

    //网单生成日期
    private Date       wdrq;

    //商品编码SKU
    private String     cpdm;

    //商品名称
    private String     cpmc;

    //商品分类名称
    private String     cpxh;

    //单位名称
    private String     cpdw;

    //商品数量
    private BigDecimal cpsl;

    //含税单价
    private BigDecimal hsdj;

    //含税金额
    private BigDecimal hsje;

    //不含税单价
    private BigDecimal bhsdj;

    //不含税金额
    private BigDecimal bhsje;

    //税额
    private BigDecimal jsje;

    //计税百分比，增票默认值是0.17
    private BigDecimal jssl;

    //积分金额，目前全部是0
    private BigDecimal jfje;

    //节能补贴金额，目前已经没有节能补贴，该字段为0即可
    private BigDecimal jlje;

    //节能补贴备注，目前已经没有节能补贴，该字段为空即可
    private String     jlbz;

    //发票类型(1=增税,2=普税)
    private Integer    fplx;

    //发票状态，1代表开票，5代表取消开票
    private Integer    fpzt;

    //订单支付方式
    private String     skfs;

    //为空即可
    private String     lbjsdh;

    //开票库位，对应网单的出库库位
    private String     kwbm;

    //是否货票同行，1代表货票同行即库房开票，2代表非货票同行即共享开票
    private Integer    hptx;

    //订单类型(DDLX)目前不管普票还是增票暂都默认ZBCC
    private String     ddlx;

    //为空即可
    private String     fgsno;

    //为空即可
    private String     ddhno;

    //LES提单号
    private String     wlno;

    //备用字段1
    private String     add1;

    //备用字段2，目前该字段专门当作“订单来源”传给金税
    private String     add2;

    //发票创建时间
    private Date       rrrq;

    //发票日期最后一次修改时间
    private Date       gxrq;

    private String     fphm;

    private Date       kprq;

    private Date       skrq;

    private String     kpman;

    private Integer    kpzt;

    private Date       zfrq;

    public String getWdh() {
        return wdh;
    }

    public void setWdh(String wdh) {
        this.wdh = wdh;
    }

    public String getKhbm() {
        return khbm;
    }

    public void setKhbm(String khbm) {
        this.khbm = khbm;
    }

    public String getKhmc() {
        return khmc;
    }

    public void setKhmc(String khmc) {
        this.khmc = khmc;
    }

    public String getKhsh() {
        return khsh;
    }

    public void setKhsh(String khsh) {
        this.khsh = khsh;
    }

    public String getKhdz() {
        return khdz;
    }

    public void setKhdz(String khdz) {
        this.khdz = khdz;
    }

    public String getKhkhyhzh() {
        return khkhyhzh;
    }

    public void setKhkhyhzh(String khkhyhzh) {
        this.khkhyhzh = khkhyhzh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getWdrq() {
        return wdrq;
    }

    public void setWdrq(Date wdrq) {
        this.wdrq = wdrq;
    }

    public String getCpdm() {
        return cpdm;
    }

    public void setCpdm(String cpdm) {
        this.cpdm = cpdm;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getCpxh() {
        return cpxh;
    }

    public void setCpxh(String cpxh) {
        this.cpxh = cpxh;
    }

    public String getCpdw() {
        return cpdw;
    }

    public void setCpdw(String cpdw) {
        this.cpdw = cpdw;
    }

    public BigDecimal getCpsl() {
        return cpsl;
    }

    public void setCpsl(BigDecimal cpsl) {
        this.cpsl = cpsl;
    }

    public BigDecimal getHsdj() {
        return hsdj;
    }

    public void setHsdj(BigDecimal hsdj) {
        this.hsdj = hsdj;
    }

    public BigDecimal getHsje() {
        return hsje;
    }

    public void setHsje(BigDecimal hsje) {
        this.hsje = hsje;
    }

    public BigDecimal getBhsdj() {
        return bhsdj;
    }

    public void setBhsdj(BigDecimal bhsdj) {
        this.bhsdj = bhsdj;
    }

    public BigDecimal getBhsje() {
        return bhsje;
    }

    public void setBhsje(BigDecimal bhsje) {
        this.bhsje = bhsje;
    }

    public BigDecimal getJsje() {
        return jsje;
    }

    public void setJsje(BigDecimal jsje) {
        this.jsje = jsje;
    }

    public BigDecimal getJssl() {
        return jssl;
    }

    public void setJssl(BigDecimal jssl) {
        this.jssl = jssl;
    }

    public BigDecimal getJfje() {
        return jfje;
    }

    public void setJfje(BigDecimal jfje) {
        this.jfje = jfje;
    }

    public BigDecimal getJlje() {
        return jlje;
    }

    public void setJlje(BigDecimal jlje) {
        this.jlje = jlje;
    }

    public String getJlbz() {
        return jlbz;
    }

    public void setJlbz(String jlbz) {
        this.jlbz = jlbz;
    }

    public Integer getFplx() {
        return fplx;
    }

    public void setFplx(Integer fplx) {
        this.fplx = fplx;
    }

    public Integer getFpzt() {
        return fpzt;
    }

    public void setFpzt(Integer fpzt) {
        this.fpzt = fpzt;
    }

    public String getSkfs() {
        return skfs;
    }

    public void setSkfs(String skfs) {
        this.skfs = skfs;
    }

    public String getLbjsdh() {
        return lbjsdh;
    }

    public void setLbjsdh(String lbjsdh) {
        this.lbjsdh = lbjsdh;
    }

    public String getKwbm() {
        return kwbm;
    }

    public void setKwbm(String kwbm) {
        this.kwbm = kwbm;
    }

    public String getDdlx() {
        return ddlx;
    }

    public void setDdlx(String ddlx) {
        this.ddlx = ddlx;
    }

    public String getFgsno() {
        return fgsno;
    }

    public void setFgsno(String fgsno) {
        this.fgsno = fgsno;
    }

    public String getDdhno() {
        return ddhno;
    }

    public void setDdhno(String ddhno) {
        this.ddhno = ddhno;
    }

    public String getWlno() {
        return wlno;
    }

    public void setWlno(String wlno) {
        this.wlno = wlno;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public Date getRrrq() {
        return rrrq;
    }

    public void setRrrq(Date rrrq) {
        this.rrrq = rrrq;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public Date getKprq() {
        return kprq;
    }

    public void setKprq(Date kprq) {
        this.kprq = kprq;
    }

    public Date getSkrq() {
        return skrq;
    }

    public void setSkrq(Date skrq) {
        this.skrq = skrq;
    }

    public String getKpman() {
        return kpman;
    }

    public void setKpman(String kpman) {
        this.kpman = kpman;
    }

    public Integer getKpzt() {
        return kpzt;
    }

    public void setKpzt(Integer kpzt) {
        this.kpzt = kpzt;
    }

    public Date getZfrq() {
        return zfrq;
    }

    public void setZfrq(Date zfrq) {
        this.zfrq = zfrq;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public Integer getHptx() {
        return hptx;
    }

    public void setHptx(Integer hptx) {
        this.hptx = hptx;
    }

}
