package ru.inno.matyunin.dao;


import ru.inno.matyunin.pojo.Mobile;

import javax.ejb.EJB;
import java.util.List;


public interface MobileDao {
  boolean addMobile(Mobile mobile);

  Mobile getMobileById(Integer id);

  void createTable();

  List<Mobile> getAllMobile();

  boolean updateMobile(Mobile mobile);
  boolean deleteMobileById(Integer id);

}
