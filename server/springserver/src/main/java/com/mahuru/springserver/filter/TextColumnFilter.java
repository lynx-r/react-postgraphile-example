package com.mahuru.springserver.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TextColumnFilter extends ColumnFilter {
  private String type;
  private String filter;
  private String filterType;
}
