package com.shuzheng.holmes.server;

import com.alibaba.fastjson.JSONObject;
import com.shuzheng.holmes.common.utils.ClassloadUtils;
import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import com.shuzheng.holmes.core.filter.HolmesFilterAbstract;
import com.shuzheng.holmes.core.filter.HolmesFilterFactory;
import com.shuzheng.holmes.server.dto.FilterDto;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("testFilter")
public class TestFilter {

    @RequestMapping("getTestFilterResult")
    public Mono<Object> getTestFilterResult(@RequestBody FilterDto filterDto) {
        String holmesTempName = filterDto.getHolmesFilterName() + "-temp";
        HolmesFilterAbstract holmesFilter;
        ConfigContext configContext = new ConfigContext();
        configContext.getJsonProperties(filterDto.getConfigJson());
        Object results = "";
        // 生产过滤器
        try {
            if (!FilterContext.isExist(holmesTempName)) {
                Class<HolmesFilterAbstract> aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass(filterDto.getClassName());
                if (aClass == null) {
                    aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromJavaFile(
                            filterDto.getClassName(),
                            filterDto.getJavaPath(),
                            filterDto.getClassPath());
                }
                HolmesFilterFactory.createAndRegister(aClass, holmesTempName, configContext, FilterTypeEnums.JAVA);
            } else {
                return Mono.just("过滤器名称重复，创建临时过滤器失败");
            }
            // 执行过滤器
            holmesFilter = (HolmesFilterAbstract) FilterContext.getFilter(holmesTempName);
            if (!StringUtils.isEmpty(configContext.getString("hashMapKey"))) {
                Map map = JSONObject.parseObject(filterDto.getMsg(), Map.class);
                results = holmesFilter.run(map);
            }else {
                results = holmesFilter.run(filterDto.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FilterContext.remove(holmesTempName);
        }
        return Mono.just(results);
    }
}
