package org.lmt.desensitization;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/18
 */
@Slf4j
public class Ctest2 {

    public static void main(String[] args) {
        String temp = "20201227-202917Req：NodeId=8005， Queue Id=106， MsgId=800000030185A96C23C20E 1 \n" +
                "Buf= 012000000000010000001000 000000000000123456789012  KC XP 00..GV2gODkBbGg=\n" +
                "254_CA=2.3&_ENDIAN=0&func id=410301&cust id=-1&cust orgid=11036trdpwd=+0K8mU 025 \n" +
                "input type=zk input id= 144444444\n" +
                "Ans：·NodeId=8005， Queue Id=106， MsgId=800000030185A96C23C20E1D， Len=283，\n" +
                "Buf= 01300000000001000 000308005080050800512345  6789012KCXP00GV2gODkBbGg=5FE 8 \n" +
                "254CA=2.3&SYSID=8005EENDIAN=O&_RS_1=MESSAGE； 3； LEVEL， CODE， MSG；\n" +
                "161=2， -410301060， -410301060[-980023090] 资金帐号不存在*144444444Y&_E ORS\n" +
                "D， Len=278，\n" +
                "aa..\n" +
                "100000\n" +
                "..-410301.001520000001103.\n" +
                "01510\n" +
                "154004\n" +
                ") &net addr=0050569DCEBD&orgid=1103&oper way=0fext= 0\n" +
                "7E1D008005000002000751410301.001570000001103\n" +
                "1=1&RC=16_cc=3G_TL=3：1；";
        temp = temp.replace(".", " ");
        temp = temp.replace("：", ":");
        temp = temp.replace("，", ",");
        temp = temp.replace("；", ";");
        log.info(temp);

    }
}
