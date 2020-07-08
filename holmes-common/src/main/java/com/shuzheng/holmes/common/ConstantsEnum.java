package com.shuzheng.holmes.common;


import lombok.Getter;

public interface ConstantsEnum {

    /**
     * 异常类型
     */
    @Getter
    enum EXCEPTION_TYPE {

        PARAM_EXCEPTION("01", "参数错误"), NONE_EXIST("02", "不存在"), DIRTY_DATA("03", "脏数据"),
        NOT_PRIVILEGED("04", "权限不足"), INVOKE_EXCEPTION("80", "接口调用异常"), UNKNOWN_EXCEPTION("99", "未知系统异常");
        private String code;
        private String desc;

        EXCEPTION_TYPE(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }



    /**
     * 用户角色
     */
    @Getter
    enum ROLE {
        ADMIN("admin"), USER("user");

        private String role;

        ROLE(String role) {
            this.role = role;
        }
    }

    /**
     * 启动环境
     */
    @Getter
    enum ENVIRONMENT {
        DEV("dev"), LOCAL("local"), PROD("prod");

        private String enviroment;

        ENVIRONMENT(String enviroment) {
            this.enviroment = enviroment;
        }

    }


    /**
     * 数据状态
     */
    @Getter
    enum DATA_STATUS {
        NORMAL((byte)0), DELETE((byte)1);

        private Byte status;

        DATA_STATUS(Byte status) {
            this.status = status;
        }

    }

}

