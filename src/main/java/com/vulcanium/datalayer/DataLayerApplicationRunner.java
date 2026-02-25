package com.vulcanium.datalayer;

import com.vulcanium.datalayer.model.Category;
import com.vulcanium.datalayer.model.Comment;
import com.vulcanium.datalayer.model.Product;
import com.vulcanium.datalayer.service.CategoryService;
import com.vulcanium.datalayer.service.CommentService;
import com.vulcanium.datalayer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLayerApplicationRunner implements CommandLineRunner {

    private final static int ENTITY_ID = 1;
    private final static String ENTITY_PRODUCT_NAME = "AssuranceTousRisques";
    private final static String ENTITY_CATEGORY_NAME = "Standard";
    private final static String ENTITY_COMMENT_CONTENT = "Assurance";

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    private int categoryIdToDelete = -1;
    private int productIdToDelete = -1;

    @Override
    public void run(String... args) throws Exception {

        // Read entities from the database
        displayAllEntities();
        displayEntityById();
        displayEntityProductByIdWithComments();
        displayEntityCommentByIdWithProduct();
        displayEntityProductByIdWithCategories();
        displayEntityCategoryByIdWithProducts();
        displayEntitiesProductByName();
        displayEntitiesProductByCategoryName();
        displayEntitiesProductByCostLessThan();
        displayEntitiesCategoryByName();
        displayEntitiesCategoryByProductName();
        displayEntitiesCommentByContent();

        // Create entities in the database
        createEntitiesWithTheirRelationships();

        // Delete entities from the database
        deleteEntityCategoryById();
        deleteEntityProductById();
    }

    private void displayAllEntities() {

        System.out.println("-----------------------------");
        System.out.println("Products:");
        List<Product> products = productService.getProducts();
        products.forEach(product -> System.out.println(product.getName()));

        System.out.println("-----------------------------");
        System.out.println("Categories:");
        List<Category> categories = categoryService.getCategories();
        categories.forEach(category -> System.out.println(category.getName()));

        System.out.println("-----------------------------");
        System.out.println("Comments:");
        List<Comment> comments = commentService.getComments();
        comments.forEach(comment -> System.out.println(comment.getContent()));
    }

    private void displayEntityById() {

        System.out.println("-----------------------------");

        Optional<Product> optionalProduct = productService.getProductById(ENTITY_ID);
        optionalProduct.ifPresentOrElse(
                product -> System.out.println("Product: " + product.getName()),
                () -> System.out.println("Product not found")
        );

        System.out.println("-----------------------------");

        Optional<Category> optionalCategory = categoryService.getCategoryById(ENTITY_ID);
        optionalCategory.ifPresentOrElse(
                category -> System.out.println("Category: " + category.getName()),
                () -> System.out.println("Category not found")
        );

        System.out.println("-----------------------------");

        Optional<Comment> optionalComment = commentService.getCommentById(ENTITY_ID);
        optionalComment.ifPresentOrElse(
                comment -> System.out.println("Comment: " + comment.getContent()),
                () -> System.out.println("Comment not found")
        );
    }

    private void displayEntityProductByIdWithComments() {

        System.out.println("-----------------------------");

        Optional<Product> optionalProduct = productService.getProductById(ENTITY_ID);
        optionalProduct.ifPresentOrElse(
                product -> {
                    System.out.println("Product " + product.getName() + " with comments:");
                    product.getComments().forEach(comment -> System.out.println(comment.getContent()));
                },
                () -> System.out.println("Product with comments not found")
        );
    }

    private void displayEntityCommentByIdWithProduct() {

        System.out.println("-----------------------------");

        Comment comment = commentService.getCommentWithAssociatedProduct(ENTITY_ID);

        if (comment != null) {
            System.out.println("Comment " + comment.getContent() + " with product:");
            System.out.println(comment.getProduct().getName());
        } else {
            System.out.println("Comment with product not found");
        }
    }

    private void displayEntityProductByIdWithCategories() {

        System.out.println("-----------------------------");

        Optional<Product> optionalProduct = productService.getProductById(ENTITY_ID);
        optionalProduct.ifPresentOrElse(
                product -> {
                    System.out.println("Product " + product.getName() + " with categories:");
                    product.getCategories().forEach(category -> System.out.println(category.getName()));
                },
                () -> System.out.println("Product with categories not found")
        );
    }

    private void displayEntityCategoryByIdWithProducts() {

        System.out.println("-----------------------------");

        Optional<Category> optionalCategory = categoryService.getCategoryById(ENTITY_ID);
        optionalCategory.ifPresentOrElse(
                category -> {
                    System.out.println("Category " + category.getName() + " with products:");
                    category.getProducts().forEach(product -> System.out.println(product.getName()));
                },
                () -> System.out.println("Category with products not found")
        );
    }

    private void displayEntitiesProductByName() {

        System.out.println("-----------------------------");

        List<Product> products = productService.getProductsByName(ENTITY_PRODUCT_NAME);
        products.forEach(product -> System.out.println("Product " + ENTITY_PRODUCT_NAME + " has the following ID: " + product.getProductId()));
    }

    private void displayEntitiesProductByCategoryName() {

        System.out.println("-----------------------------");

        List<Product> products = productService.getProductsByCategoryName(ENTITY_CATEGORY_NAME);
        products.forEach(product -> System.out.println("Product " + product.getName() + " is included in the category: " + ENTITY_CATEGORY_NAME));
    }

    private void displayEntitiesProductByCostLessThan() {

        System.out.println("-----------------------------");

        List<Product> products = productService.getProductsByCostLessThan(1000);
        products.forEach(product -> System.out.println("Product " + product.getName() + " has the following cost (< 1000€): " + product.getCost() + "€"));
    }

    private void displayEntitiesCategoryByName() {

        System.out.println("-----------------------------");

        List<Category> categories = categoryService.getCategoriesByName(ENTITY_CATEGORY_NAME);
        categories.forEach(category -> System.out.println("Category " + ENTITY_CATEGORY_NAME + " has the following ID: " + category.getCategoryId()));
    }

    private void displayEntitiesCategoryByProductName() {

        System.out.println("-----------------------------");

        List<Category> categories = categoryService.getCategoriesByProductName(ENTITY_PRODUCT_NAME);
        categories.forEach(category -> System.out.println("Category " + category.getName() + " is included in the product: " + ENTITY_PRODUCT_NAME));
    }

    private void displayEntitiesCommentByContent() {

        System.out.println("-----------------------------");

        List<Comment> comments = commentService.getCommentsContaining(ENTITY_COMMENT_CONTENT);
        comments.forEach(comment -> System.out.println("Comment containing the word " + ENTITY_COMMENT_CONTENT + ": " + comment.getContent()));
    }

    private void createEntitiesWithTheirRelationships() {

        System.out.println("-----------------------------");

        // Create and persist the new category
        Category category = new Category();
        category.setName("Promotion");

        category = categoryService.saveCategory(category);

        // Create and persist the new product associated with the new category
        Product product = new Product();
        product.setName("AssuranceUltime");
        product.setDescription("Assurance auto qui couvre tout !");
        product.setCost(2000);

        category.addProduct(product);
        product = productService.saveProduct(product);

        // Create and persist the new comment associated with the new product
        Comment comment = new Comment();
        comment.setContent("C'est vraiment la meilleure assurance du monde. Par contre, ça coûte cher...");

        product.addComment(comment);
        comment = commentService.saveComment(comment);

        // Display all the relationships between the entities
        System.out.println("Created category " + category.getCategoryId() + ": " + category.getName());
        System.out.println("This category contains a product named: " + category.getProducts().getFirst().getName());
        System.out.println("This product contains the following comment: " + product.getComments().iterator().next().getContent());

        // Set the IDs of the entities to be deleted from the database
        categoryIdToDelete = category.getCategoryId();
        productIdToDelete = product.getProductId();
    }

    private void deleteEntityCategoryById() {

        System.out.println("-----------------------------");

        if (categoryIdToDelete != -1) {
            Category category = categoryService.getCategoryById(categoryIdToDelete).get();

            category.getProducts().forEach(category::removeProduct);
            categoryService.deleteCategory(categoryIdToDelete);

            System.out.println("The category " + category.getName() + " (" + categoryIdToDelete + "): has been deleted");
        } else {
            System.out.println("Category to delete not found");
        }
    }

    private void deleteEntityProductById() {

        System.out.println("-----------------------------");

        if (productIdToDelete != -1) {
            Product product = productService.getProductById(productIdToDelete).get();

            product.getComments().forEach(product::removeComment);
            productService.deleteProduct(productIdToDelete);

            System.out.println("The product " + product.getName() + " (" + productIdToDelete + "): has been deleted");
        } else {
            System.out.println("Product to delete not found");
        }
    }
}
