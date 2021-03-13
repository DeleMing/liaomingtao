package org.lmt.desensitization;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/18
 */
@Slf4j
public class Ctest1 {

    public static void main(String[] args) {
        String temp = "20201227-235934.\n" +
                "Req：：NodeId=5007， Queue Id=430， MagId=50000006045676B123C23F 68， Len=374， .\n" +
                "Buf= 012800000000000000001000 0000000000000A94EF138F7F KC XP 00.Gv2gODkBbGq=\n" +
                "141_CA=2.3G_ENDIAN= 0\n" +
                "&bank pwd=\n" +
                "&client flag= 02021/00\n" +
                "&cust id=6\n" +
                "6custorgid= 4301\n" +
                "kext=1/1918：28\n" +
                "&func id= 410305\n" +
                "6g_station addr=6CAE8B62900A22\n" +
                "&input id=10586706&input info=\n" +
                "k input type=z\n" +
                "&net addr=6CAE8B62900A\n" +
                "&oper way=E\n" +
                "&orgid= 4301\n" +
                "&terminal info=\n" +
                "&trd pwd=llu*； @40z@ 25\n" +
                "&server id=5g_server id=5.\n" +
                "410305.00248000000.\n" +
                "176.179.1\n" +
                "1/01/1918：2830\n" +
                "Ans：·NodeId=5007， Queue Id=430， MsgId=50000006045676B123C23F 68， Len=1023， Buf= 013000000000000000003050 0705007050070A94EF138F7F KCX\n" +
                "GVZgODkBbGg=5FE8AF 6600500700000301373941030500897000000.141_CA=2.36_SYSID=50076_ENDIAN=06_RS_1=MESSAGE； 3； LEVEL， CODE， N\n" +
                "16_1=0， 0， 外围登陆成功&E ORS_1=1&RS_2=DATA； 35；\n" +
                "market， sec uid， name， cust id， cust prop， fun did， cust name， orgid， bank code， timeout flag， identity sign，\n" +
                "auth level， pwd err times， agt cust id， credit fund， credit flag， net addr， cust kind， cust group， fund limit， bank state，\n" +
                "custody state， clear state， last print date， last print way， print ip， login date， login time， output info， oper way，\n" +
                "id no end date， id no begin date， out msg， cust status， super flag；\n" +
                "2&2=1， A 641393224， 刘梦明， 55132633， 0， 10586706， 刘梦明， 4301， 6004， 0， ， 0， 0， 55132633， 0， 0， 6CAE8B62900A， E， c， ， 1， o， 0，\n" +
                "20200211， 0， ， 20201227， 21205975， ， 012347ADEFGIMNVYcu， 20270906， 20170906， 您的交易密码已经321天未修改， 0， 1&3=2， 0278160114，I\n" +
                "刘梦明， 55132633， 0， 10586706， 刘梦明， 4301， 6004， 0， 0.0， 55132633， 0.06CAE8B62900A， E， c， ， 1， 0， 0， 20200211， 0.2020122721205975dows\n" +
                "012347ADEFGIMNVYcu， 20270906， 20170906， 您的交易密码已经321天未修改， 0， 1&_E ORS_2=3&_RC=3&_cc=35&_TL=3：1； 35：2；";

        temp = temp.replace(".", " ");
        temp = temp.replace("：", ":");
        temp = temp.replace("，", ",");
        temp = temp.replace("；", ";");
        log.info(temp);

    }
}
