package org.ourspring.product.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.ourspring.global.exceptions.script.AlertException;
import org.ourspring.global.libs.Utils;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class ProductManageService {

    public void processBatch(List<Integer> chks) {

        if (chks == null || chks.isEmpty()) {
            throw new AlertException("처리할 상품을 선택하세요.", HttpStatus.BAD_REQUEST);
        }




    }

}
