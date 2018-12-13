package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.workorder.WOUserDao;
import com.haier.shop.dao.workorder.WoDictionaryDao;
import com.haier.shop.dao.workorder.WoReviewContactsDao;
import com.haier.shop.dao.workorder.WoReviewLogDao;
import com.haier.shop.dto.LogBean;
import com.haier.shop.dto.ReviewContacts;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import com.haier.shop.model.WOUser;
import com.haier.shop.service.WoReviewContactsDataService;
import com.haier.shop.util.ReviewConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.services
 * @Date: Created in 2018/4/26 10:59
 * @Modified By:
 */
@Service
public class WoReviewContactsDataServiceImpl implements WoReviewContactsDataService {

  private static String EMPTY = "空";
  private static Logger log = LogManager.getLogger(WoReviewContactsDataServiceImpl.class);
  @Resource
  private WoReviewContactsDao woReviewContactsDao;
  @Resource
  private WoReviewLogDao woReviewLogDao;
  @Resource
  private WoDictionaryDao woDictionaryDao;
  @Resource
  private WOUserDao woUserDao;

  /**
   * 分页查询
   */
  @Override
  public JSONObject page(ReviewContactsDto reviewContacts,
      PagerInfo pager) {
    JSONObject result = new JSONObject();
    result.put("total", woReviewContactsDao.findPageCount(reviewContacts));
    result.put("rows", woReviewContactsDao.findPageList(reviewContacts, pager));
    return result;
  }

  @Override
  public List<ReviewContactsDto> findListLG(ReviewContactsDto reviewContacts) {
    List<ReviewContactsDto> data = new ArrayList<>();
    String ordercomeDTO = reviewContacts.getOrdercome();
    String question1level1DTO = reviewContacts.getQuestion1level1();
    String question1level2DTO = reviewContacts.getQuestion1level2();
    String companyDTO = reviewContacts.getCompany();
    // 判断订单来源是否为空
    if (ordercomeDTO == null) {
      reviewContacts.setOrdercome("");
    }
    // 判断责任1是否为空
    if (question1level1DTO == null) {
      reviewContacts.setQuestion1level1("");
    }
    // 判断责任2是否为空
    if (question1level2DTO == null) {
      reviewContacts.setQuestion1level2("");
    }
    // 判断工贸是否为空
    if (companyDTO == null) {
      reviewContacts.setCompany("");
    }
    List<ReviewContactsDto> list = new ArrayList<ReviewContactsDto>();
    list.add(reviewContacts);

    String oldQuestion1level2 = reviewContacts.getQuestion1level2();// 原工贸
    for (int i = 0; i < 5; i++) {
      data = woReviewContactsDao.validateByList(list);
      if (data != null && data.size() > 0) {
        break;
      }
      switch (i) {
        case 0:
          reviewContacts.setQuestion1level2("");// 设置责任位2为空
          break;
        case 1:
          reviewContacts.setQuestion1level2(oldQuestion1level2);// 设置责任位2位原责任2
          reviewContacts.setCompany("");// 设置工贸为空
          break;
        case 2:
          reviewContacts.setQuestion1level2("");// 设置责任位2为空
          reviewContacts.setCompany("");// 设置工贸为原工贸
          break;
        case 3:
          reviewContacts.setQuestion1level1("");// 设置责任位1为空
          break;
        default:
          data.clear();
          break;
      }
    }
    return data;
  }

  /**
   * 添加
   */
  @Override
  public Boolean addReviewContacts(ReviewContacts record) {
    String uuid = UUID.randomUUID().toString();
    record.setId(uuid);
    int mun = woReviewContactsDao.insert(record);
    // 插入日志
    insertLog(null, record, ReviewConstants.LOG.LOG_4);
    return mun > 0;
  }

  /**
   * 修改
   */
  @Override
  public Boolean updateReviewContacts(ReviewContacts record) {
    int mun = woReviewContactsDao.updateByPrimaryKeySelective(record);
    // 插入日志
    insertLog(null, record, ReviewConstants.LOG.LOG_5);
    return mun > 0;
  }

