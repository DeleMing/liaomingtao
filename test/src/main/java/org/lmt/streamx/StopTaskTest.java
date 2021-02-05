package org.lmt.streamx;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.lmt.http.HttpClient;
import org.lmt.req.Req;
import org.lmt.resp.Resp;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/5
 */
@Slf4j
public class StopTaskTest {

    public static void main(String[] args) {
        String url = "http://192.168.70.85:9002";
        String getJobs = "/api/jobs";
        String stopJobs = "/api/jobs/cancel/%s";
        String status = "RUNNING";
        Resp<List<GetJobDto>> getJobDtoResp = new HttpClient(url).invokeGet(getJobs,
                new Req(), new TypeReference<Resp<List<GetJobDto>>>() {
                });
        if (getJobDtoResp.hasSuccess()) {
            List<GetJobDto> data = getJobDtoResp.getData();
            List<String> jobIds = data.stream().filter(item -> status.equals(item.getState())).map(GetJobDto::getId).collect(Collectors.toList());
            for (String jobId : jobIds) {
                String stopApi = String.format(stopJobs, jobId);
                Resp<StopJobDto> stopJobDtoResp = new HttpClient(url).invokeGet(stopApi, new Req(), new TypeReference<Resp<StopJobDto>>() {
                });
                if (stopJobDtoResp.hasFail()) {
                    log.error("停止任务失败：{}", JSON.toJSONString(stopJobDtoResp));
                }
            }
        }

    }
}
