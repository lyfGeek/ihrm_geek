package ${pPackage}.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "${table.name}")
public class ${ClassName} implements Serializable{

    <#list table.columns as column>
    <#if column.columnKey??>
    @Id
    </#if>
    private ${column.columnType} ${column.columnName2};
    </#list>

    <#list table.columns as column>
    public ${column.columnType} get${column.columnName2?cap_first}(){
        return this.${column.columnName2};
    }

    public void set${column.columnName2?cap_first}(${column.columnType} ${column.columnName2}){
        this.${column.columnName2}=${column.columnName2};
    }
    </#list>
}
