package com.source.point.sourcePoint.common;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.source.point.sourcePoint.utils.GsonUtils;

/**
 * 凡是继承该类的对象，在对象序列化按照指定的方式统一处理
 */
public class SerializedEntity implements Serializable {
	
	private static final long serialVersionUID = -286601657281683260L;

	@Override
    public String toString() {
        return GsonUtils.fromObject2Json(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj);
    }
}

