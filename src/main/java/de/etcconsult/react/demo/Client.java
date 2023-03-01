package de.etcconsult.react.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "client")
public class Client {
  @Id
  @Column
  private Long id;

  @Column
  private String name;

  @Column
  private String email;
}