  /**
   * 删除
   */
  @Override
  public Boolean deleteReviewContacts(List<ReviewContacts> record, String qf) {
    // 插入日志
    for (ReviewContacts r : record) {
      ReviewContacts userid = woReviewContactsDao.selectByPrimaryKey(r.getId());
      r.setUserid(userid.getUserid());
      Integer bs;
      if ("tcy".equals(qf)) {
        bs = ReviewConstants.LOG.LOG_9;
      } else {
        bs = ReviewConstants.LOG.LOG_6;
      }
      insertLog(null, r, bs);
    }
    //
    return woReviewContactsDao.deleteReviewContacts(record) > 0;
  }

  @Override
  public Boolean findinsert(ReviewContactsDto reviewContacts) {
    Boolean bool = true;
    boolean noOff = true;// true为添加 false为修改 默认为添加
    if (!("").equals(reviewContacts.getId())
        && null != reviewContacts.getId()) {
      // 如果存在id 则为修改
      noOff = false;
    }

    // ===================================================//
    // 根据责任位1，责任位2，订单来源，工贸验证
    String ddly = reviewContacts.getOrdercome();
    String zr1 = reviewContacts.getQuestion1level1();
    String zr2 = reviewContacts.getQuestion1level2();
    String gm = reviewContacts.getCompany();
    List<String> ddlyList = Arrays.asList(ddly.split(","));
    List<String> gmList = Arrays.asList(gm.split(","));
    List<ReviewContactsDto> contactList = new ArrayList<ReviewContactsDto>();
    for (String ddlyStr : ddlyList) {
      for (String gmStr : gmList) {
        ReviewContactsDto contacts = new ReviewContactsDto();
        contacts.setQuestion1level1(zr1);
        contacts.setQuestion1level2(zr2);
        contacts.setOrdercome(ddlyStr);
        contacts.setCompany(gmStr);
        contactList.add(contacts);
      }
    }

    List<ReviewContactsDto> data = woReviewContactsDao.validateByList(contactList);

    // 精确查找
    // List<ReviewContactsDto> data =
    // reviewContactsReadDao.findList(contacts);
    // 有重复责任位(添加走)
    if (!data.isEmpty()) {
      String finallyOrder = EMPTY;
      String finallyCompany = EMPTY;
      for (ReviewContactsDto reviewContact : data) {
        if (!(reviewContact.getId().equals(reviewContacts.getId()))) {
          //查询相同的人员责任
          String order = reviewContact.getOrdercome();
          String ques1 = reviewContact.getQuestion1level1();
          String ques2 = reviewContact.getQuestion1level2();
          String company = reviewContact.getCompany();
          List<String> resultLyList = Arrays.asList(order.split(","));
          List<String> resultGmList = Arrays.asList(company.split(","));
          for (String lys : ddlyList) {//循环传入订单来源
            for (String rlys : resultLyList) {//循环相同订单来源
              if (lys.equals(rlys)) {//如果两个相等
                finallyOrder = rlys;
                break;
              }
            }
          }
          for (String gms : gmList) {//循环传入工贸
            for (String rgms : resultGmList) {//循环相同工贸
              if (gms.equals(rgms)) {
                finallyCompany = rgms;
                break;
              }
            }
          }
          ques1 = "".equals(ques1) ? EMPTY : ques1;
          ques2 = "".equals(ques2) ? EMPTY : ques2;
          finallyCompany = "".equals(finallyCompany) ? "空" : finallyCompany;
          throw new BusinessException("人员'" + reviewContact.getUsername() + "'下已存在以下责任:" +
              "<br>" + "订单来源:" + finallyOrder +
              "<br>" + "责任位1:" + ques1 +
              "<br>" + "责任位2:" + ques2 +
              "<br>" + "工贸:" + finallyCompany);
        }
      }
    }

    // ===================================================//
    ReviewContactsDto reviewContactsDto = new ReviewContactsDto();
    Integer oneORother = null;
    // 查询领导
    reviewContactsDto.setUserid(reviewContacts.getUserid());
    List<ReviewContactsDto> ld = woReviewContactsDao
        .findList(reviewContactsDto);
    if (ld != null && ld.size() > 0) {
      // 数据库查询的数据
      String Manageruserid1 = ld.get(0).getManageruserid1();
      String Manageruserid2 = ld.get(0).getManageruserid2();
      String Issend1 = (ld.get(0).getIssend1() != null && !"".equals(ld
          .get(0).getIssend1())) ? ld.get(0).getIssend1() : "0";
      String Issend2 = (ld.get(0).getIssend2() != null && !"".equals(ld
          .get(0).getIssend2())) ? ld.get(0).getIssend2() : "0";
      // 前台传来的数据
      String R_Manageruserid1 = reviewContacts.getManageruserid1();
      String R_Manageruserid2 = reviewContacts.getManageruserid2();
      String R_Issend1 = (reviewContacts.getIssend1() != null && !""
          .equals(reviewContacts.getIssend1())) ? reviewContacts
          .getIssend1() : "0";
      String R_Issend2 = (reviewContacts.getIssend2() != null && !""
          .equals(reviewContacts.getIssend2())) ? reviewContacts
          .getIssend2() : "0";
      // 判断前台领导与后台领导是否匹配
      if (!((Manageruserid1.equals(R_Manageruserid1))
          && (Manageruserid2.equals(R_Manageruserid2))
          && (Issend1.equals(R_Issend1)) && (Issend2
          .equals(R_Issend2)))) {
        // 不匹配更新所有相同人员的旧数据中的领导
        reviewContacts.setIssend1(R_Issend1);
        reviewContacts.setIssend2(R_Issend2);
        int mun = woReviewContactsDao
            .updatemanagerUserId(reviewContacts);
        // 日志插入
        LogBean log = new LogBean();
        log.setUsername(reviewContacts.getAdduser());
        log.setFkid(reviewContacts.getId());
        log.setMkname(ReviewConstants.MKNAME.MKNAME_2);
        log.setType(ReviewConstants.LOG.LOG_8);
        log.setContent("人员更新领导");
        log.setLogtime(new Date());
        woReviewLogDao.insertSelective(log);
      }
    } else {
      //没有Userid为第一次添加责任人
      oneORother = ReviewConstants.LOG.LOG_4;
    }

    // 如果订单来源是'其他'，就把其他的插入数据字典
    if (("其他").equals(reviewContacts.getOrdercome())) {
      ReviewDataDictionaryEntity reviewDataDictionaryEntity = new ReviewDataDictionaryEntity();
      reviewDataDictionaryEntity.setValueMeaning(reviewContacts.getQtd());// 对应中文名称
      ReviewDataDictionaryEntity review = woDictionaryDao
          .selectorderFlag();
      // 验证订单来源重复
      ReviewDataDictionaryEntity view = woDictionaryDao
          .selectByMeaning(reviewContacts.getQtd());
      if (view == null) {
        // 不重复
        reviewDataDictionaryEntity.setValue(review.getOrderFlag()
            .toString());
        reviewDataDictionaryEntity.setOrderFlag(review.getOrderFlag());
        woDictionaryDao
            .addReviewLY(reviewDataDictionaryEntity);
        // 日志插入
        LogBean log = new LogBean();
        log.setUsername(reviewContacts.getAdduser());
        log.setFkid(reviewContacts.getId());
        log.setMkname(ReviewConstants.MKNAME.MKNAME_2);
        log.setType(ReviewConstants.LOG.LOG_7);
        log.setContent("订单来源选择'其他':" + reviewContacts.getQtd());
        log.setLogtime(new Date());
        woReviewLogDao.insertSelective(log);
        reviewContacts.setOrdercome(reviewContacts.getQtd());
      } else {
        throw new BusinessException("有重复的订单来源!");
      }
    }
    // 如果是true则添加
    if (noOff) {
      // 插入语句
      String uuid = UUID.randomUUID().toString();
      reviewContacts.setId(uuid);
      reviewContacts.setAddtime(new Date().toString());
      int mun = woReviewContactsDao.insert(reviewContacts);
      // 日志插入
      if (oneORother == null) {
        //为责任人增加责任位走
        oneORother = ReviewConstants.LOG.LOG_7;
      }
      insertLog(reviewContacts, null, oneORother);
    } else {
      // ReviewContacts record = new ReviewContacts();
      // BeanUtils.copyProperties(reviewContacts, record);
      reviewContacts.setUpdateuser(reviewContacts.getAdduser());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      reviewContacts.setUpdatetime(sdf.format(new Date()));
      woReviewContactsDao.updateByPrimaryKeySelective(reviewContacts);
      // 日志插入
      insertLog(reviewContacts, null, ReviewConstants.LOG.LOG_8);
    }
    return bool;
  }

