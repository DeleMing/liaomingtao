import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2022/3/20
 */
public class BusThread extends Thread {

    public Double startTime;

    public Double runTotalTime;

    public BusInfo busInfo;

    public Map<Double, Map<Integer, List<PassengerInfo>>> passengerInfoMap;

    public List<BusStation> upStationList;

    public List<BusStation> downStationList;

    public BusThread(Double startTime, Double runTotalTime, BusInfo busInfo, Map<Double, Map<Integer, List<PassengerInfo>>> passengerInfoMap, List<BusStation> upStationList, List<BusStation> downStationList) {
        this.startTime = startTime;
        this.runTotalTime = runTotalTime;
        this.passengerInfoMap = passengerInfoMap;
        this.busInfo = busInfo;
        this.upStationList = upStationList;
        this.downStationList = downStationList;
    }

    @Override
    public void run() {
        while (startTime <= runTotalTime) {
            List<BusStation> stationMap = busInfo.getOpOrDown() == 0 ? upStationList : downStationList;
            startTime = runBus(busInfo, stationMap, startTime, passengerInfoMap, runTotalTime);
        }
        busInfo.setTotalRunTime(startTime);
    }

    /**
     * bus运行状况
     *
     * @param busInfo
     * @param busStationList
     * @param startTime
     */
    public static Double runBus(BusInfo busInfo, List<BusStation> busStationList, Double startTime, Map<Double, Map<Integer, List<PassengerInfo>>> passengerInfoMap, Double totalTime) {
        boolean start = true;
        for (BusStation busStation : busStationList) {
            if (startTime > totalTime) {
                break;
            }
            // 上车人数、下车人数
            Integer getOnSize = 0;
            Integer getOutSize = 0;
            // 当前站点序号
            Integer busStationSerialNumber = busStation.getSerialNumber();
            if (!start) {
                // 如果不是始发站，记录当前到达站点序号与时间
                createBusRecord(busInfo, startTime, busStationSerialNumber, 0, getOnSize, getOutSize);
            }
            // 前往下一站耗时
            Double spendTime = 0.0;
            // 判断当前是否有乘客下车
            List<PassengerInfo> passengerInfoList = busInfo.getPassengerInfoList();
            if (passengerInfoList != null && !passengerInfoList.isEmpty()) {
                // 不等于空，说明有乘客，进行乘客下车操作
                Iterator<PassengerInfo> iterator = passengerInfoList.iterator();
                while (iterator.hasNext()) {
                    PassengerInfo passengerInfo = iterator.next();
                    if (!busStationSerialNumber.equals(passengerInfo.getOutBugStation)) {
                        continue;
                    }
                    getOutSize++;
                    // 下车
                    iterator.remove();
                    // 增加耗时10秒
                    spendTime += 0.6;
                }
            }
            // 判断当前车上乘客是否达到上限
            if (passengerInfoList != null && passengerInfoList.size() >= 29) {
                // 此车达到人数上线，不再进行上车操作，直接开往下一站
                startTime += spendTime;
                createBusRecord(busInfo, startTime + spendTime, -1, passengerInfoList.size(), getOnSize, getOutSize);
                continue;
            }
            // 判断当前时间是否有乘客上车
            for (Double time : passengerInfoMap.keySet()) {
                if (time > startTime) {
                    continue;
                }
                // 大于0, 说明有乘客上车，循环判断哪些乘客是否在此站点上车
                Map<Integer, List<PassengerInfo>> map = passengerInfoMap.get(time);
                if (map != null && !map.isEmpty() && map.containsKey(busInfo.getOpOrDown())) {
                    List<PassengerInfo> passengerInfoListOne = map.get(busInfo.getOpOrDown());
                    if (passengerInfoListOne == null || passengerInfoListOne.isEmpty()) {
                        continue;
                    }
                    // 不等于空，说明有乘客，进行乘客下车操作
                    Iterator<PassengerInfo> iterator = passengerInfoListOne.iterator();
                    while (iterator.hasNext()) {
                        PassengerInfo passengerInfo = iterator.next();
                        if (!busStationSerialNumber.equals(passengerInfo.getOnBusStation)) {
                            continue;
                        }
                        // 上车
                        if (passengerInfoList == null) {
                            passengerInfoList = new ArrayList<>();
                        }
                        passengerInfoList.add(passengerInfo);
                        busInfo.setTotalPassengerNum(busInfo.getTotalPassengerNum() + 1);
                        // 此时刻等车人数减少
                        iterator.remove();
                        getOnSize++;
                        // 增加耗时10秒
                        spendTime += 0.6;
                    }
                }
            }
            if (start) {
                // 生成从起始站出发记录
                createStartOrEndRecode(busInfo, startTime, busStationSerialNumber, passengerInfoList == null ? 0 : passengerInfoList.size(), true);
            }
            start = false;
            if (spendTime != 0) {
                // 记录下车后站点时间
                createBusRecord(busInfo, startTime + spendTime, -1, passengerInfoList.size(), getOnSize, getOutSize);
            }
            Double nextStationSpendTime = busStation.getNextStationSpendTime();
            startTime += spendTime + nextStationSpendTime;
            busInfo.setTotalTime(busInfo.getTotalTime() + nextStationSpendTime);
        }
        if (startTime > totalTime) {
            return startTime;
        }
        // 创建达到终点站记录
        createStartOrEndRecode(busInfo, startTime, 0, 0, false);
        // 到达终点，所有乘客下车
        List<PassengerInfo> passengerInfoList = busInfo.getPassengerInfoList();
        if (passengerInfoList != null && !passengerInfoList.isEmpty()) {
            startTime += (0.6 * passengerInfoList.size());
            passengerInfoList.clear();
        }
        busInfo.setPassengerInfoList(passengerInfoList);
        // 修改公交上下行方向
        busInfo.setOpOrDown(busInfo.getOpOrDown() == 0 ? 1 : 0);
        return startTime;
    }

