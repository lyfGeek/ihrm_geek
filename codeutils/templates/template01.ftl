欢迎您，${username}
欢迎您，#{number}
<#-- 注释。-->


<#-- if 指令。-->
<#if flag = 1>
    传入数据 = 1
<#elseif flag = 2>
    传入数据 = 2
<#else >
    传入数据 = 其他
</#if>


<#-- list 指令。-->
<#list weeks as week>
    ${week_index} ~ ${week}
</#list>


<#-- include 指令。-->
<#include "template02.ftl">


<#-- assign 指令。

在 ftl 模板中定义数据存入到 root 节点下。
-->
<#assign name="zhangsan">

${name}


<#-- 函数。-->
${username?lower_case}
