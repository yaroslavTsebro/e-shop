package com.technograd.technograd.dao.entity;

import java.sql.ResultSet;

public interface EntityMapper<T> {
    T mapRow(ResultSet rs);
}
