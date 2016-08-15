package com.ee.enigma.dto;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DeviceIssueTrendLineDto {
    private Date startDate;
    private Date endDate;
    private List<IssueTrendLineData> issueTrendLineData = new ArrayList<IssueTrendLineData>();

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<IssueTrendLineData> getIssueTrendLineData() {
        return issueTrendLineData;
    }

    public void setIssueTrendLineData(List<IssueTrendLineData> issueTrendLineData) {
        this.issueTrendLineData = issueTrendLineData;
    }

}
