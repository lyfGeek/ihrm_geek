package ${pPackage}.dao;

import ${pPackage}.${ClassName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface I${ClassName}Dao extends JpaRepository<${ClassName}, String>, JpaSpecificationExecutor<${ClassName}> {
}
