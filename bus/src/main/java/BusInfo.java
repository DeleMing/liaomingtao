import java.util.List;

/**
 * @author: LiaoMingtao
 * @date: 2022/3/20
 */
public class BusInfo {

    /**
     * 公交序号
     */
    private String busSerialNumber;
    /**
     * 是否损坏
     */
    public boolean isBroken;

    public Integer getOpOrDown() {
        return opOrDown;
    }

    public void setOpOrDown(Integer opOrDown) {
        this.opOrDown = opOrDown;
    }

    /**
     * 上行运行 0； 下行运行 1
     */
    public Integer opOrDown;

    /**
     * 当前乘客
     */
    public List<PassengerInfo> passengerInfoList;

    /**
     * 运行记录
     */
    public List<BusRunRecord> busRunRecordList;

    /**
     * 总载客数
     */
    public Integer totalPassengerNum;

    /**
     * 总运行时间
     */
    public Double totalRunTime;

    /**
     * 总行驶时间
     */
    public Double totalTime;


    public Integer getTotalPassengerNum() {
        return totalPassengerNum;
    }

    public void setTotalPassengerNum(Integer totalPassengerNum) {
        this.totalPassengerNum = totalPassengerNum;
    }

    public Double getTotalRunTime() {
        return totalRunTime;
    }

    public void setTotalRunTime(Double totalRunTime) {
        this.totalRunTime = totalRunTime;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public String getBusSerialNumber() {
        return busSerialNumber;
    }

    public void setBusSerialNumber(String busSerialNumber) {
        this.busSerialNumber = busSerialNumber;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public List<PassengerInfo> getPassengerInfoList() {
        return passengerInfoList;
    }

    public void setPassengerInfoList(List<PassengerInfo> passengerInfoList) {
        this.passengerInfoList = passengerInfoList;
    }

    public List<BusRunRecord> getBusRunRecordList() {
        return busRunRecordList;
    }

    public void setBusRunRecordList(List<BusRunRecord> busRunRecordList) {
        this.busRunRecordList = busRunRecordList;
    }
}
