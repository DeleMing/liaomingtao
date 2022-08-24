import java.util.*;

/**
 * @author: LiaoMingtao
 * @date: 2022/3/20
 */
public class BusRunCount {

    public static void main(String[] args) {
        // 获取所有上行站点时间
        Integer[] upList = {5, 6, 7, 8, 4, 3, 6, 5, 6, 7, 4, 3, 6, 3};
        List<BusStation> upStationList = getBusStationMap(upList, true);
        // 获取所有下行站点时间
        Integer[] downList = {4, 7, 5, 6, 3, 4, 5, 3, 7, 4, 5, 4, 5, 4};
        List<BusStation> downStationList = getBusStationMap(downList, false);

        // 通过运行时间长度，计算大概乘客个数,上车的时间，以及上车的站点，下车的站点等
        Double runTotalTime = 300.0;
        Map<Double, Map<Integer, List<PassengerInfo>>> passengerInfoMap = getAllPassenger(runTotalTime, upStationList.size() + 1);

        // 获取所有初始车辆数目
        String[] busArr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        Map<Integer, List<BusInfo>> busInfoMap = getBusInfo(busArr);
        for (Integer upOrDown : busInfoMap.keySet()) {
            List<BusInfo> busInfoList = busInfoMap.get(upOrDown);
            for (int i = 0; i < busInfoList.size(); i++) {
                Double nowTime = i * 15.0;
                BusInfo busInfo = busInfoList.get(i);
                BusThread busThread = new BusThread(nowTime, runTotalTime, busInfo, passengerInfoMap, upStationList, downStationList);
                busThread.start();
                try {
                    busThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        String info = "公交车（NAME）: %s, 总载客人数: %s, 总行驶时间（分钟）: %s, 总行驶时间（分钟）: %s";
        for (Integer upOrDown : busInfoMap.keySet()) {
            for (BusInfo busInfo : busInfoMap.get(upOrDown)) {
                printInfo(info, busInfo);
                printDetailInfo(busInfo);
            }
        }
    }

    /**
     * 输出信息
     *
     * @param info
     * @param busInfo
     */
    public static void printInfo(String info, BusInfo busInfo) {
        String message = String.format(info, busInfo.getBusSerialNumber(), busInfo.getTotalPassengerNum(), busInfo.getTotalRunTime(), busInfo.getTotalTime());
        System.out.println(message);
    }

    /**
     * 输出详细信息
     *
     * @param busInfo
     */
    public static void printDetailInfo(BusInfo busInfo) {
        List<BusRunRecord> busRunRecordList = busInfo.getBusRunRecordList();
        if (busRunRecordList == null || busRunRecordList.isEmpty()) {
            return;
        }
        String info = "时间：%s, 动作：%s";
        for (BusRunRecord busRunRecord : busRunRecordList) {
            String timeStr = getTime(busRunRecord.getNowTime());
            if (busRunRecord.isStartStation()) {
                String message = String.format(info, timeStr, "从" + busRunRecord.getNowStationNumber() + "发车, 乘客 " + busRunRecord.getNowPassenger() + "人");
                System.out.println(message);
                continue;
            }
            if (busRunRecord.isEndStation()) {
                String message = String.format(info, timeStr, "到达终点站");
                System.out.println(message);
                continue;
            }
            if (busRunRecord.getNowStationNumber() != -1) {
                String message = String.format(info, timeStr, "到达" + busRunRecord.getNowStationNumber() + "站");
                System.out.println(message);
                continue;
            }
            if (busRunRecord.getNowStationNumber() == -1) {
                String message = String.format(info, timeStr, "下车" + busRunRecord.getDownPassenger() + "人，上车" + busRunRecord.getUpPassenger() + "人, 继续出发");
                System.out.println(message);
            }
        }
    }

    /**
     * 获取时间
     *
     * @param time
     * @return
     */
    public static String getTime(Double time) {
        if (time == 0) {
            return "00:00";
        }
        Double hour = time / 60;
        Double min = time % 60;
        return String.format("%d:%02d", hour.intValue(), min.intValue());
    }


    /**
     * 获取公交站点信息
     *
     * @param spendTime
     * @param isUp
     * @return
     */
    public static List<BusStation> getBusStationMap(Integer[] spendTime, boolean isUp) {
        int len = spendTime.length;
        List<BusStation> stationInfo = new ArrayList<>(spendTime.length);
        if (isUp) {
            for (int i = 1; i <= len; i++) {
                BusStation busStation = new BusStation(i, spendTime[i - 1].doubleValue());
                stationInfo.add(busStation);
            }
        } else {
            int j = 15;
            for (int i = 1; i <= len; i++) {
                BusStation busStation = new BusStation(j, spendTime[i - 1].doubleValue());
                stationInfo.add(busStation);
                j--;
            }
        }
        return stationInfo;
    }

    /**
     * 获取bus信息
     *
     * @param busArr
     * @return
     */
    public static Map<Integer, List<BusInfo>> getBusInfo(String[] busArr) {
        Map<Integer, List<BusInfo>> busInfoMap = new HashMap<>(2);
        int len = busArr.length;
        for (int i = 1; i <= len; i++) {
            // 判断上行还是下行
            Integer upOrDown = i / 2;

            BusInfo busInfo = new BusInfo();
            busInfo.setBroken(false);
            busInfo.setOpOrDown(upOrDown);
            busInfo.setBusSerialNumber(busArr[i - 1]);
            busInfo.setTotalTime(0.0);
            busInfo.setTotalRunTime(0.0);
            busInfo.setTotalPassengerNum(0);
            if (busInfoMap.containsKey(upOrDown)) {
                busInfoMap.get(upOrDown).add(busInfo);
            } else {
                List<BusInfo> busInfoList = new ArrayList<>();
                busInfoList.add(busInfo);
                busInfoMap.put(upOrDown, busInfoList);
            }
        }
        return busInfoMap;
    }

    /**
     * 获取乘客上车时间与下车站点
     *
     * @param time         运行时长
     * @param totalStation 总站点数
     * @return
     */
    public static Map<Double, Map<Integer, List<PassengerInfo>>> getAllPassenger(Double time, Integer totalStation) {
        Integer size = (time.intValue() / 5) + 1;
        Map<Double, Map<Integer, List<PassengerInfo>>> passerInfoMap = new HashMap<>(size);
        Double startTime = 0.0;
        Random random = new Random();
        // 每五分钟多10个乘客，计算乘客总数
        while (startTime <= time) {
            Map<Integer, List<PassengerInfo>> map = new HashMap<>(2);
            for (int i = 0; i < 10; i++) {
                PassengerInfo passengerInfo = new PassengerInfo();
                // 随机判断上行乘车还是下行乘车, 0 上行， 1 下行
                Integer upOrDown = random.nextInt(2);
                // 随机上车站点, 如果是上行，上车的站点不能是15，如果是下行，上车的站点不能是1
                Integer upStation = random.nextInt(totalStation) + 1;
                while ((upOrDown == 0 && upStation == 15) || (upOrDown == 1 && upStation == 1)) {
                    upStation = random.nextInt(totalStation) + 1;
                }
                Integer downStation = random.nextInt(upStation) + 1;
                // 随机下车站点，如果是上行，下车站点大于上车站点， 如果是下行，下车站点小于上车站点
                if (upOrDown == 0) {
                    // 剩余站点数随机一个数字，加上上车站点，就是下车的站点数
                    downStation = random.nextInt(totalStation - upStation) + 1 + upStation;
                }
                passengerInfo.setUpOrDown(upOrDown);
                passengerInfo.setGetOnBusStation(upStation);
                passengerInfo.setGetOutBugStation(downStation);
                if (map.containsKey(upOrDown)) {
                    map.get(upOrDown).add(passengerInfo);
                } else {
                    List<PassengerInfo> list = new ArrayList<>(10);
                    list.add(passengerInfo);
                    map.put(upOrDown, list);
                }
            }
            passerInfoMap.put(startTime + 5.0, map);
            // 增加五分钟
            startTime += 5;
        }
        return passerInfoMap;
    }
}
