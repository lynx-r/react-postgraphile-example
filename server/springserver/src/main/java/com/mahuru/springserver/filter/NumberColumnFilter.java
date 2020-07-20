package com.mahuru.springserver.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class NumberColumnFilter extends ColumnFilter {
  private String type;
  private Integer filter;
  private Integer filterTo;
}
