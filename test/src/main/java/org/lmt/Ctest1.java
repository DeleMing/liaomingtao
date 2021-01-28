package org.lmt;

/**
 * @author: LiaoMingtao
 * @date: 2021/1/9
 */
public class Ctest1 {


    public static void main(String[] args) {
        String a = "poststr,orderdate,ordersno,custid,custname,fundid,moneytype,orgid,secuid,bsflag,orderid,reporttime,opertime,market,stkcode,stkname,orderprice,orderqty,orderfrzamt,matchqty,matchamt,cancelqty,orderstatus,seat,cancelflag,stktype,creditid,creditflag,bsflag2,lendflag,contractsno,nightflag;";
        String b = "2&_2=20170630!85019,20170630,85019,280033400970,高汝松,280063400970,0,2800,0600062422,D,GE006936,9532946,9532945,0,300673,佩蒂股份,22.340,20000,.00,0,.00,0,2,390378,F,0,0,3,0D,0,,0&_3=20170630!85162,20170630,85162,280033400970,高汝松,280063400970,0,2800,E025964601,D,0130006761,9533315,9533314,1,732595,东尼申购,13.010,10000,.00,0,.00,0,2,21818,F,0,0,3,0D,0,,0&_4=20170630!133192,20170630,133192,280033400970,高汝松,280063400970,0,2800,E025964601,S,0130010591,10174747,10174746,1,601169,北京银行,9.220,2000,.00,0,.00,0,2,21818,F,0,0,1,0S,0,,0&_EORS_2=4&_RC=4&_CC=32&_TL=3:1;32:3;";
        String[] splitA = a.split(",");
        System.out.println(splitA.length);
        String[] splitB = b.split(",");
        System.out.println(splitB.length);
    }
}
