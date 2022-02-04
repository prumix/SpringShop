import com.prumi.web.api.dto.ProductDto;
import com.prumi.web.core.entities.Product;
import com.prumi.web.core.entities.ProductCategory;
import com.prumi.web.core.repositories.ProductsRepository;
import com.prumi.web.core.services.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

@SpringBootTest(classes = ProductsService.class)
public class ProductTest {

    @Autowired
    private ProductsService productsService;

    @MockBean
    private ProductsRepository productsRepository;


    @Test
    public void findByIdTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);
        productCategory.setTitle("Meat");

        Product product = new Product();
        product.setId(1L);
        product.setTitle("product");
        product.setPrice(10);
        product.setProductCategory(productCategory);

        Mockito.doReturn(Optional.of(product))
                .when(productsRepository)
                .findById(product.getId());
        Assertions.assertNotNull(product);
        Assertions.assertEquals(1L, product.getId());

    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);
        productCategory.setTitle("Meat");

        Product product = new Product();
        product.setId(1L);
        product.setTitle("product");
        product.setPrice(10);
        product.setProductCategory(productCategory);

        Mockito.doReturn(Optional.of(product))
                .when(productsRepository)
                .findById(1L);
        productsService.save(product);

        Product productTest = productsService.findById(1L).get();
        Assertions.assertNotNull(productTest);



    }
}
