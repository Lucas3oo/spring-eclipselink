package com.cepheid.cloud.skel.model;

import javax.persistence.Entity;

import org.eclipse.persistence.annotations.AdditionalCriteria;


@Entity
@AdditionalCriteria(":ADMINACCESS = 1 or this.mTenant=:TENANT")
public class Item extends AbstractEntity {

  private String mName;
  
  private Integer mTenant;
  
  public String getName() {
    return mName;
  }
  
  public void setName(String name) {
    this.mName = name;
  }

  public Integer getTenant() {
    return mTenant;
  }
  
  public void setTenant(Integer tenant) {
    mTenant = tenant;
  }
  
  @Override
  public String toString() {
    return "Item [mName=" + mName + ", mTenant=" + mTenant + "]";
  }

  
  
}
