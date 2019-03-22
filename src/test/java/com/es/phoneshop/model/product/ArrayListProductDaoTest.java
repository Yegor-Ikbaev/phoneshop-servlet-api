package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArrayListProductDaoTest {

    private final Long ID = 1L;

    private Product product;

    private ProductDao productDao;

    @Before
    public void setup() {
	productDao = ArrayListProductDao.getInstance();
	product = new Product();
	product.setId(ID);
    }

    @After
    public void destroy() {
	product.setPrice(new BigDecimal(1));
	product.setStock(1);
	productDao.findProducts().forEach((product) -> productDao.delete(product.getId()));
    }

    @Test
    public void testFindProductsNoResults() {
	assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductAfterSaving() {
	product.setPrice(new BigDecimal(1));
	product.setStock(1);
	productDao.save(product);
	assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testFindProductAfterSavingWithStockLess0() {
	product.setPrice(new BigDecimal(1));
	product.setStock(0);
	productDao.save(product);
	assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductAfterSavingNoResults() {
	productDao.save(product);
	assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProductById() {
	product.setPrice(new BigDecimal(1));
	product.setStock(1);
	productDao.save(product);
	assertEquals(ID, productDao.getProduct(ID).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductByIdNoResult() {
	productDao.getProduct(ID);
    }

    @Test
    public void testDeleteProduct() {
	productDao.save(product);
	productDao.delete(ID);
	assertTrue(productDao.findProducts().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductNoSuchProduct() {
	productDao.delete(ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductsWithSameId() {
	Product product2 = new Product();
	product2.setId(ID);
	productDao.save(product);
	productDao.save(product2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductsWithNullId() {
	product.setId(null);
	productDao.save(product);
    }

    @Test
    public void testFindByDescription() {
	int stock = 1;
	BigDecimal price = new BigDecimal(1);

	Product product1 = new Product();
	product1.setDescription("Samsung Galaxy S");
	product1.setId(1L);
	product1.setPrice(price);
	product1.setStock(stock);
	Product product2 = new Product();
	product2.setDescription("Samsung Galaxy S III");
	product2.setId(2L);
	product2.setPrice(price);
	product2.setStock(stock);
	Product product3 = new Product();
	product3.setDescription("Apple iPhone");
	product3.setId(3L);
	product3.setPrice(price);
	product3.setStock(stock);
	Product product4 = new Product();
	product4.setDescription("Siemens C61");
	product4.setId(4L);
	product4.setPrice(price);
	product4.setStock(stock);

	productDao.save(product1);
	productDao.save(product2);
	productDao.save(product3);
	productDao.save(product4);

	List<Product> controlProducts = new ArrayList<>();
	controlProducts.add(product1);
	controlProducts.add(product2);

	assertEquals(controlProducts, productDao.findProductsByDescription("Samsung"));
    }

    @Test
    public void testFindByDescriptionNoResult() {
	int stock = 1;
	BigDecimal price = new BigDecimal(1);

	Product product1 = new Product();
	product1.setDescription("Samsung Galaxy S");
	product1.setId(1L);
	product1.setPrice(price);
	product1.setStock(stock);
	Product product2 = new Product();
	product2.setDescription("Samsung Galaxy S III");
	product2.setId(2L);
	product2.setPrice(price);
	product2.setStock(stock);
	Product product3 = new Product();
	product3.setDescription("Apple iPhone");
	product3.setId(3L);
	product3.setPrice(price);
	product3.setStock(stock);
	Product product4 = new Product();
	product4.setDescription("Siemens C61");
	product4.setId(4L);
	product4.setPrice(price);
	product4.setStock(stock);

	productDao.save(product1);
	productDao.save(product2);
	productDao.save(product3);
	productDao.save(product4);

	assertTrue(productDao.findProductsByDescription("Nokia").isEmpty());
    }
}