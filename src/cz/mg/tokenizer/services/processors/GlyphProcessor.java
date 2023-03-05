package cz.mg.tokenizer.services.processors;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Glyph;
import cz.mg.collections.list.List;

public @Service class GlyphProcessor {
    private static @Optional GlyphProcessor instance;

    public static @Mandatory GlyphProcessor getInstance() {
        if (instance == null) {
            instance = new GlyphProcessor();
        }
        return instance;
    }

    private GlyphProcessor() {
    }

    /**
     * Splits string into individual characters with their original position.
     */
    public @Mandatory List<Glyph> process(@Mandatory String content) {
        List<Glyph> glyphs = new List<>();
        for (int i = 0; i < content.length(); i++) {
            glyphs.addLast(new Glyph(content.charAt(i), i));
        }
        return glyphs;
    }
}
