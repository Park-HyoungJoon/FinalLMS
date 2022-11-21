package com.edo.util.pagination;

import com.edo.util.pagination.Paging;
import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paged<T> {

    private Page<T> page;

    private Paging paging;

}