package br.com.project.orderprocess.mappers;

import br.com.project.orderprocess.model.dtos.ProductDTO;
import br.com.project.orderprocess.model.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    default Page<ProductDTO> convert(Page<ProductEntity> page) {
        return new PageImpl<>(this.convert(page.getContent()), page.getPageable(), page.getTotalElements());
    }

    List<ProductDTO> convert(List<ProductEntity> content);
}
