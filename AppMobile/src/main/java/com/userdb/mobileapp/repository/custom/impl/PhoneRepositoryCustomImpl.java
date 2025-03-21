package com.userdb.mobileapp.repository.custom.impl;

import com.userdb.mobileapp.entity.Phone;
import com.userdb.mobileapp.repository.custom.IPhoneRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class PhoneRepositoryCustomImpl implements IPhoneRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Phone> getPhoneByCategory(int categoryID) {
//        // Câu truy vấn JPQL để lấy tất cả các điện thoại trong một danh mục cụ thể, nhóm theo phoneName
//        String jpql = "SELECT p.phoneName FROM Phone p WHERE p.category.categoryID = :categoryID GROUP BY p.phoneName";
//
//        // Tạo đối tượng Query từ câu lệnh JPQL
//        Query query = entityManager.createQuery(jpql);
//
//        // Gán giá trị cho tham số categoryID trong câu truy vấn
//        query.setParameter("categoryID", categoryID);
//
//        // Thực thi truy vấn và trả về danh sách các điện thoại
//        return query.getResultList();
        return null;
    }
}
