package cn.user_db.model;

import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

import cn.network.Url;
import cn.network.model.AMBaseDto;
import cn.utils.YZStringUtil;

/**
 * Created by base on 20/09/15.
 */
public class MolaUser extends AMBaseDto implements Serializable {
    private String role;
    private long userId;
    private Object yomoerId;
    private long lastLogin;
    private long modifyTime;
    private Object yzhId;
    private Object channel;
    private Object corpId;
    private Object expire;
    private Object corpName;
    private Object domain;
    private Object departments;
    private String phone;
    private String email;
    private String avatar;
    private String avatarChange;
    private String name;
    private int sex;
    private Object birthday;
    private int chPasswd;
    private boolean canUnbind;
    private boolean canMerge;
    private String membership;
    private String membershipLabel;
    private Object duetime;
    private String securityLevel;
    private Object shopPush;
    private String securityLevelCN;
    private List<String> binds;
    private List<MembershipsBean> memberships;
    private String account;
    private boolean isAdmin;
    private CorpBean corp;
    private Object jobType;
    private Object area;
    private Object address;
    private int corpFrozen;
    private int accountFrozen;
    private boolean firstLogin;
    private String adminRole;
    private int isFrozen;
    private int enterprisePemission;
    private int needChangePassword;
    private int isBindEmail;
    private int isBindWX;

    public int getNeedChangePassword() {
        return needChangePassword;
    }

    public String getAvatarChange() {
        return avatarChange;
    }

    public void setAvatarChange(String avatarChange) {
        this.avatarChange = avatarChange;
    }

    public void setIsBindWX(int isBindWX) {
        this.isBindWX = isBindWX;
    }

    public int getIsBindWX() {
        return isBindWX;
    }

    public boolean isNeedChangePassword() {
        return needChangePassword == 1;
    }

    public void setNeedChangePassword(int needChangePassword) {
        this.needChangePassword = needChangePassword;
    }

