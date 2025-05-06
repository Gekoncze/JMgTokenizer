package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.Token;

/**
 * A simple token builder to create tokens.
 * Position is added during builder creation to be later used during token creation.
 */
public @Component class TokenBuilder {
    private final @Mandatory StringBuilder text;
    private final int position;

    public TokenBuilder(int position) {
        this.text = new StringBuilder();
        this.position = position;
    }

    public TokenBuilder(int position, char ch) {
        this(position);
        this.text.append(ch);
    }

    public TokenBuilder(int position, @Mandatory String s) {
        this(position);
        this.text.append(s);
    }

    public @Mandatory String getText() {
        return text.toString();
    }

    public int getPosition() {
        return position;
    }

    public @Mandatory TokenBuilder append(char ch) {
        this.text.append(ch);
        return this;
    }

    public @Mandatory TokenBuilder append(String s) {
        this.text.append(s);
        return this;
    }

    public @Mandatory Token build(@Mandatory TokenFactory factory) {
        return factory.create(getText(), getPosition());
    }

    public static @Mandatory Token build(@Mandatory CharacterReader reader, @Mandatory TokenFactory factory) {
        return new TokenBuilder(
            reader.getPosition(),
            reader.read()
        ).build(factory);
    }
}
