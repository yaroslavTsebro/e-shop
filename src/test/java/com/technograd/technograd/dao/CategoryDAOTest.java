package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.DBManager;
import com.technograd.technograd.dao.entity.Fields;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CategoryDAOTest {

    private Category expectedCategory;
    private CategoryDAO instance;

    @Mock
    Connection mockCon;
    @Mock
    PreparedStatement mockPS;
    @Mock
    ResultSet mockRS;

    @BeforeEach
    public void setUp() throws SQLException{
        mockCon = Mockito.mock(Connection.class);
        mockPS = Mockito.mock(PreparedStatement.class);
        mockRS = Mockito.mock(ResultSet.class);
        instance = new CategoryDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int categoryId = 1;
        String nameUa = "nameUa";
        String nameEn = "nameEn";
        when(mockRS.getInt(Fields.ID)).thenReturn(categoryId);
        when(mockRS.getString(Fields.NAME_UA)).thenReturn(nameUa);
        when(mockRS.getString(Fields.NAME_EN)).thenReturn(nameEn);

        expectedCategory = new Category();
        expectedCategory.setId(categoryId);
        expectedCategory.setNameUa(nameUa);
        expectedCategory.setNameEn(nameEn);
    }

    @Test
    void getCategoryById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Category actualCategory = instance.getCategoryById(1);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            Assertions.assertEquals(expectedCategory, actualCategory);
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());
        }
    }

    @Test
    void getAllCategories() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try (MockedStatic<DBManager> ignored = mockStatic(DBManager.class)) {
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            List<Category> actualCategories = instance.getAllCategories();
            List<Category> expectedCategories = new ArrayList<>();
            expectedCategories.add(expectedCategory);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actualCategories.size());
            Assertions.assertEquals(expectedCategories, actualCategories);
        }
    }

    @Test
    void searchCategories() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            List<Category> actualCategories = instance.searchCategories("nameUa");
            List<Category> expectedCategories = new ArrayList<>();
            expectedCategories.add(expectedCategory);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actualCategories.size());
            Assertions.assertEquals(expectedCategories, actualCategories);
        }
    }

    @Test
    void createCategory() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Category category = new Category();
            category.setId(expectedCategory.getId());
            category.setNameUa(expectedCategory.getNameUa());
            category.setNameEn(expectedCategory.getNameEn());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createCategory(category);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeUpdate();
            verify(dbManager, times(1)).closeResources(any(), any());
        }
    }

    @Test
    void updateCategory() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Category category = new Category();
            category.setId(expectedCategory.getId());
            category.setNameUa(expectedCategory.getNameUa());
            category.setNameEn(expectedCategory.getNameEn());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.updateCategory(category);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeUpdate();
            verify(dbManager, times(1)).closeResources(any(), any());
        }
    }

    @Test
    void deleteById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedCategory.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            instance.deleteById(id);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}
