package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exсeption.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntendDAO {
    private static final String SQL__CREATE_INTEND_SENDING = "INSERT INTO intend(start_date, user_id, sending_or_receiving, address, condition)" +
            "VALUES (current_timestamp, ?, 'SENDING', '', 'CART');";
    private static final String SQL__UPDATE_INTEND_CONDITION = "UPDATE intend SET condition = ? WHERE id=?;";
    private static final String SQL__CREATE_INTEND_RECEIVING = "INSERT INTO intend(start_date, supplier_id, employee_id, sending_or_receiving, address, condition)" +
            "VALUES (current_timestamp, ?, ?, 'RECEIVING', 'STORAGE', 'NEW');";

    private static final String SQL__CREATE_INTEND_RETURN = "INSERT INTO intend_return(intend_id, date, reason) VALUES (?,current_timestamp, ?);";

    private static final String SQL__FIND_INTEND_BY_ID = "SELECT * FROM intend WHERE id=?;";
    private static final String SQL__FIND_CART_BY_ID = "SELECT * FROM intend WHERE user_id=? AND condition='CART';";
    private static final String SQL__FIND_INTENDS_BY_USER_ID = "SELECT * FROM intend WHERE user_id=? AND condition!='CART';";
    private static final String SQL__CHANGE_CART_INTO_INTEND = "UPDATE intend SET start_date = current_timestamp, condition = 'NEW', address=? WHERE id=?;";
    private static final String SQL__FIND_RECEIVING_INTEND = "SELECT * FROM intend WHERE sending_or_receiving = 'SENDING';";
    private static final String SQL__FIND_SENDINGS_INTEND = "SELECT * FROM intend WHERE sending_or_receiving = 'SENDING' and condition != 'CART' and (end_date >= ? and end_date <= ?);";
    private static final String SQL__FIND_SENDING_INTEND = "SELECT * FROM intend";
    private static final String SQL__UPDATE_CONDITION = "UPDATE intend SET";
    private static final String SQL__UPDATE_COUNT_BY_ID = "UPDATE product SET count = ? WHERE id = ?;";
    private static final String SQL__GET_ALL_EXPIRED_CARTS = "SELECT id from intend WHERE condition = 'CART' AND EXTRACT(DAY FROM CURRENT_TIMESTAMP - start_date) >= ?;";
    private static final String SQL__DELETE_CART_BY_ID = "DELETE FROM intend WHERE id=?;";
    private static final String SQL__DELETE_LI_BY_ID = "DELETE FROM list_intend WHERE intend_id=?;";
    public List<Intend> getAllCartsWithExpiredDateByDays(int countOfDays) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Intend> intendList = new ArrayList<>();
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__GET_ALL_EXPIRED_CARTS);
            preparedStatement.setInt(1, countOfDays);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Intend intend = new Intend();
                intend.setId(rs.getInt(Fields.ID));
                intendList.add(intend);
            }
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
        return intendList;
    }

    public void deleteCartAndListIntendById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__DELETE_LI_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL__DELETE_CART_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }


    public void createIntendSending(int userId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_INTEND_SENDING);
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public String buildUpdateConditionQuery(String condition){
        String base = SQL__UPDATE_CONDITION;
        if(condition.equals(Condition.IN_WAY.toString())){
            base += " condition='IN_WAY'";
        } else if (condition.equals(Condition.ACCEPTED.toString())) {
            base += " condition='ACCEPTED', start_date = current_timestamp";
        } else if (condition.equals(Condition.DENIED.toString())) {
            base += " condition='DENIED' , end_date = current_timestamp";
        } else if (condition.equals(Condition.TURNED_BACK.toString())) {
            base += " condition='TURNED_BACK' , end_date = current_timestamp";
        } else if (condition.equals(Condition.COMPLETED.toString())) {
            base += " condition='COMPLETED', end_date = current_timestamp";
        }
        base += " WHERE id=?;";
        return base;
    }
    public void updateConditionAccepted(String query, int intendId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, intendId);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL__FIND_INTEND_BY_ID);
            preparedStatement.setInt(1, intendId);
            IntendMapper mapper = new IntendMapper();
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Intend i = mapper.mapRow(rs);
                intend = i;
            }

            for (int i = 0; i < intend.getListIntends().size(); i++) {
                int productId = intend.getListIntends().get(i).getProduct().getId();
                int productCount = intend.getListIntends().get(i).getProduct().getCount();
                int intendedCount = intend.getListIntends().get(i).getCount();

                int rest = productCount - intendedCount;
                if(rest < 0){
                    throw new DBException();
                }

                preparedStatement = connection.prepareStatement(SQL__UPDATE_COUNT_BY_ID);
                preparedStatement.setInt(1, rest);
                preparedStatement.setInt(2, productId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public void updateConditionTurnedBack(String query, int intendId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, intendId);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL__FIND_INTEND_BY_ID);
            preparedStatement.setInt(1, intendId);
            IntendMapper mapper = new IntendMapper();
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Intend i = mapper.mapRow(rs);
                intend = i;
            }

            for (int i = 0; i < intend.getListIntends().size(); i++) {
                int productId = intend.getListIntends().get(i).getProduct().getId();
                int productCount = intend.getListIntends().get(i).getProduct().getCount();
                int intendedCount = intend.getListIntends().get(i).getCount();

                int rest = productCount + intendedCount;
                if(productCount > rest){
                    throw new DBException();
                }

                preparedStatement = connection.prepareStatement(SQL__UPDATE_COUNT_BY_ID);
                preparedStatement.setInt(1, rest);
                preparedStatement.setInt(2, productId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public void updateConditionTurnedBackFromUserWithReason(String query, int intendId, String reason) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, intendId);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL__FIND_INTEND_BY_ID);
            preparedStatement.setInt(1, intendId);
            IntendMapper mapper = new IntendMapper();
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Intend i = mapper.mapRow(rs);
                intend = i;
            }

            for (int i = 0; i < intend.getListIntends().size(); i++) {
                int productId = intend.getListIntends().get(i).getProduct().getId();
                int productCount = intend.getListIntends().get(i).getProduct().getCount();
                int intendedCount = intend.getListIntends().get(i).getCount();

                int rest = productCount + intendedCount;
                if(productCount > rest){
                    throw new DBException();
                }

                preparedStatement = connection.prepareStatement(SQL__UPDATE_COUNT_BY_ID);
                preparedStatement.setInt(1, rest);
                preparedStatement.setInt(2, productId);
                preparedStatement.execute();
            }

            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_INTEND_RETURN);
            preparedStatement.setInt(1, intendId);
            preparedStatement.setString(2, reason);
            preparedStatement.execute();

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }


    public void updateConditionTurnedBackFromUser(String query, int intendId, String reason) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, intendId);
            preparedStatement.execute();

            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_INTEND_RETURN);
            preparedStatement.setInt(1, intendId);
            preparedStatement.setString(2, reason);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public void updateCondition(String query, int intendId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, intendId);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public List<Intend> findAllReceivings() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Intend> intendList = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_RECEIVING_INTEND);
            IntendMapper mapper = new IntendMapper();
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                intendList.add(mapper.mapRow(rs));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return intendList;
    }

    public List<Intend> findAllReceivingsForReport(Timestamp date1, Timestamp date2) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Intend> intendList = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_SENDINGS_INTEND);
            preparedStatement.setTimestamp(1, date1);
            preparedStatement.setTimestamp(2, date2);
            IntendMapper mapper = new IntendMapper();
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                intendList.add(mapper.mapRow(rs));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return intendList;
    }


    public List<Intend> findAllIntendsFormQueryBuilder(String query) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Intend> intendList = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Intend intend = new Intend();
                intend.setId(rs.getInt(Fields.ID));
                intend.setStartDate(rs.getTimestamp(Fields.INTEND_START_DATE));
                intend.setEndDate(rs.getTimestamp(Fields.INTEND_END_DATE));
                intend.setUserId(rs.getInt(Fields.INTEND_USER_ID));
                intend.setSupplierId(rs.getInt(Fields.INTEND_SUPPLIER_ID));
                intend.setEmployeeId(rs.getInt(Fields.INTEND_EMPLOYEE_ID));
                intend.setSendingOrReceiving(rs.getString(Fields.INTEND_SENDING_OR_RECEIVING));
                intend.setAddress(rs.getString(Fields.INTEND_ADDRESS));
                intend.setCondition(rs.getString(Fields.INTEND_CONDITION));
                intendList.add(intend);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return intendList;
    }

    public List<Intend> findIntendByUserId(int userId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Intend> intendList = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_INTENDS_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Intend intend = new Intend();
                intend.setId(rs.getInt(Fields.ID));
                intend.setStartDate(rs.getTimestamp(Fields.INTEND_START_DATE));
                intend.setEndDate(rs.getTimestamp(Fields.INTEND_END_DATE));
                intend.setUserId(rs.getInt(Fields.INTEND_USER_ID));
                intend.setSupplierId(rs.getInt(Fields.INTEND_SUPPLIER_ID));
                intend.setEmployeeId(rs.getInt(Fields.INTEND_EMPLOYEE_ID));
                intend.setSendingOrReceiving(rs.getString(Fields.INTEND_SENDING_OR_RECEIVING));
                intend.setAddress(rs.getString(Fields.INTEND_ADDRESS));
                intend.setCondition(rs.getString(Fields.INTEND_CONDITION));
                intendList.add(intend);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return intendList;
    }

    public void changeCartIntoIntend(String address, int cartId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CHANGE_CART_INTO_INTEND);
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, cartId);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    public void updateIntendCondition(int id, Condition condition) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_INTEND_CONDITION);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, condition.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public void createIntendReceiving(Intend intend) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_INTEND_RECEIVING);
            preparedStatement.setInt(1, intend.getSupplierId());
            preparedStatement.setInt(2, intend.getEmployeeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    public Intend findIntendById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_INTEND_BY_ID);
            IntendMapper mapper = new IntendMapper();
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                intend = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return intend;
    }
    public Intend findCartById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_CART_BY_ID);
            IntendMapper mapper = new IntendMapper();
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                intend = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return intend;
    }

    public String viewIntendsQueryBuilder(String condition, String sendingOrReceiving){
        String queryBase = SQL__FIND_SENDING_INTEND;

        if(sendingOrReceiving.equals(SendingOrReceiving.SENDING.toString())){
            queryBase += " WHERE sending_or_receiving = 'SENDING'";
        } else {
            queryBase += " WHERE sending_or_receiving = 'RECEIVING'";
        }
        if(condition == null){

        }  else if(condition.equals(Condition.CART.toString())){
            queryBase += " AND condition = 'CART'";
        } else if (condition.equals(Condition.NEW.toString())) {
            queryBase += " AND condition = 'NEW'";
        } else if (condition.equals(Condition.ACCEPTED.toString())) {
            queryBase += " AND condition = 'ACCEPTED'";
        } else if (condition.equals(Condition.TURNED_BACK.toString())) {
            queryBase += " AND condition = 'TURNED_BACK'";
        } else if (condition.equals(Condition.COMPLETED.toString())) {
            queryBase += " AND condition = 'COMPLETED'";
        } else if (condition.equals(Condition.IN_WAY.toString())) {
            queryBase += " AND condition = 'IN_WAY'";
        } else if (condition.equals(Condition.DENIED.toString())) {
            queryBase += " AND condition = 'DENIED'";
        }

        queryBase += " ORDER BY start_date";
        queryBase += ";";
        return queryBase;
    }


    private static class IntendMapper implements EntityMapper<Intend>{
        @Override
        public Intend mapRow(ResultSet rs) {
            try {
                Intend intend = new Intend();
                intend.setId(rs.getInt(Fields.ID));
                intend.setStartDate(rs.getTimestamp(Fields.INTEND_START_DATE));
                intend.setEndDate(rs.getTimestamp(Fields.INTEND_END_DATE));
                intend.setUserId(rs.getInt(Fields.INTEND_USER_ID));
                intend.setSupplierId(rs.getInt(Fields.INTEND_SUPPLIER_ID));
                intend.setEmployeeId(rs.getInt(Fields.INTEND_EMPLOYEE_ID));
                intend.setSendingOrReceiving(rs.getString(Fields.INTEND_SENDING_OR_RECEIVING));
                intend.setAddress(rs.getString(Fields.INTEND_ADDRESS));
                intend.setCondition(rs.getString(Fields.INTEND_CONDITION));
                intend.setListIntends(new ListIntendDAO().getAllListIntendsByIntendId(rs.
                        getInt(Fields.ID)));
                return intend;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
