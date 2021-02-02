package com.etl.ds.core.Dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.etl.ds.core.enums.RunningType;
import com.etl.ds.core.exception.BizException;
import com.etl.ds.core.utils.DateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class TaskParamDto {

    private Integer executeType;
    private RunningType runningType;
    private Map<String, String> vars = new HashMap<>();

    public static final String VAR_DATE = "var_date";
    public static final String BIZ_DATE = "biz_date";
    public static final String BIZ_TIME = "biz_time";

    public static boolean isInherentParam(String param) {
        return param.equals(VAR_DATE) || param.equals(BIZ_DATE) || param.equals(BIZ_TIME);
    }

    //T+1
    public TaskParamDto(Integer executeType, String bizDate, RunningType runningType) {
        this.executeType = executeType;
        this.initBizDate(bizDate);
        this.runningType = runningType;
    }

    //real-time
    public TaskParamDto(Integer executeType, String bizDate, String bizTime, RunningType runningType) {
        this.executeType = executeType;
        this.initBizDate(bizDate, bizTime);
        this.runningType = runningType;
    }

    private void initBizDate(String bizDate, String bizTime) {
        vars.put(BIZ_DATE, bizDate);
        vars.put(VAR_DATE, DateUtils.bizDate2VarDate(bizDate));
        vars.put(BIZ_TIME, bizTime);
    }

    public TaskParamDto(Integer executeType, Map<String, String> vars, RunningType runningType) {
        this.executeType = executeType;
        this.setVars(vars);
        this.runningType = runningType;
    }


    @JSONField(serialize = false)
    public String findBizDate() {
        return vars.get(BIZ_DATE);
    }

    @JSONField(serialize = false)
    public String findBizTime() {
        return vars.get(BIZ_TIME);
    }

    @JSONField(serialize = false)
    public String findVarDate() {
        return vars.get(VAR_DATE);
    }

    @JSONField(serialize = false)
    public void initBizDate(String bizDate) {
        vars.put(BIZ_DATE, bizDate);
        vars.put(VAR_DATE, DateUtils.bizDate2VarDate(bizDate));
    }

    public void setVars(Map<String, String> vars) {
        if (!vars.containsKey(BIZ_DATE)) {
            throw new BizException("执行参数必须包含 biz_date ");
        }
        vars.put(VAR_DATE, DateUtils.bizDate2VarDate(vars.get(BIZ_DATE)));
        this.vars.putAll(vars);
    }

}