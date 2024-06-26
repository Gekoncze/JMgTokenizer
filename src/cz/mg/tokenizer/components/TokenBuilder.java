package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.Token;

public @Component class TokenBuilder {
    private @Mandatory StringBuilder text;
    private int position;

    public TokenBuilder(int position) {
        this.text = new StringBuilder();
        this.position = position;
    }

    public TokenBuilder(int position, char ch) {
        this(position);
        this.text.append(ch);
    }

    public @Mandatory StringBuilder getText() {
        return text;
    }

    public void setText(@Mandatory StringBuilder text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public @Mandatory Token build(@Mandatory TokenFactory factory) {
        return factory.create(text.toString(), position);
    }

    public static @Mandatory Token build(@Mandatory CharacterReader reader, @Mandatory TokenFactory factory) {
        return new TokenBuilder(
            reader.getPosition(),
            reader.read()
        ).build(factory);
    }
}
