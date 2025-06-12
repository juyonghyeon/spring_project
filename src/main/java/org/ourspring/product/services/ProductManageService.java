package org.ourspring.product.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.ourspring.global.exceptions.script.AlertException;
import org.ourspring.global.libs.Utils;
import org.ourspring.product.entities.Product;
import org.ourspring.product.repositories.ProductRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    }

}
