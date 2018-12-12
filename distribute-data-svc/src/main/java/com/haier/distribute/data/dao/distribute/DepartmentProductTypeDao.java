package com.haier.distribute.data.dao.distribute;

import java.util.List;

import com.haier.distribute.data.model.DepartmentProductType;

public interface DepartmentProductTypeDao {

    List<DepartmentProductType> selectproduct();

    String selectcode(int productTypeId);

    List<DepartmentProductType> selectname(int productTypeId);

    DepartmentProductType getDepartment(Integer productTypeId);

}