  @Override
  public Boolean updatedel(ReviewContacts record) {
    int num = woReviewContactsDao.updatedel(record);
    // 日志插入
    insertLog(null, record, ReviewConstants.LOG.LOG_9);
    return num > 0;
  }

  /**
   * 保存按钮
   */
  @Override
  public Integer updatemanagerUserId(ReviewContactsDto reviewContacts) {
    String Issend1 = (reviewContacts.getIssend1() != null && !""
        .equals(reviewContacts.getIssend1())) ? reviewContacts
        .getIssend1() : "0";
    String Issend2 = (reviewContacts.getIssend2() != null && !""
        .equals(reviewContacts.getIssend2())) ? reviewContacts
        .getIssend2() : "0";
    reviewContacts.setIssend1(Issend1);
    reviewContacts.setIssend2(Issend2);
    int mun = woReviewContactsDao.updatemanagerUserId(reviewContacts);
    // 日志插入
    LogBean log = new LogBean();
    log.setUsername(reviewContacts.getUpdateuser());
    log.setFkid(reviewContacts.getId());
    log.setMkname(ReviewConstants.MKNAME.MKNAME_2);
    log.setType(ReviewConstants.LOG.LOG_5);
    WOUser re = woUserDao.getById(reviewContacts
        .getUserid());
    log.setContent(re.getUserName());
    log.setLogtime(new Date());
    woReviewLogDao.insertSelective(log);
    // insertLog(reviewContacts, null, ReviewConstants.LOG.LOG_5);
    return mun;
  }

