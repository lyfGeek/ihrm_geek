package com.geek.company.dao;

import com.geek.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ICompanyDaoTest {

    @Autowired
    private ICompanyDao companyDao;

    @Test
    public void test() {
        Company company = companyDao.findById("1").get();
        System.out.println(company);

//        companyDao.save(company);
//        companyDao.deleteById("1");
//        companyDao.findById("1");
//        companyDao.findAll();

    }
}
