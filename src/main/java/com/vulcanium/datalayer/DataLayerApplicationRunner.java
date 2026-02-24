package com.vulcanium.datalayer;

import com.vulcanium.datalayer.model.Category;
import com.vulcanium.datalayer.model.Comment;
import com.vulcanium.datalayer.model.Product;
import com.vulcanium.datalayer.service.CategoryService;
import com.vulcanium.datalayer.service.CommentService;
import com.vulcanium.datalayer.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DataLayerApplicationRunner implements CommandLineRunner {

    private final static int ENTITY_ID = 1;

    private ProductService productService;
    private CategoryService categoryService;
    private CommentService commentService;

    @Override
    public void run(String... args) throws Exception {
        displayAllEntities();
        displayEntityById();
        displayEntityProductByIdWithComments();
        displayEntityCommentByIdWithProduct();
        displayEntityProductByIdWithCategories();
        displayEntityCategoryByIdWithProducts();
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
}