  /**
   * 通过ID更新责任人
   */
  @Override
  public Integer updateById(ReviewContactsDto reviewContacts) {
    int mun = woReviewContactsDao.updateById(reviewContacts);
    // 日志插入
    insertLog(reviewContacts, null, ReviewConstants.LOG.LOG_5);
    return mun;
  }

  @Override
  public List<ReviewContactsDto> findListBySearch(ReviewContactsDto rcd) {
    return woReviewContactsDao.findList(rcd);
  }


  /**
   * 日志操作
   */
  private Boolean insertLog(ReviewContactsDto dto, ReviewContacts record,
      int i) {
    Boolean bool = false;
    LogBean log = new LogBean();
    /**
     * 1:人员添加 2:人员修改 3:人员删除 4:人员责任位配置添加 5:人员信息修改 6:人员责任位配置删除 7:责任位添加 8:责任位修改
     * 9:责任位删除 10:工单添加 12:工单修改 13:反馈内容添加 14:中间结果添加 15:更改责任位 16:工单导出 17:日志导出
     */
    log.setType(i);
    Date logtime = new Date();
    log.setLogtime(logtime);
    // 1：人员信息管理 2：人员责任位配置 3：工单'
    log.setMkname(ReviewConstants.MKNAME.MKNAME_2);
    if (dto == null) {
      log.setFkid(record.getId());
      WOUser re = woUserDao.getById(record.getUserid());
      if (re != null) {
        log.setContent(re.getUserName());
      }
      if (i == 1 || i == 4 || i == 7) {
        log.setUsername(record.getAdduser());
      } else {
        log.setUsername(record.getUpdateuser());
      }
    } else {
      log.setFkid(dto.getId());
      WOUser re = woUserDao.getById(dto.getUserid());
      log.setContent(re.getUserName());
      if (i == 1 || i == 4 || i == 7) {
        log.setUsername(dto.getAdduser());
      } else {
        log.setUsername(dto.getUpdateuser());
      }
    }
    woReviewLogDao.insertSelective(log);
    return bool;
  }
}
