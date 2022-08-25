package org.lmt;


import org.lmt.http.ErrorType;

/**
 * 业务处理状态码定义基本类
 * <p>
 * 日志码位数	         7 位整数，前两位表示产品代号，后5为各产品自行分配
 * 平台类	             以1开头（10-29）
 * 公共服务	             以5开头（50-79）
 * SaaS后台	             以3开头（30-49）
 * 第三方SaaS后台	     以8开头（80-99）
 * 0	                 请求成功
 * -1	                 系统繁忙
 * -2	                 未知异常
 * <p>
 * --------------------------------------------------------
 * 平台代号	平台名称	                  平台ID（2位）
 * --------------------------------------------------------
 * system       平台                           00
 * gse	        管控	                       10
 * job	        作业	                       12
 * paas	        集成	                       13
 * bcs	        容器	                       14
 * datasvr	    数据（基础模块&计算模块）	   15
 * bkm	        移动运维服务	               17
 * ai	        AI	                           18
 * datacube	    数据分析服务		           19
 * fta	        故障自愈后台	               30
 * redis		                               50
 * es		                                   51
 * MySQL		                               52
 * zk		                                   53
 * kafka		                               54
 * rabbitmq		                               55
 * icube                                       80
 * datasmart                                   81
 * --------------------------------------------------------
 *
 * @author: LiaoMingtao
 * @date: 2019/12/3
 */
public enum ResultCode implements ErrorType {
    /**
     * 状态码
     */
    SUCCESS(0, "成功"),
    SYSTEM_BUSY(-1, "系统防繁忙"),
    SYSTEM_ERROR(-2, "未知异常"),

    /* http状态码 */
    HTTP_STATUS_404(404, "未找到请求资源"),
    HTTP_STATUS_500(500, "未知服务器异常");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回code
     *
     * @return 错误码
     */
    @Override
    public Integer getCode() {
        return this.code;
    }

    /**
     * 返回message
     *
     * @return 错误描述
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public ErrorType getEnum(Integer code) {
        ResultCode[] t = ResultCode.values();
        for (ResultCode et : t) {
            if (et.getCode().equals(code)) {
                return et;
            }
        }
        return null;
    }
}
