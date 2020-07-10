package com.mahuru.aggridss.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OlympicWinner {
  String athlete;
  String country;
  int age;
  String sport;
  int year;
  int gold;
  int silver;
  int bronze;
}
