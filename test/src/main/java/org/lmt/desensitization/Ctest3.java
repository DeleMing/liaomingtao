package org.lmt.desensitization;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/18
 */
@Slf4j
public class Ctest3 {

    public static void main(String[] args) {
        String temp = "20201227-202920·Req：NodeId=6007， Queue Id=304， Ms qId=6000000604B2949123C20B 24..Len=346..\n" +
                "Buf= 01280000000000000000100 000000000000040F2E92B8E 7 AKC XP 00..GV2gODkBbGg=.\n" +
                ".410570.00220000000..\n" +
                "010_CA=2.36_ENDIAN=0&cust id=000004105641&cust orgid=3607&ext=&func id=410570&fun did=0002800713kg_station addr= 13517951826\n" +
                "6  money type=6netaddr=13517951826koperway=&orgid=3607&terminal info=&trd pwd=G|8|6l3B&server id=6sg_server id=6.\n" +
                "Ans：NodeId=6007， Queue Id=304， MsgId=6000000604B2949123C20E 24， Len=1284， Buf= 01300000000000000000306 007060070600740F2E92B8B 7 ARC XP 00.\n" +
                "GV2gODkBbGg=5FE87E 20006007000005013147410570.01158000000.........010_CA=2.3&_8YSID=6007G_ENDIAN=0&_RS_1=MESSAGE； 3； LEVEL， CODE， MSG；\n" +
                "16_1=0， 0， 客户资产总值查询&_E ORS_1=16_R 8_2=DATA； 48； cust id， orgid， br hid， status， fun did， fund name， money type， cust type， fund asset bal，\n" +
                "fund asset avl， .fund asset total， fund asset total new， fund asset， fund assets tk total， fund assets tk， fund as a et ggt， fund assets tk wx xj， .\n" +
                "fund asset bond total， fund asset bond， fund asset rz gh， fund asset rq gh， fund asset match rq gh， fundasaetstkzy， fund asset rz cs， ·fund asset rq cs， .\n" +
                "fund assets tk xyz y， fund asset adjust， fund asset adjust_xsf， fund asset adjust_qt， fund asset of total， fund asset of， fund asset bjh gsh， .\n" +
                "fund asset bj hg sz， fund asset of un come， fund asset x jgj， fund asset zf zz， ·fund asset jdl sec， fund asset f jj， fund asset cur day rq gh， .\n" +
                "fund asset cur day rz gh， fund asset otc end， debts total， zy_mtk value， zy_debts， en_mkt value， en_debts， zy wy mkt value， ·sum profit amt；\n" +
                "2&2=4105641，3607，3607，0，2800713，潘欣玲，0，0，1000.00，158069.42，825587.15，825587.15，158069.42，331334.56，302463.77，0.00，0.00，\n" +
                "779.99，779.99，0.00，0.00，0.00，0.00，0.00，0.00，0.00，28090.80，0.00，28090.80，336183.17，336183.17，0.00，0.00，0.00，55.98，0.00，0.00，0.00，\n" +
                "157013.44， 0.00， 0.00， 0.00， 0.00， 0.00， 0.00， 0.00， 0.00， -63104.286_E ORS_2=26_RC=2&_Cc=486_TL=3：1； 48：1；";
        temp = temp.replace(".", " ");
        temp = temp.replace("：", ":");
        temp = temp.replace("，", ",");
        temp = temp.replace("；", ";");
        log.info(temp);

    }
}
