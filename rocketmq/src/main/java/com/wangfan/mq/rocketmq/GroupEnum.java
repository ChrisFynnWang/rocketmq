package com.wangfan.mq.rocketmq;

/**
 * @Author : Chris Wang
 * @Description :
 * @Date : 2019/6/18 10:40
 */
public enum GroupEnum {
    COMMANDER("司令"),
    MUSKETEER("火枪兵");

    private String desc;

    GroupEnum(String desc) {
        this.desc = desc;
    }
}
