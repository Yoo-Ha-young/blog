package com.board.board.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PopularSearch")
@Table(name = "PopularSearch")
public class PopularSearchEntity {
    @Id
    private String popularTerm;

    private int popularSearchCount;

}
