package org.lmt.streamx;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/5
 */
@Getter
@Setter
public class GetJobDto {

    /**
     *         {
     *             "id": "004e2250132126cce080d72490d80a4e",
     *             "name": "log2es_dwd_default_log",
     *             "state": "RUNNING",
     *             "startTime": 1612434076124,
     *             "endTime": -1,
     *             "duration": 73190608
     *         },
     */
    private String id;
    private String name;
    private String state;
    private String startTime;
    private String endTime;
    private String duration;
}
