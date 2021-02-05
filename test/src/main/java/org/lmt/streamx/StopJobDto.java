package org.lmt.streamx;

import lombok.Data;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/5
 */
@Data
public class StopJobDto {

    /**
     * {
     *         "success": true,
     *         "message": null,
     *         "path": "Canceling the job with ID c15efaeffb13d998642851beb589c761 success."
     *     }
     */
    private String success;
    private String message;
    private String path;
}