    public int getIsBindEmail() {
        if (TextUtils.isEmpty(email)) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setIsBindEmail(int isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public int getIsBindPhone() {
        if (TextUtils.isEmpty(phone)) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getEnterprisePemission() {
        return enterprisePemission;
    }

    public void setEnterprisePemission(int enterprisePemission) {
        this.enterprisePemission = enterprisePemission;
    }

    /**
     * 发表企业公告
     *
     * @return
     */
    public boolean isEnablePublish() {
        boolean flag = false;
        long mask = 1 << 5;// 文件日志权限
        if ((enterprisePemission & mask) == mask) {
            flag = true;
        }
        return flag;
    }

    public static class MembershipsBean {
        /**
         * membership : Member
         * duetime : null
         * membershipLabel : 注册会员
         */

        private String membership;
        private Object duetime;
        private String membershipLabel;

        public String getMembership() {
            return membership;
        }

        public void setMembership(String membership) {
            this.membership = membership;
        }

        public Object getDuetime() {
            return duetime;
        }

        public void setDuetime(Object duetime) {
            this.duetime = duetime;
        }

        public String getMembershipLabel() {
            return membershipLabel;
        }

        public void setMembershipLabel(String membershipLabel) {
            this.membershipLabel = membershipLabel;
        }
    }

    public static class CorpBean {
        /**
         * id : 10500
         * name : 雨润双汇有限公司雨润双汇有限公司雨润双汇有限公司雨润双汇有限公司雨润双汇有限公司雨润双汇有限公司雨润
         * domain : 7hrp753
         * udid : 7HRP753
         * industry : null
         * corpSize : 0
         * managers : [{"id":370927400424308737,"name":"企业管理员","phone":"15851710851","email":"15720607668@163.com"}]
         * expireTime : 1609344000000
         * grantSize : 7
         * memberSize : 7
         * state : Paid
         */

        private int id;
        private String name;
        private String domain;
        private String udid;
        private Object industry;
        private int corpSize;
        private long expireTime;
        private int grantSize;
        private int memberSize;
        private String state;
        private List<ManagersBean> managers;

        public boolean isExpire() {
            if (expireTime > System.currentTimeMillis()) {
                return false;
            } else {
                return true;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public Object getIndustry() {
            return industry;
        }

        public void setIndustry(Object industry) {
            this.industry = industry;
        }

        public int getCorpSize() {
            return corpSize;
        }

        public void setCorpSize(int corpSize) {
            this.corpSize = corpSize;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        public int getGrantSize() {
            return grantSize;
        }

        public void setGrantSize(int grantSize) {
            this.grantSize = grantSize;
        }

        public int getMemberSize() {
            return memberSize;
        }

        public void setMemberSize(int memberSize) {
            this.memberSize = memberSize;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<ManagersBean> getManagers() {
            return managers;
        }

        public void setManagers(List<ManagersBean> managers) {
            this.managers = managers;
        }

        public static class ManagersBean {
            /**
             * id : 370927400424308737
             * name : 企业管理员
             * phone : 15851710851
             * email : 15720607668@163.com
             */

            private long id;
            private String name;
            private String phone;
            private String email;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Object getYomoerId() {
        return yomoerId;
    }

    public void setYomoerId(Object yomoerId) {
        this.yomoerId = yomoerId;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Object getYzhId() {
        return yzhId;
    }

    public void setYzhId(Object yzhId) {
        this.yzhId = yzhId;
    }

    public Object getChannel() {
        return channel;
    }

    public void setChannel(Object channel) {
        this.channel = channel;
    }

    public Object getCorpId() {
        return corpId;
    }

    public void setCorpId(Object corpId) {
        this.corpId = corpId;
    }

    public Object getExpire() {
        return expire;
    }

    public void setExpire(Object expire) {
        this.expire = expire;
    }

    public Object getCorpName() {
        return corpName;
    }

    public void setCorpName(Object corpName) {
        this.corpName = corpName;
    }

    public Object getDomain() {
        return domain;
    }

    public void setDomain(Object domain) {
        this.domain = domain;
    }

    public Object getDepartments() {
        return departments;
    }

    public void setDepartments(Object departments) {
        this.departments = departments;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        if (YZStringUtil.isEmpty(avatarChange) && !avatar.contains(Url.getScheme())) {
            avatar = Url.getUrlBuilder(true) + "/auth" + avatar;
        }
        if (!YZStringUtil.isEmpty(avatarChange)) {
            avatar = avatarChange;
        }
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public int getChPasswd() {
        return chPasswd;
    }

    public void setChPasswd(int chPasswd) {
        this.chPasswd = chPasswd;
    }

    public boolean isCanUnbind() {
        return canUnbind;
    }

    public void setCanUnbind(boolean canUnbind) {
        this.canUnbind = canUnbind;
    }

    public boolean isCanMerge() {
        return canMerge;
    }

    public void setCanMerge(boolean canMerge) {
        this.canMerge = canMerge;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getMembershipLabel() {
        return membershipLabel;
    }

    public void setMembershipLabel(String membershipLabel) {
        this.membershipLabel = membershipLabel;
    }

    public Object getDuetime() {
        return duetime;
    }

    public void setDuetime(Object duetime) {
        this.duetime = duetime;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public Object getShopPush() {
        return shopPush;
    }

    public void setShopPush(Object shopPush) {
        this.shopPush = shopPush;
    }

    public String getSecurityLevelCN() {
        return securityLevelCN;
    }

    public void setSecurityLevelCN(String securityLevelCN) {
        this.securityLevelCN = securityLevelCN;
    }

    public List<String> getBinds() {
        return binds;
    }

    public void setBinds(List<String> binds) {
        this.binds = binds;
    }

    public List<MembershipsBean> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<MembershipsBean> memberships) {
        this.memberships = memberships;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public CorpBean getCorp() {
        return corp;
    }

    public void setCorp(CorpBean corp) {
        this.corp = corp;
    }

    public Object getJobType() {
        return jobType;
    }

    public void setJobType(Object jobType) {
        this.jobType = jobType;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public int getCorpFrozen() {
        return corpFrozen;
    }

    public void setCorpFrozen(int corpFrozen) {
        this.corpFrozen = corpFrozen;
    }

    public int getAccountFrozen() {
        return accountFrozen;
    }

    public void setAccountFrozen(int accountFrozen) {
        this.accountFrozen = accountFrozen;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public int getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(int isFrozen) {
        this.isFrozen = isFrozen;
    }

    @Override
    public String toString() {
        return "MolaUser{" +
                "role='" + role + '\'' +
                ", userId=" + userId +
                ", yomoerId=" + yomoerId +
                ", lastLogin=" + lastLogin +
                ", modifyTime=" + modifyTime +
                ", yzhId=" + yzhId +
                ", channel=" + channel +
                ", corpId=" + corpId +
                ", expire=" + expire +
                ", corpName=" + corpName +
                ", domain=" + domain +
                ", departments=" + departments +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", chPasswd=" + chPasswd +
                ", canUnbind=" + canUnbind +
                ", canMerge=" + canMerge +
                ", membership='" + membership + '\'' +
                ", membershipLabel='" + membershipLabel + '\'' +
                ", duetime=" + duetime +
                ", securityLevel='" + securityLevel + '\'' +
                ", shopPush=" + shopPush +
                ", securityLevelCN='" + securityLevelCN + '\'' +
                ", binds=" + binds +
                ", memberships=" + memberships +
                ", account='" + account + '\'' +
                ", isAdmin=" + isAdmin +
                ", corp=" + corp +
                ", jobType=" + jobType +
                ", area=" + area +
                ", address=" + address +
                ", corpFrozen=" + corpFrozen +
                ", accountFrozen=" + accountFrozen +
                ", firstLogin=" + firstLogin +
                ", adminRole='" + adminRole + '\'' +
                ", isFrozen=" + isFrozen +
                '}';
    }
}