    /**
     * 生成记录
     *
     * @param busInfo
     * @param nowTime
     * @param stationNumber
     * @param nowPassengerSize
     * @param upSize
     * @param downSize
     */
    public static void createBusRecord(BusInfo busInfo, Double nowTime, Integer stationNumber, Integer nowPassengerSize, Integer upSize, Integer downSize) {
        BusRunRecord busRunRecord = new BusRunRecord();
        busRunRecord.setNowTime(nowTime);
        busRunRecord.setNowPassenger(nowPassengerSize);
        busRunRecord.setNowStationNumber(stationNumber);
        busRunRecord.setUpPassenger(upSize);
        busRunRecord.setDownPassenger(downSize);
        List<BusRunRecord> busRunRecordList = busInfo.getBusRunRecordList();
        if (busRunRecordList == null) {
            busRunRecordList = new ArrayList<>();
        }
        busRunRecordList.add(busRunRecord);
        busInfo.setBusRunRecordList(busRunRecordList);
    }

    /**
     * 生成开始记录
     *
     * @param busInfo
     * @param nowTime
     * @param stationNumber
     * @param isStart
     */
    public static void createStartOrEndRecode(BusInfo busInfo, Double nowTime, Integer stationNumber, Integer passengerNum, boolean isStart) {
        BusRunRecord busRunRecord = new BusRunRecord();
        busRunRecord.setNowTime(nowTime);
        if (isStart) {
            busRunRecord.setStartStation(true);
        } else {
            busRunRecord.setEndStation(true);
        }
        List<BusRunRecord> busRunRecordList = busInfo.getBusRunRecordList();
        if (busRunRecordList == null) {
            busRunRecordList = new ArrayList<>();
        }
        busRunRecord.setNowStationNumber(stationNumber);
        busRunRecord.setNowPassenger(passengerNum);
        busRunRecordList.add(busRunRecord);
        busInfo.setBusRunRecordList(busRunRecordList);
    }
}
