package pl.com.bottega.spyqdoc.preparation;

import lombok.Data;

import java.util.UUID;

@Data
public class DoUpdateContent {
    public UUID id;
    public String content;
}
