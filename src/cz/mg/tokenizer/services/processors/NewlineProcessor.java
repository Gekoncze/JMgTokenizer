package cz.mg.tokenizer.services.processors;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Glyph;
import cz.mg.collections.list.List;

public @Service class NewlineProcessor {
    private static @Optional NewlineProcessor instance;

    public static @Mandatory NewlineProcessor getInstance() {
        if (instance == null) {
            instance = new NewlineProcessor();
        }
        return instance;
    }

    private NewlineProcessor() {
    }

    /**
     * Splits characters to rows by newline character.
     */
    public @Mandatory List<List<Glyph>> process(@Mandatory List<Glyph> glyphs) {
        List<List<Glyph>> rows = new List<>(new List<>());
        for (Glyph glyph : glyphs) {
            if (glyph.getCharacter() == '\n') {
                rows.addLast(new List<>());
            } else {
                rows.getLast().addLast(glyph);
            }
        }
        return rows;
    }
}
