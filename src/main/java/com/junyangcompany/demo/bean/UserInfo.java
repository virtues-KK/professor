package com.junyangcompany.demo.bean;

import com.junyangcompany.demo.entity.City;
import com.junyangcompany.demo.entity.District;
import com.junyangcompany.demo.entity.Province;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;
import lombok.Data;

import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author zxy
 * @date 2018-09-17 18:34
 */
@Data
public class UserInfo {

  /**
   * 通过id 关联 安全服务中的id
   */
  private Long id;
  /**
   * 电话号码
   */
  private String phoneNumber;
  /**
   * 地址类
   */
  private Province province;

  private City city;

  private District district;

  /**
   * 是否为有效用户
   */
  private Boolean isValid;

  /**
   * 微信openId
   */
  private String wechatOpenId;

  /**
   * 用户等级
   */
  private Level level;

  private Long userId;

  private String name;

  private String nickName;

  private String image;

  private School school;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String startYear;

  private Boolean gender;

  private Boolean isStudent;
  @Enumerated
  private ScienceAndArt scienceArt;

  private Integer position;

  private Integer modifyTimes;

  private Double score;

  private Boolean isSuperUser;
  // 班级
  private String className;
  public enum Level {
    FULL_FEATURED,
    ZHI_YUAN,
    XUAN_KE,
    EXPERIENCE,
    SUPER_USER,
    SHENG_XUE,
  }

}