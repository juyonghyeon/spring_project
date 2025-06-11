package org.ourspring.product.entities;

import lombok.Data;
import org.ourspring.global.entities.BaseEntity;
import org.ourspring.product.constants.ProductStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("PRODUCT")
public class Product extends BaseEntity {
    @Id
    private Long seq;
    private String gid;
    private String name;
    private String category;
    private ProductStatus status;

    @Column("consumerPrice")
    private int consumerPrice;

    @Column("salePrice")
    private int salePrice;

    private String description;
}
