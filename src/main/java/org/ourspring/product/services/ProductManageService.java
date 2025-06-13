package org.ourspring.product.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.ourspring.global.exceptions.script.AlertException;
import org.ourspring.product.entities.Product;
import org.ourspring.product.repositories.ProductRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
public class ProductManageService {

    private final ProductRepository repository;

    private final HttpServletRequest request;


    public void processBatch(List<Integer> chks) {

        if (chks == null || chks.isEmpty()) {
            throw new AlertException("처리할 상품을 선택하세요.", HttpStatus.BAD_REQUEST);
        }
        String method = request.getMethod();
        List<Product> products = new ArrayList<>();

        for (int chk : chks) {
            Long seq = Long.valueOf(request.getParameter("seq_" + chk));
            Product product = repository.findById(seq).orElse(null);
            if (product == null) continue;
            if (method.equalsIgnoreCase("DELETE")) {
                product.setDeletedAt(LocalDateTime.now());
            } else {
                boolean updateStatus = Boolean.parseBoolean(Objects.requireNonNull(request.getParameter("updateStatus_" + chk), "false"));
                if (updateStatus) {
                    product.setModifiedAt(LocalDateTime.now());

                }
                products.add(product);

            }

        }
        repository.saveAll(products);
    }

}
