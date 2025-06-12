package org.ourspring.product.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.ourspring.global.exceptions.script.AlertException;
import org.ourspring.global.libs.Utils;
import org.ourspring.product.constants.ProductStatus;
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

    private final HttpServletRequest request;
    private final ProductRepository repository;

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


                    // 강사님이 이 부분을 html에서 처리하여서 주석처리 넣습니다.
//                    String statusParam = request.getParameter("status_" + chk);
//                    if (statusParam != null) {
//                        try {
//                            ProductStatus status = ProductStatus.valueOf(statusParam.toUpperCase());
//                            product.setStatus(status);
//                        } catch (IllegalArgumentException e) {
//                            throw new AlertException("유효하지 않은 상태값입니다: " + statusParam, HttpStatus.BAD_REQUEST);
//                        }
//                    }

                }
            }

            products.add(product);


        }
        repository.saveAll(products);


    }

}
