/**
 * @author: LiaoMingtao
 * @date: 2022/3/20
 */
public class BusRunRecord {

    /**
     * 当前时间
     */
    public Double nowTime;

    /**
     * 是否为始发站
     */
    public boolean isStartStation;

    /**
     * 是否为终点站
     */
    public boolean isEndStation;

    /**
     * 当前站点号
     */
    public Integer nowStationNumber;

    /**
     * 当前乘客
     */
    public Integer nowPassenger;

    /**
     * 上车乘客
     */
    public Integer upPassenger;

    /**
     * 下车乘客
     */
    public Integer downPassenger;

    public Double getNowTime() {
        return nowTime;
    }

    public void setNowTime(Double nowTime) {
        this.nowTime = nowTime;
    }

    public boolean isStartStation() {
        return isStartStation;
    }

    public void setStartStation(boolean startStation) {
        isStartStation = startStation;
    }

    public boolean isEndStation() {
        return isEndStation;
    }

    public void setEndStation(boolean endStation) {
        isEndStation = endStation;
    }

    public Integer getNowStationNumber() {
        return nowStationNumber;
    }

    public void setNowStationNumber(Integer nowStationNumber) {
        this.nowStationNumber = nowStationNumber;
    }

    public Integer getNowPassenger() {
        return nowPassenger;
    }

    public void setNowPassenger(Integer nowPassenger) {
        this.nowPassenger = nowPassenger;
    }

    public Integer getUpPassenger() {
        return upPassenger;
    }

    public void setUpPassenger(Integer upPassenger) {
        this.upPassenger = upPassenger;
    }

    public Integer getDownPassenger() {
        return downPassenger;
    }

    public void setDownPassenger(Integer downPassenger) {
        this.downPassenger = downPassenger;
    }
}
