/**
 * @author: LiaoMingtao
 * @date: 2022/3/20
 */
public class PassengerInfo {

    /**
     * 上行乘车 0； 下行乘车 1；
     */
    public Integer upOrDown;

    /**
     * 上车站点
     */
    public Integer getOnBusStation;

    /**
     * 下车站点
     */
    public Integer getOutBugStation;


    public Integer getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(Integer upOrDown) {
        this.upOrDown = upOrDown;
    }

    public Integer getGetOnBusStation() {
        return getOnBusStation;
    }

    public void setGetOnBusStation(Integer getOnBusStation) {
        this.getOnBusStation = getOnBusStation;
    }

    public Integer getGetOutBugStation() {
        return getOutBugStation;
    }

    public void setGetOutBugStation(Integer getOutBugStation) {
        this.getOutBugStation = getOutBugStation;
    }
}
