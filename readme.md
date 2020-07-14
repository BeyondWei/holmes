## Holmes 设计使用说明

### 模块说明

- holmes-common 
	- 程序基础模块，包括程序的基本工具类、程序实体类、常量

- holmes-dao
	- 数据源事物管理相关配置，mapper层存储

- holmes-service
	- 业务层逻辑处理、部分数据库操作处理
	
- holmes-alert
	- 告警模块，底层使用DS1.2.1的告警模块

- holmes-core
	- holmes核心模块。包含  过滤器、处理器、数据入口；配置容器，过滤器容器，处理器容器

- holmes-server
	- 数据接收，处理，分发模块终端模块。可以支持集群部署。暂时不保证数据的高可用。
	
- holmes-api （尚未开发）
	- 对接前端提供的api接口
	
- holmes-sdk  (尚未开发)
	- 集成至其他项目的sdk包


### 核心功能说明

#### 数据接收

- http 方式接口传输
	- 接口地址：`/httpEntranceTask/receive`
	- 请求方式： post + json
	- 请求头：projectUuid 项目uuid ; logUuid 日志类型uuid
	- ```
	{
    "msg": "2020-07-08 10:59:58,423 INFO "
	}```

- log4j + flume + kafka
	- 引入 sdk
		```
		<dependency>
            <groupId>org.apache.flume.flume-ng-clients</groupId>
            <artifactId>flume-ng-log4jappender-sz</artifactId>
            <version>1.8.0</version>
        </dependency>
		```
	- log4j 配置
		```
		log4j.appender.flume = org.apache.flume.clients.log4jappender.ShuzhengLog4jAppender
		log4j.appender.flume.Hostname = 192.168.5.122
		log4j.appender.flume.Port = 2222
		log4j.appender.flume.UnsafeMode = true
		log4j.appender.flume.logUuid = test
		log4j.appender.flume.projectUuid = shuzheng
		```
	
#### 过滤器

	config 中公用参数
	`hashMapKey` : 如果需要在hashMap中提取，需要增加hashMapKey，来表示hashMap中的那个值
	例如：{"split":",","equal":":","hashMapKey":"4"}

- 字符串分割过滤 SplitFilter
	- context 填写
		```
		{
		"holmesFilterName": "SplitFilter",
		"className": "com.shuzheng.holmes.core.filter.SplitFilter"
		}
		```
	- config 填写
		```
		{
		"splitKey":" ",
		"value":"4"
		}
		```
		splitKey：分割的关键字符
		value: 获取第几个字符串，从0开始；可以用`,`分割，同时获取多个字符串
		
- 字符串截取 SubstringFilter
	- context 填写
		```
		{
		"holmesFilterName": "SubstringFilter",
		"className": "com.shuzheng.holmes.core.filter.SubstringFilter"
		}
		```
	- config 填写
		```
		{
		"key1":"1,4",
		"key2":"5,8"
		}
		```
		key1,kye2: 返回的字符串的key值
		"1,4": 字符串截取的长度
		
- 正则表达式比对截取（可以传入多个表达式，返回每个匹配到的第一个表达式结果）  RegularFilter
	- context 填写
		```
		{
			"holmesFilterName": "RegularFilter",
			"className": "com.shuzheng.holmes.core.filter.RegularFilter"
		}
		```
	- config 填写
		```
		{
		"key1":"\d{15}(\d{2}[0-9xX])?"
		}
		```
		key1,kye2: 返回的字符串的key值
		
- 转JSON ToJsonFilter
	- context 填写
		```
		{
			"holmesFilterName": "ToJsonFilter",
			"className": "com.shuzheng.holmes.core.filter.ToJsonFilter"
		}
		```
	- config 填写
		```
		{
		"split":",",
		"equal":":"
		}
		```
		split: 分隔关键字符
		equal: key 和 value分隔字符

