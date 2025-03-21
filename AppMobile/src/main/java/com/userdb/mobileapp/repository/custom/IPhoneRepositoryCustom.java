package com.userdb.mobileapp.repository.custom;

import com.userdb.mobileapp.entity.Phone;

import java.util.List;

public interface IPhoneRepositoryCustom {
    List<Phone> getPhoneByCategory(int categoryID);
}
