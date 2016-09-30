package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2015/3/3 0003.
 */
import java.io.Serializable;

/**
 * 产品类别
 */
public class Category implements Serializable {
    private String id;
    /**
     * 类别名称
     */
    private String name;
//    /**
//     * 描述
//     */
//    private String description;
//    /**
//     * 类别级别（一级类别，二级类别），默认为一级
//     */
//    private Integer level = 1;
//    /**
//     * 上级Id
//     */
//    private String superiorId;
//    /**
//     * 上级name
//     */
//    private String superiorName;

    /**
     * 是否显示
     */
    private boolean status;

    /**
     * 图片
     */
    private String image;

    /**
     * 所属商
     */
    private String merchantAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Integer getLevel() {
//        return level;
//    }
//
//    public void setLevel(Integer level) {
//        this.level = level;
//    }
//
//    public String getSuperiorId() {
//        return superiorId;
//    }
//
//    public void setSuperiorId(String superiorId) {
//        this.superiorId = superiorId;
//    }
//
//    public String getSuperiorName() {
//        return superiorName;
//    }
//
//    public void setSuperiorName(String superiorName) {
//        this.superiorName = superiorName;
//    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Category) {
            if (((Category) obj).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
