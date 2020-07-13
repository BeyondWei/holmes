package com.shuzheng.holmes.server.statistical;


import com.shuzheng.holmes.common.utils.StringUtils;
import com.shuzheng.holmes.core.context.DealContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.deal.HolmesDeal;
import com.shuzheng.holmes.core.filter.HolmesFilter;
import com.shuzheng.holmes.server.task.FilterTask;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/holmesStatical")
public class HolmesStatical {

    /**
     * 获取最新的收集的数据
     * @param key projectUuid+"-"+logUuid
     * @return
     */
    @RequestMapping("/entrance/{key}")
    public Mono<String> getRntranceResult(@PathVariable("key") String key) {
        return Mono.just(FilterTask.results_map.get(key));
    }

    /**
     * 获取目前过滤器的状态
     * @param key filterUuid+"-"+filterName
     * @return
     */
    @RequestMapping("/filter/{key}")
    public Mono<HolmesFilter> getFilterResult(@PathVariable("key") String key) {
        return Mono.just(FilterContext.getFilter(key));
    }


    /**
     * 获取目前的处理器的状态
     * @param key dealUuid+"-"+dealName
     * @return
     */
    @RequestMapping("/deal/{key}")
    public Mono<HolmesDeal> getDealResult(@PathVariable("key") String key) {
        return Mono.just(DealContext.getDeal(key));
    }
}
