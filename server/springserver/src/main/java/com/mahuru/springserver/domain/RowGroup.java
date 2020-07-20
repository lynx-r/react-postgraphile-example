package com.mahuru.springserver.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@NoArgsConstructor
@Data
public class RowGroup {
  String id;
  String field;
  List<Statement> whereStatements;
  String displayName;

  public RowGroup(String id, String field, List<Statement> whereStatements, String displayName) {
    this.id = id;
    this.field = field;
    this.whereStatements = whereStatements != null ? whereStatements : new ArrayList<>();
    this.displayName = displayName;
  }

  public boolean hasWhere() {
    return whereStatements != null && !whereStatements.isEmpty();
  }

  public String getWhereField() {
    if (!hasWhere()) {
      return "";
    }
    return whereStatements
        .stream()
        .map(st -> format("%s %s %s", field, st.op, st.value))
        .collect(joining(" AND "));
  }
}
