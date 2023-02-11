package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductDAOTest {

    private ProductDAO instance;
    private Product expectedProduct;
    private List<Characteristic> expectedCharacteristics;

    @Mock
    Connection mockCon;
    @Mock
    PreparedStatement mockPS;
    @Mock
    ResultSet mockRS;

    @BeforeEach
    void setUp() throws SQLException {
        mockCon = Mockito.mock(Connection.class);
        mockPS = Mockito.mock(PreparedStatement.class);
        mockRS = Mockito.mock(ResultSet.class);
        instance = new ProductDAO();
        expectedCharacteristics = new ArrayList<>();
        expectedCharacteristics.add(new Characteristic("cha1", "cha2"));

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int id = 1;
        String nameUa = "name";
        String nameEn = "name";
        BigDecimal price = new BigDecimal(1000);
        int weight = 200;
        Category category = new Category();
        category.setId(id);
        Company company = new Company();
        company.setId(id);
        int count = 10;
        int warranty =1;
        String titleUa = "title";
        String titleEn = "title";
        String descriptionUa = "descriptionUa";
        String descriptionEn = "descriptionEn";
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(1, 1, "name"));
        List<ProductCharacteristic> pc = new ArrayList<>();
        pc.add(new ProductCharacteristic(1, 1, new Compatibility(
                1,
                new Category(1, "name", "name"),
                new Characteristic(1, "name", "name")
                ),
                "value"));

        when(mockRS.getInt(Fields.ID)).thenReturn(id);
        when(mockRS.getString(Fields.NAME_UA)).thenReturn(nameUa);
        when(mockRS.getString(Fields.NAME_EN)).thenReturn(nameEn);
        when(mockRS.getBigDecimal(Fields.PRODUCT_PRICE)).thenReturn(price);
        when(mockRS.getInt(Fields.PRODUCT_WEIGHT)).thenReturn(weight);
        when(mockRS.getInt(Fields.PRODUCT_CATEGORY_ID)).thenReturn(id);
        when(mockRS.getInt(Fields.PRODUCT_COMPANY_ID)).thenReturn(id);
        when(mockRS.getInt(Fields.PRODUCT_COUNT)).thenReturn(count);
        when(mockRS.getInt(Fields.PRODUCT_WARRANTY)).thenReturn(warranty);
        when(mockRS.getString(Fields.PRODUCT_TITLE_UA)).thenReturn(titleUa);
        when(mockRS.getString(Fields.PRODUCT_TITLE_EN)).thenReturn(titleEn);
        when(mockRS.getString(Fields.PRODUCT_DESCRIPTION_UA)).thenReturn(descriptionUa);
        when(mockRS.getString(Fields.PRODUCT_DESCRIPTION_EN)).thenReturn(descriptionEn);

        expectedProduct = new Product();
        expectedProduct.setId(id);
        expectedProduct.setNameUa(nameUa);
        expectedProduct.setNameEn(nameEn);
        expectedProduct.setPrice(price);
        expectedProduct.setWeight(weight);
        expectedProduct.setCategory(category);
        expectedProduct.setCompany(company);
        expectedProduct.setCount(count);
        expectedProduct.setWarranty(warranty);
        expectedProduct.setTitleUa(titleUa);
        expectedProduct.setTitleEn(titleEn);
        expectedProduct.setDescriptionUa(descriptionUa);
        expectedProduct.setDescriptionEn(descriptionEn);
        expectedProduct.setPhotos(photos);
        expectedProduct.setProductCharacteristics(pc);
    }

    @Test
    void updateCountById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)) {
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedProduct.getId();
            int count = expectedProduct.getCount();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.updateCountById(id, count);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void updateProduct() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)) {
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Product product = expectedProduct;

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.updateProduct(product);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void createProductAndPhotosAndCharacteristics() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Product product = expectedProduct;
            List<Characteristic> li = expectedCharacteristics;

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createProductAndPhotosAndCharacteristics(product, li);

            verify(mockCon, times(4)).prepareStatement(anyString());
            verify(mockPS, times(2)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}