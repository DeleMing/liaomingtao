/**
 * @author: LiaoMingtao
 * @date: 2022/3/20
 */
public class BusStation {

    /**
     * 站点序号
     */
    private Integer serialNumber;

    /**
     * 下个站点耗时
     */
    private Double nextStationSpendTime;

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getNextStationSpendTime() {
        return nextStationSpendTime;
    }

    public void setNextStationSpendTime(Double nextStationSpendTime) {
        this.nextStationSpendTime = nextStationSpendTime;
    }

    public BusStation(Integer serialNumber, Double nextStationSpendTime) {
        this.serialNumber = serialNumber;
        this.nextStationSpendTime = nextStationSpendTime;
    }
}
