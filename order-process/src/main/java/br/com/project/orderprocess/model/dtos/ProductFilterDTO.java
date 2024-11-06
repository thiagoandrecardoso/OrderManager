package br.com.project.orderprocess.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class ProductFilterDTO {
    private Long orderId;

    private int page = 0;
    private int pageSize = 10;

    @JsonIgnore
    public Pageable pageable() {
        return PageRequest.of(this.getPage(), this.pageSize == -1 ? Integer.MAX_VALUE : this.pageSize);
    }
}
