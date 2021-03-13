package org.lmt.desensitization;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/19
 */
@Slf4j
public class Ctest4 {

    public static void main(String[] args) {
        String key = "custid, orgid, brhid, status, fundid, fundname, moneytype, custtype, fundassetbal,\n" +
                "fundassetavl, fundassettotal, fundassettotalnew,fundasset, fundassetstktotal, fundassetstk, fundasaetggt, fundassetstkwxxj,  \n" +
                "fundassetbondtotal, fundassetbond, fundassetrzgh, fundassetrqgh, fundassetmatchrqgh, fundasaetstkzy, fundassetrzcs, fundassetrqcs,  \n" +
                "fundassetstkxyzy, fundassetadjust, fundassetadjust_xsf, fundassetadjust_qt, fundassetoftotal, fundassetof, fundassetbjhgsh,  \n" +
                "fundassetbjhgsz, fundassetofuncome, fundassetx jgj, fundassetzfzz, fundassetjdlsec, fundassetfjjj, fundassetcurdayrqgh,  \n" +
                "fundassetcurdayrzgh, fundassetotcend, debtstotal, zy_mtkvalue, zy_debts, en_mktvalue, en_debts, zywymktvalue, sumprofitamt;";
        String value = "2&_2=4105641,3607,3607,0,2800713,潘欣玲,0,0,1000.00,158069.42,825587.15,825587.15,158069.42,331334.56,302463.77,0.00,0.00,\n" +
                "779.99,779.99,0.00,0.00,0.00,0.00,0.00,0.00,0.00,28090.80,0.00,28090.80,336183.17,336183.17,0.00,0.00,0.00,55.98,0.00,0.00,0.00,\n" +
                "157013.44,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,-63104.28&_EORS_2=2&_RC=2&_CC=48&_TL=3:1;48:1;";
        String[] split = key.split(",");
        String[] split1 = value.split(",");
        log.info("字段长度:{}", split.length);
        log.info("字段长度:{}", split1.length);
    }
}