- 提取json 中的某个值 JsonFilter
	- context 填写
		```
		{
			"holmesFilterName": "JsonFilter",
			"className": "com.shuzheng.holmes.core.filter.JsonFilter"
		}
		```
	- config 填写
		```
		{
		"keys":"key1,kye2"
		}
		```
		keys: 提取的key值

#### 处理器

- 写入本地文件 LogFileDeal
	- context 填写
		```
		{
		"holmesDealName": "LogFileDeal",
		"className": "com.shuzheng.holmes.core.deal.LogFileDeal"
		}
		```
	- config 填写
		```
		{
		"log.file.path":"/mnt/deal/log2"
		}
		```
		log.file.path: 日志存放地址

#### 统计功能

- 收集最新的获取到的数据
	- 接口地址: "/holmesStatical/entrance/${projectUuid}-${logUuid}"
		- projectUuid: 项目编码
		- logUuid: 日志编码
- 获取过滤器最新的状态
	- 接口地址: "/holmesStatical/filter/${filterUuid}-${filterName}"
		- filterUuid: 过滤器编码
		- filterName: 过滤器名称
- 获取处理器最新的状态
	- 接口地址: "/holmesStatical/deal/${dealUuid}-${dealName}"
		- dealUuid: 处理器编码
		- dealName: 处理器名称

#### 数据测试

- 接口地址: "/testFilter/getTestFilterResult"
- 请求方式: post + json
- 实例：
	```
	{
    "holmesFilterName": "SplitFilter",
    "className": "com.shuzheng.holmes.core.filter.SplitFilter",
    "configJson":"{\"splitKey\":\" \",\"value\":\"4\"}",
    "msg":"2020-07-08 10:59:58,423 INFO (AspceJService.java:134)- interfaceCode:M3dfkN6d88mWekf4,requestId:4831496ccf3f4d499adc80eb561a0f80,appKey:c7cb11361df94d1889162a6a03f70ab1,total:30ms,server:11ms,gateway:19ms,invokeCacheTime:0ms,proceedTime:10ms,vaildTime:{AccessValid:4ms,CacheRecordData:0ms,CacheRecordAccessValid:1ms,AuthorityValid:4ms,AppRightVaild:1ms,PowerInvokeLog:0ms,CountryPlatformInvokeValid:0ms,DataValid:1ms,ParamsValid:1ms,IdentityVerifiyValid:3ms,InvokeCountValid:2ms,LogHubInvokeValid:0ms,InterfaceRightValid:0ms},resultCode:00,resultMsg:成功,resultData:{\"data\":{\"cityNo\":\"330300\",\"extInfo\":{},\"idcardNo\":\"330324197908135200\",\"idcardType\":\"IDENTITY_CARD\",\"level\":\"green\",\"name\":\"盛陈红\",\"phone\":\"\",\"qrCode\":\"V2bdbc620bfe7e81217d2fcb6862c8a655\",\"qrCodeText\":\"https://h5.dingtalk.com/healthAct/index.html?qrCode=V2bdbc620bfe7e81217d2fcb6862c8a655#/result\",\"temperature\":{\"currentHealth\":[]},\"zjgxsj\":\"2020-02-16 16:17:41\"},\"errCode\":0,\"errMsg\":\"success\",\"success\":true,\"wrapped\":true}"
}
	```
	
	- holmesFilterName: 拦截器名称
	- className: 类名
	- configJson: 配置信息
	- msg: 传输信息

### 数据处理图

- 过滤器和处理器关系图

![](http://192.168.5.25:4999/server/../Public/Uploads/2020-07-14/5f0d572849701.png)

- 外部过滤器加载图

![](http://192.168.5.25:4999/server/../Public/Uploads/2020-07-14/5f0d58d2c6687.png)

- 数据流转图

![](http://192.168.5.25:4999/server/../Public/Uploads/2020-07-14/5f0d58ec5149a.png)

- 总体架构图

![](http://192.168.5.25:4999/server/../Public/Uploads/2020-07-14/5f0d58fc01997.png)

