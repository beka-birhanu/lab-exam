package com.onlineBookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BookModel
 */
@Getter
@Setter
@NoArgsConstructor
public class BookModel {
  private string title;
  private string author;
  private double price;
}
