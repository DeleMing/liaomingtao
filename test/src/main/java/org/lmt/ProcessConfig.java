package org.lmt;

import lombok.Data;

import java.util.List;

/**
 * @author: LiaoMingtao
 * @date: 2021/5/29
 */
@Data
public class ProcessConfig {

    private String processName;

    private List<ProcessParamConfig> processParam;

    @Data
    static
    class ProcessParamConfig {
        private String key;

        private String value;

        private String expression;

        private String condition;
    }
}
