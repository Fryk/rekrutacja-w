package com.cbtl.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String name;
    private ProductSpecification specification;
}
