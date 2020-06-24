package core;

import com.shuzheng.holmes.core.filter.HolmesFilter;
import com.shuzheng.holmes.core.filter.HolmesFilterAbstract;
import com.shuzheng.holmes.core.filter.HolmesFilterFactory;
import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import com.shuzheng.holmes.core.filter.RegularFilter;

public class RegularFilterTest {

    public static void main(String[] args) {
        ConfigContext configContext = new ConfigContext();
        configContext.put("regular", "\\d{15}(\\d{2}[0-9xX])?");
        HolmesFilterAbstract factory = HolmesFilterFactory.factory(RegularFilter.class, configContext, FilterTypeEnums.JAVA);
        factory.register();

        HolmesFilter filter = FilterContext.getFilter("com.shuzheng.holmes.core.filter.RegularFilter");
        Object filter1 = filter.filter("qqq330483199702045415qqq");
        System.out.println(filter1.toString());
    }
}
