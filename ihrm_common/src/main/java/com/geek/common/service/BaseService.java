package com.geek.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BaseService<T> {

    protected Specification<T> getSpecification(String companyId) {
        // 只查询 companyId。
        // 很多地址都需要根据 companyId 查询。
        // 很多对象中都具有 companyId。
        Specification<T> specification = new Specification<T>() {
            /**
             * 用户构造查询条件。
             *
             * @param root            包含了所有对象属性。
             * @param criteriaQuery   一般不用。
             * @param criteriaBuilder 构造查询条件。
             * @return
             */
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 根据企业 id 查询。
                return criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
            }
        };
        return specification;
    }
}
