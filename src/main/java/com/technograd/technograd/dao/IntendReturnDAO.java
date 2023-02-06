package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntendReturnDAO {
    private static final String SQL__FIND_INTEND_RETURN_BY_INTEND_ID = "SELECT * FROM intend_return WHERE intend_id=?";

    public IntendReturn findIntendReturnByIntendId(int intendId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        IntendReturn intendReturn = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_INTEND_RETURN_BY_INTEND_ID);
            IntendReturnMapper mapper = new IntendReturnMapper();
            preparedStatement.setInt(1, intendId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                intendReturn = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return intendReturn;
    }

    private static class IntendReturnMapper implements EntityMapper<IntendReturn> {
        @Override
        public IntendReturn mapRow(ResultSet rs) {
            try {
                IntendReturn intend = new IntendReturn();
                intend.setId(rs.getInt(Fields.ID));
                intend.setIntendId(rs.getInt(Fields.INTEND_RETURN_INTEND_ID));
                intend.setDate(rs.getDate(Fields.INTEND_RETURN_DATE));
                intend.setReason(rs.getString(Fields.INTEND_RETURN_REASON));
                return intend;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
