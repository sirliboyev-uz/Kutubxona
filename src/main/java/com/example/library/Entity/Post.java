package com.example.library.Entity;

import com.example.library.Entity.Template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post extends AbstractEntity {

    @Column(columnDefinition = "text")
    private String title;

    @Column(columnDefinition = "text")
    private String text;

    @Column(nullable = false)
    private String url;
}
